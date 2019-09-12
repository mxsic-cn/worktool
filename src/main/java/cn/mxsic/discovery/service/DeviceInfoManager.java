package cn.mxsic.discovery.service;

import cn.mxsic.discovery.model.DeviceInfo;
import cn.mxsic.discovery.model.PortInfo;
import cn.mxsic.discovery.util.ComUtil;
import cn.mxsic.discovery.util.Constants;
import cn.mxsic.discovery.util.REUtil;
import cn.mxsic.log.print.LocalLogUtil;
import cn.mxsic.snmp.mib.IpNetToMediaEntry;

import java.util.*;
import java.util.Map.Entry;

public class DeviceInfoManager {

	public List<DeviceInfo> allDeviceList = new ArrayList<DeviceInfo>();
	public List<DeviceInfo> switchList = new ArrayList<DeviceInfo>();
	public HashMap<String, DeviceInfo> macDeviceMap = new HashMap<String, DeviceInfo>();
	public Map<String, String> allMacIpTable = new HashMap<String, String>();

	public DeviceInfoManager(List<DeviceInfo> deviceInfos) {
		System.out.println("开始初始化分析数据" + new Date(System.currentTimeMillis()));
		this.allDeviceList = deviceInfos;
		initMac();
		initDevice();
		initPort();
		System.out.println("分析数据初始化完毕!" + new Date(System.currentTimeMillis()));
	}

	/**
	 * 
	 * 初始化系统发现设备的所有mac-ip地址表
	 * 
	 */
	private void initMac() {
		Iterator<DeviceInfo> deviceInfoIterator = this.allDeviceList.iterator();
		while (deviceInfoIterator.hasNext()) {
			DeviceInfo deviceInfo = deviceInfoIterator.next();
			String deviceMAC = deviceInfo.getDeviceMAC();
			Set<String> deviceMacList = deviceInfo.getDeviceMacs();
			List<IpNetToMediaEntry> deviceIpAdEntAddrList = deviceInfo
					.getIpNetToMediaEntries();
			Iterator<IpNetToMediaEntry> ipNetToMediaEntryIterator = deviceIpAdEntAddrList.iterator();
			while (ipNetToMediaEntryIterator.hasNext()) {
				IpNetToMediaEntry ipAdEntAddr = ipNetToMediaEntryIterator.next();
				String mac = ipAdEntAddr.getIpNetToMediaPhysAddress();
				String ip = ipAdEntAddr.getIpNetToMediaNetAddress();
				if (REUtil.isMac(mac)&& ComUtil.ipOnList(ip,allDeviceList)) {
					LocalLogUtil.writeSqllog(mac.toUpperCase()+">>"+ ip,"mac_ip.txt");
					allMacIpTable.put(mac.toUpperCase(), ip);
				}
			}

			// 迭代设备MAC地址列表
			Iterator<String> macIterator = deviceMacList.iterator();
			while (macIterator.hasNext()) {
				String mac = macIterator.next();
				if (REUtil.isMac(mac)) {
					LocalLogUtil.writeSqllog(mac.toUpperCase()+">>"+ deviceInfo.getDeviceIP(),"mac_ip.txt");
					allMacIpTable.put(mac.toUpperCase(), deviceInfo.getDeviceIP());
					this.macDeviceMap.put(mac.toUpperCase(), deviceInfo);
				}
			}
			if (REUtil.isMac(deviceMAC)){
				LocalLogUtil.writeSqllog(deviceMAC.toUpperCase()+">>"+ deviceInfo.getDeviceIP(),"mac_ip.txt");
				allMacIpTable.put(deviceMAC.toUpperCase(), deviceInfo.getDeviceIP());
				this.macDeviceMap.put(deviceMAC.toUpperCase(), deviceInfo);
			}
		}
	}

