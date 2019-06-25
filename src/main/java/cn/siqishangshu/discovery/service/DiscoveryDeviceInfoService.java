package cn.siqishangshu.discovery.service;

import cn.siqishangshu.discovery.model.DeviceInfo;
import cn.siqishangshu.discovery.model.DiscoveryConfig;
import cn.siqishangshu.discovery.model.PortInfo;
import cn.siqishangshu.discovery.util.Constants;
import cn.siqishangshu.discovery.util.REUtil;
import cn.siqishangshu.snmp.mib.*;
import cn.siqishangshu.snmp.util.SNMPConfig;
import cn.siqishangshu.snmp.util.SNMPUtils;
import org.snmp4j.smi.Variable;

import java.net.InetAddress;
import java.util.*;

public class DiscoveryDeviceInfoService {

	private  DiscoveryConfig discoveryConfig ;
	/**
	 * 发现设备
	 * @return
	 */
	public DeviceInfo getDeviceBasicInfo(String ip, List<SNMPConfig> configList,  DiscoveryConfig discoveryConfig) {
		this.discoveryConfig = discoveryConfig;
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceIP(ip);
		deviceInfo.getDeviceIps().add(ip);

		SNMPUtils snmp = new SNMPUtils();
		System.out.println("IP: "+ip);
		try {
			for (SNMPConfig snmpConfig : configList) {
				try {
					snmp.start(ip,snmpConfig);
					if(snmp.isReadable()){
						getSystem(deviceInfo,snmp);        //系统信息
						if("".equals(deviceInfo.getSystemInfo().getSysDesc())){
							return null;
						}
						deviceInfo.setSnmpConfig(snmpConfig);
						getMainMAC(deviceInfo,snmp);       //主MAC地址
						getIpAddressInfo(deviceInfo,snmp); //IP地址列表
						getInterfaceInfo(deviceInfo,snmp); //端口信息
						getIpNetToMediaInfo(deviceInfo,snmp);  //ARP表
						getIpRouteInfo(deviceInfo,snmp);		//router
						getLLDPInfo(deviceInfo,snmp);      //LLDP邻居表
						getCDPInfo(deviceInfo,snmp);       //邻居表（思科）
						getOspfInfo(deviceInfo,snmp);      //OSPF信息表
						getStpInfo(deviceInfo,snmp);       //STP生成树协议表
						getDocFPTableInfo(deviceInfo,snmp);//DOCFP信息
						getBasePortInfo(deviceInfo,snmp); //BasePort信息
						return deviceInfo;
					}else{
						continue;
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}finally{
					snmp.end();
				}
			}
			if (discoveryConfig.isUsePing()) {
				InetAddress address = InetAddress.getByName(ip);
				boolean flag = address.isReachable(3000);
				if(flag){
					String hostName = address.getCanonicalHostName();
					deviceInfo.setDeviceIP(ip);
					deviceInfo.setDeviceName(hostName);
					deviceInfo.getDeviceIps().add(ip);
					deviceInfo.setDeviceProtocol(Constants.DeviceProtocolPing);
					return deviceInfo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 获取设备系统描述信息
	 * @param deviceInfo
	 */
	private void getSystem(DeviceInfo deviceInfo ,SNMPUtils snmp) {
		try {
			deviceInfo.setDeviceProtocol(Constants.DeviceProtocolSNMP);
			SystemInfo systemInfo = (SystemInfo)snmp.getObjectInfo(SystemInfo.class);
			if(systemInfo.getSysDesc() != null){
				deviceInfo.setSystemInfo(systemInfo);
				deviceInfo.setDeviceName(systemInfo.getSysName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 获取系统主MAC地址
	 */
	private void getMainMAC(DeviceInfo deviceInfo,SNMPUtils snmp) {

		try {
			Variable var = snmp.getVariable(Constants.SwitchMacOid);
			String mac = var ==null ? null : var.toString();
			if (REUtil.isMac(mac)){
				deviceInfo.getDeviceMacs().add(mac.toUpperCase());
				deviceInfo.setDeviceMAC(mac.toUpperCase());
				deviceInfo.setDeviceType(Constants.DeviceTypeSwitch);
			} else{
			   mac = getHostMAC(deviceInfo, snmp);
				if(mac != null){
					deviceInfo.getDeviceMacs().add(mac.toUpperCase());
					deviceInfo.setDeviceMAC(mac.toUpperCase());
				}
			   getDeviceTypeInfo(deviceInfo,snmp);//设备类型
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 获取设备端口信息
	 */
	private void getInterfaceInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		try {
			List<IfEntry> ifEntries = snmp.getTable(IfEntry.class);
			deviceInfo.setIfEntries(ifEntries);
			for (IfEntry ifEntry : ifEntries) {
				String mac = ifEntry.getIfPhysAddress()==null?"":ifEntry.getIfPhysAddress();
				if (!REUtil.isMac(mac)){
					continue;
				}else{
					deviceInfo.getDeviceMacs().add(mac.toUpperCase());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 获取设备ARP表
	 */
	private void getIpNetToMediaInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		try {
			List<IpNetToMediaEntry> ipAdEntAddrList = snmp.getTable(IpNetToMediaEntry.class);
			deviceInfo.getIpNetToMediaEntries().addAll(ipAdEntAddrList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 获取思科CDP信息
	 */
	private void getCDPInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		try {
			List<CdpCacheEntry> cdpCacheEntries = snmp.getTable(CdpCacheEntry.class);
			deviceInfo.getCdpCacheEntries().addAll(cdpCacheEntries);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 获取交换机STP生成树
	 * @param deviceInfo
	 */
	private void getStpInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		try {
			List<Dot1dStpPortEntry> dot1dStpPortEntries = snmp.getTable(Dot1dStpPortEntry.class);
			deviceInfo.getDot1dStpPortEntries().addAll(dot1dStpPortEntries);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * 获取交换机OSPF信息
	 * @param deviceInfo
	 */
	private void getOspfInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		try {
			List<OspfNbrEntry> ospfNbrEntries = snmp.getTable(OspfNbrEntry.class);
			deviceInfo.getOspfNbrEntries().addAll(ospfNbrEntries);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *
	 *
	 * 获取交换机OSPF信息
	 * @param deviceInfo
	 */
	private void getIpRouteInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		try {
			List<IpRouteEntry> ipRouteEntries = snmp.getTable(IpRouteEntry.class);
			Iterator<IpRouteEntry> it = ipRouteEntries.iterator();
			while(it.hasNext()){
				IpRouteEntry ipRouteEntry = it.next();
				if(REUtil.isEffectiveIp(ipRouteEntry.getIpRouteDest()) &&
						REUtil.isEffectiveIp(ipRouteEntry.getIpRouteNextHop())){
					deviceInfo.getIpRouteEntries().add(ipRouteEntry);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 获取设备IP地址表
	 * @param deviceInfo
	 */
	private void getIpAddressInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		try {
			List<IpAddrEntry> ipAddrEntries = snmp.getTable(IpAddrEntry.class);
			for (IpAddrEntry ipAddrEntry : ipAddrEntries) {
				if(REUtil.isEffectiveIp(ipAddrEntry.getIpAdEntAddr())){
					deviceInfo.getDeviceIps().add(ipAddrEntry.getIpAdEntAddr());
					deviceInfo.getIpAddrEntries().add(ipAddrEntry);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 获取设备LLDP邻居表
	 */
	private void getLLDPInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		try {
			List<LldpLocPortEntry> lldpLocPortEntries = snmp.getTable(LldpLocPortEntry.class);
			List<LldpRemEntry> lldpRemEntries = snmp.getTable(LldpRemEntry.class);
			Iterator<LldpRemEntry> localIterator = lldpRemEntries.iterator();
			while (localIterator.hasNext())
			{
				LldpRemEntry localLLDPRemEntry =   localIterator.next();
				String str;
				if (localLLDPRemEntry.getLldpRemChassisIdSubtype() == 4)
				{
					str = REUtil.upperMac(localLLDPRemEntry.getLldpRemChassisId());
					localLLDPRemEntry.setLldpRemChassisId(str);
				}
				if (localLLDPRemEntry.getLldpRemPortIdSubtype() == 4)
				{
					str = REUtil.upperMac(localLLDPRemEntry.getLldpRemPortId());
					localLLDPRemEntry.setLldpRemPortId(str);
				}
				deviceInfo.getLldpRemEntries().add(localLLDPRemEntry);
			}
			deviceInfo.getLldpLocPortEntries().addAll(lldpLocPortEntries);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * snmp
	 * @param deviceInfo
	 */
	private void getDocFPTableInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		try {
			List<Dot1dTpFdbEntry> dot1dTpFdbEntries = snmp.getTable(Dot1dTpFdbEntry.class);
			Iterator<Dot1dTpFdbEntry> dot1dTpFdbEntryIterator = dot1dTpFdbEntries.iterator();
			while (dot1dTpFdbEntryIterator.hasNext())
			{
				Dot1dTpFdbEntry localDot1dTpFdbEntry = dot1dTpFdbEntryIterator.next();
				String dot1dTpFdbAddress  = localDot1dTpFdbEntry.getDot1dTpFdbAddress();
				localDot1dTpFdbEntry.setDot1dTpFdbAddress(REUtil.upperMac(dot1dTpFdbAddress));
			}
			boolean bool = false;
			if ((dot1dTpFdbEntries.size() <= 0) && (deviceInfo.getLldpRemEntries().size() <= 0) && (deviceInfo.getCdpCacheEntries().size() <= 0) && (deviceInfo.getDot1dStpPortEntries().size() <= 0))
			{
				bool = true;
				Iterator<IpNetToMediaEntry> localObject2 = deviceInfo.getIpNetToMediaEntries().iterator();
				while (localObject2.hasNext())
				{
					IpNetToMediaEntry ipNetToMediaEntry =  localObject2.next();
					Dot1dTpFdbEntry  dot1dTpFdbEntry = new Dot1dTpFdbEntry();
					dot1dTpFdbEntry.setDot1dTpFdbAddress(ipNetToMediaEntry.getIpNetToMediaPhysAddress());
					dot1dTpFdbEntry.setDot1dTpFdbPort(ipNetToMediaEntry.getIpNetToMediaIfIndex());
					dot1dTpFdbEntry.setDot1dTpFdbStatus(3);
					if (ipNetToMediaEntry.getIpNetToMediaType() != 2)
						dot1dTpFdbEntries.add(dot1dTpFdbEntry);
				}
			}
			Map<Integer,List<String>> hashMap = new HashMap();
			Iterator<Dot1dTpFdbEntry>  dot1dTpFdbEntryIterator1 = dot1dTpFdbEntries.iterator();
			List<String> macInfos;
			while (dot1dTpFdbEntryIterator1.hasNext())
			{
				Dot1dTpFdbEntry dot1dTpFdbEntry =  dot1dTpFdbEntryIterator1.next();
				if (dot1dTpFdbEntry.getDot1dTpFdbStatus() != 2 && (dot1dTpFdbEntry.getDot1dTpFdbStatus() != 4))
				{
					String  dot1dTpFdbAddress = dot1dTpFdbEntry.getDot1dTpFdbAddress();
					deviceInfo.getDot1dTpFdbEntries().add(dot1dTpFdbEntry);
					macInfos =  hashMap.get(Integer.valueOf(dot1dTpFdbEntry.getDot1dTpFdbPort()));
					if (macInfos == null)
					{
						macInfos = new ArrayList();
						hashMap.put(Integer.valueOf(dot1dTpFdbEntry.getDot1dTpFdbPort()), macInfos);
					}
					if ( REUtil.isMac(dot1dTpFdbAddress) && (!macInfos.contains(dot1dTpFdbAddress)))
						macInfos.add(dot1dTpFdbAddress);
				}
			}
			Set set =   hashMap.entrySet();
			Iterator  iterator = set.iterator();
			while (iterator.hasNext())
			{
				Map.Entry<Integer, List<String>> map = (Map.Entry<Integer, List<String>>)iterator.next();
				PortInfo portInfo = new PortInfo();
				portInfo.device = deviceInfo;
				portInfo.port = map.getKey();
				portInfo.isUseIfIndexPort = bool;
				portInfo.portMacList = map.getValue();
				deviceInfo.getPortInfos().add(portInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *
	 * snmp
	 * @param deviceInfo
	 */
	private void getBasePortInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		try {
			List<Dot1dBasePortEntry> dot1dBasePortEntries = snmp.getTable(Dot1dBasePortEntry.class);
			deviceInfo.getDot1dBasePortEntries().addAll(dot1dBasePortEntries);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	 * 
	 * @param deviceInfo
	 * @return
	 */
	public void getDeviceTypeInfo(DeviceInfo deviceInfo,SNMPUtils snmp) {
		//网关类型
		try {
			int ipForwarding = snmp.getVariable(Constants.GatewayOid).toInt();
			if(1==ipForwarding){
				deviceInfo.setDeviceType(Constants.DeviceTypeRouter); //网关
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//主机类型
		try {
			List<SoftwareRunEntry> softwareRunEntryList = snmp.getTable(SoftwareRunEntry.class);
			if(softwareRunEntryList!=null && softwareRunEntryList.size()>0){
				deviceInfo.setDeviceType(Constants.DeviceTypeComputer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 获取主机MAC信息
	 */
	private String getHostMAC(DeviceInfo deviceInfo,SNMPUtils snmp) {
		String mac = null;
		try {
			InterfacesInfo interfacesInfo = (InterfacesInfo)snmp.getObjectInfo(InterfacesInfo.class);
			if(interfacesInfo.getIfNumber() > 0){
				List<IfEntry> ifEntityList = interfacesInfo.getIfEntry();
				List<IpAddrEntry> ipAddrEntries = snmp.getTable(IpAddrEntry.class);
				int typeIndex = 0 ;
				for (IpAddrEntry ipAddrEntry : ipAddrEntries) {
					if(ipAddrEntry.getIpAdEntAddr().equals(deviceInfo.getDeviceIP())){
						typeIndex = ipAddrEntry.getIpAdEntIfIndex();
					}
				}
				if (typeIndex!=0){
					for (IfEntry ifEntry : ifEntityList) {
						if (typeIndex == ifEntry.getIfIndex()){
							mac = ifEntry.getIfPhysAddress();
							break;
						}
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mac;
	}
}