	/**
	 * 
	 * 设备分组 按照有无连接关系进行设备分组
	 * 
	 */
	private void initDevice() {
		try {
			Iterator<DeviceInfo> it = this.allDeviceList.iterator();
			while (it.hasNext()) {
				DeviceInfo deviceInfo = it.next();
				if (deviceInfo.getDot1dTpFdbEntries().size() > 0) {
					this.switchList.add(deviceInfo);
				}
				if(deviceInfo.getDeviceProtocol().equals(Constants.DeviceProtocolPing)|| !REUtil.isMac(deviceInfo.getDeviceMAC())){
					if(allMacIpTable.containsValue(deviceInfo.getDeviceIP())){
						String mac = getMacOfIP(deviceInfo.getDeviceIP());
						 Iterator i = allMacIpTable.keySet().iterator();
						while(i.hasNext()){
							String key = (String)i.next();
							if(deviceInfo.getDeviceIP().equals(allMacIpTable.get(key))){
								if(mac == null){
									mac = key;
								}
								deviceInfo.getDeviceMacs().add(key);
								deviceInfo.getDeviceIps().add(allMacIpTable.get(key));
							}
						}
						deviceInfo.setDeviceMAC(mac);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 初始化交换机端口下挂设备信息
	 * 
	 */
	private void initPort() {
		try {
			System.out.println("开始初始化交换机端口下挂设备信息.....");
			Iterator<DeviceInfo> it = this.switchList.iterator();
			while (it.hasNext()) {
				DeviceInfo deviceInfo = it.next();
				Iterator<PortInfo> it2 = deviceInfo.getPortInfos()
						.iterator();
				while (it2.hasNext()) {
					PortInfo portInfo = it2.next();
					initPortInfo(portInfo);
				}
			}
			System.out.println("初始化交换机端口下挂设备信息完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 根据设备IP地址获取设备集合中对应的设备信息
	 * @param ip
	 * @return
	 */
	public DeviceInfo getDeviceInfoByIp(String ip) {
        // Effective IP地址
		if (REUtil.isEffectiveIp(ip)) {
            //遍历设备集合
            Iterator<DeviceInfo> it = this.allDeviceList.iterator();
            while (it.hasNext()) {
                DeviceInfo deviceInfo = it.next();
                if (deviceInfo.getDeviceIps().contains(ip)) {
                    return deviceInfo;
                }
            }
        }
		return null;
	}

	/**
	 * 
	 * 根据MAC地址查询设备集合中对应的设备信息
	 * @param mac
	 * @return
	 */
	public DeviceInfo getDeviceInfoByMAC(String mac) {
		//排除无效MAC地址
		if (!REUtil.isMac(mac)) {
			return null;
		}
		//遍历设备集合
		DeviceInfo deviceInfo  = this.macDeviceMap.get(mac.toUpperCase());
		return deviceInfo;
	}

	/**
	 * 
	 * 根据MAC获取对应IP地址
	 * @param mac
	 * @return
	 */
	public String getIpofMac(String mac) {
        if(REUtil.isMac(mac)){
            return allMacIpTable.get(mac.toUpperCase());
        }
        return null;
	}

	/**
	 * 根据MAC获取对应IP地址
	 * 
	 * @param ip
	 * @return
	 */
	public String getMacOfIP(String ip) {
		Set<Entry<String, String>> entrySet = allMacIpTable.entrySet();
		Iterator<Entry<String, String>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String,String> entry = it.next();
			if (entry.getValue().equals(ip)){
				return entry.getKey();
			}
		}
		for (DeviceInfo deviceInfo : allDeviceList) {
			if(deviceInfo.getDeviceIps().contains(ip)){
				return deviceInfo.getDeviceMAC();
			}
		}
		return "";
	}

	/**
	 * 
	 * 初始化设备下挂设备
	 * 
	 * @param portInfo
	 */
	private void initPortInfo(PortInfo portInfo) {
		List<String> portMacList = portInfo.portMacList;
		Iterator<String> it = portMacList.iterator();
		while (it.hasNext()) {
			String mac = it.next();
			DeviceInfo deviceInfo = getDeviceInfoByMAC(mac);
			if ((deviceInfo == null) || deviceInfo.equals(portInfo.device)
					|| portInfo.subDeviceList.contains(deviceInfo)) {
				continue;
			}
			portInfo.subDeviceList.add(deviceInfo);
		}
	}

}