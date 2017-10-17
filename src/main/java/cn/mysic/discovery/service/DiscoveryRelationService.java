package cn.mysic.discovery.service;

import cn.mysic.discovery.model.*;
import cn.mysic.discovery.util.Constants;
import cn.mysic.discovery.util.REUtil;
import cn.mysic.snmp.mib.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class DiscoveryRelationService {

	DeviceInfoManager deviceManager;
	List<LinkInfo> allLinkList = new ArrayList<LinkInfo>();

	public List<LinkInfo> createAllLinks(List<DeviceInfo> deviceInfoList,DiscoveryConfig discoveryConfig) {
		this.deviceManager = new DeviceInfoManager(deviceInfoList);
		if (discoveryConfig.isLLDP()) {
			System.out.println("\n******Starting LLDP Links find.....\n");
			DiscoveryLLDPLinks(deviceInfoList);
		}
		if (discoveryConfig.isCDP()) {
			System.out.println("\n******Starting CDP Links find.....\n");
			DiscoveryCDPLinks(deviceInfoList);
		}
		if (discoveryConfig.isOSPFNBR()) {
			System.out.println("\n******Starting OspfNbr Links find.....\n");
			DiscoveryOspfNbrLinks(deviceInfoList);
		}
		if(discoveryConfig.isARP()){
			System.out.println("\n******Starting ARP Links find.....\n");
			DiscoveryIPRouterLinks(deviceInfoList);
		}
		if (discoveryConfig.isSTP()) {
			System.out.println("\n******Starting Stp Links find.....\n");
			DiscoveryStpLinks(deviceInfoList);
		}
		if (discoveryConfig.isPortForward()) {
			System.out.println("\n******Starting MAC-IP Links find.....\n");
			DiscoveryMacLinks(deviceInfoList);
		}
		return this.allLinkList;
	}

	private void DiscoveryMacLinks(List<DeviceInfo> deviceInfoList) {
		List<DeviceInfo> deviceInfos = this.deviceManager.switchList;
		List<DeviceInfo> deviceVector = new ArrayList<DeviceInfo>();
		Iterator<DeviceInfo> it = deviceInfos.iterator();
		while (it.hasNext()) {
			DeviceInfo upDevice = it.next();
			try {
				deviceVector.add(upDevice);
				List<PortInfo>  portList = upDevice.getPortInfos();
				Iterator<PortInfo> it2 = portList.iterator();
				while (it2.hasNext()) {
					PortInfo port = it2.next();
					checkPortLeafDevice(port);
					List<DeviceInfo> devices = port.subDeviceList;
					Iterator<DeviceInfo> it3 = devices.iterator();
					while (it3.hasNext()) {
						DeviceInfo deviceInfo = it3.next();
						if (deviceVector.contains(deviceInfo)){
							continue;
						}
						List<PortInfo> ports = deviceInfo.getPortInfos();
						Iterator<PortInfo> it4 = ports.iterator();
						while (it4.hasNext()) {
							PortInfo portInfo2 = it4.next();
							checkTwoPortLink(port, portInfo2);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	private void DiscoveryLLDPLinks(List<DeviceInfo> deviceInfoList) {
		Iterator<DeviceInfo> it1 = deviceInfoList.iterator();
		while (it1.hasNext()) {
			DeviceInfo upDeviceInfo = it1.next();
			try {
				Iterator<LldpRemEntry> it2 = upDeviceInfo.getLldpRemEntries()
						.iterator();
				while (it2.hasNext()) {
					LldpRemEntry lldpRemEntry = it2.next();
					int port = lldpRemEntry.getLldpRemLocalPortNum();
					String macOrip = lldpRemEntry.getLldpRemChassisId();// 邻居设备MAC or IP depends on subtype
					DeviceInfo downDeviceInfo = this.deviceManager.getDeviceInfoByMAC(macOrip);
					if (downDeviceInfo == null && REUtil.isIp(macOrip)) {
						downDeviceInfo = this.deviceManager.getDeviceInfoByIp(macOrip);
					}
					if (downDeviceInfo == null ) {
						continue;//if there is not a mac then the data is meaningless;
					}
				PortInfo upPort = new PortInfo();
				upPort.device = upDeviceInfo;
				upPort.port = port;
				upPort.isUseIfIndexPort = false;

				PortInfo downPort = new PortInfo();
				downPort.device = downDeviceInfo;
				setRemoteLLDPPort(downPort, lldpRemEntry);

				boolean flag = true;// inside link
				if(upDeviceInfo!=downDeviceInfo){
					flag = false;// outside link
				}
				LinkInfo newLink = LinkInfo.creatLink(upPort, downPort, flag);
				addLink(newLink);
			}
		} catch (Exception e) {
			e.printStackTrace();
			continue;
		}
	}
}

	private void DiscoveryCDPLinks(List<DeviceInfo> deviceInfoList) {
		Iterator<DeviceInfo> it = deviceInfoList.iterator();
		while (it.hasNext()) {
			DeviceInfo upDeviceInfo = it.next();
			try {
				Iterator<CdpCacheEntry> it2 = upDeviceInfo.getCdpCacheEntries()
						.iterator();
				while (it2.hasNext()) {
					CdpCacheEntry cdpCacheEntry = it2.next();
					int ifIndex = cdpCacheEntry.getCdpCacheIfIndex();// IfIndex
					String address = cdpCacheEntry.getCdpCacheAddress();// Address
					DeviceInfo downDevice = this.deviceManager.getDeviceInfoByIp(address);
					if (downDevice == null) {
						downDevice = new DeviceInfo();
						downDevice.setDeviceIP(address);
					}
					PortInfo upPort = new PortInfo();
					upPort.device = upDeviceInfo;
					upPort.port = ifIndex;
					upPort.isUseIfIndexPort = true;
					PortInfo downPort = new PortInfo();
					downPort.device = downDevice;
					setRemoteCDPPort(downPort, cdpCacheEntry);
					boolean flag = true;//内部链接
					if(upDeviceInfo!=downDevice){
						flag = false;//外部链接
					}
					LinkInfo newLink = LinkInfo.creatLink(upPort,downPort, flag);
					addLink(newLink);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	private void DiscoveryStpLinks(List<DeviceInfo> deviceInfoList) {
		Iterator<DeviceInfo> it1 = deviceInfoList.iterator();
		while (it1.hasNext()) {
			DeviceInfo upDevice = it1.next();
			try {
				Iterator<Dot1dStpPortEntry> it2 = upDevice.getDot1dStpPortEntries()
						.iterator();
				while (it2.hasNext()) {
					Dot1dStpPortEntry stpPortEntry = it2.next();
					if (stpPortEntry.getDot1dStpPortState()!=null && stpPortEntry.getDot1dStpPortState() == 2)
					{
						Iterator<PortInfo> it3= upDevice.getPortInfos().iterator();
						while (it3.hasNext()) {
							PortInfo portInfo = it3.next();
							if (portInfo.port != stpPortEntry.getDot1dStpPort()){
								continue;
							}
							portInfo.isBlocking = true;
						}
					}
					String addr = stpPortEntry.getDot1dStpPortDesignatedBridge()==null?"":stpPortEntry.getDot1dStpPortDesignatedBridge().substring(6);
					DeviceInfo downDevice = this.deviceManager.getDeviceInfoByMAC(addr);
					if (downDevice == null) {
						downDevice = new DeviceInfo();
						String ip = this.deviceManager.getIpofMac(addr);
						if (ip == null){
							continue;
						}
						downDevice.setDeviceIP(ip);
						downDevice.setDeviceMAC(addr.toUpperCase());
					}
					PortInfo upPort = new PortInfo();
					upPort.device = upDevice;
					upPort.port = stpPortEntry.getDot1dStpPort();
					upPort.isUseIfIndexPort = false;
					String portX = stpPortEntry.getDot1dStpPortDesignatedPort();
					portX = portX.substring(portX.indexOf(":")+1);
					int j = new BigInteger(portX,16).intValue();

					PortInfo downPort = new PortInfo();
					downPort.device = downDevice;
					downPort.port = j;
					downPort.isUseIfIndexPort = false;

					boolean flag = true;//内部链接
					if(upDevice != downDevice){
						flag = false;//外部链接
					}
					LinkInfo localLinkInfo = LinkInfo.creatLink(upPort, downPort, flag);
					addLink(localLinkInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	private void DiscoveryOspfNbrLinks(List<DeviceInfo> deviceInfoList) {
		Iterator<DeviceInfo> it1 = deviceInfoList.iterator();
		while (it1.hasNext()) {
			DeviceInfo upDevice = it1.next();
			try {
				Iterator<OspfNbrEntry> it2 = upDevice.getOspfNbrEntries().iterator();
				while (it2.hasNext()) {
					OspfNbrEntry ospfNbrEntry = it2.next();
					String ip = ospfNbrEntry.getOspfNbrIpAddr();
					String id = ospfNbrEntry.getOspfNbrRtrId();// 32位整数在自治系统内唯一标识一台设备。
					DeviceInfo downDevice = this.deviceManager.getDeviceInfoByIp(ip);
					if (downDevice == null) {
						downDevice = this.deviceManager.getDeviceInfoByIp(id);
					}
					if (downDevice == null) {
						downDevice = new DeviceInfo();
						downDevice.setDeviceIP(ip);
					}
					PortInfo upPort = new PortInfo();
					upPort.device = upDevice;
					upPort.port = Constants.UnknownPort;
					upPort.isUseIfIndexPort = true;
					int j = ospfNbrEntry.getOspfNbrAddressLessIndex();
					if (j == 0) {
						j = downDevice.getIfIndexOfIPAddress(ip);
						if (-1 == j) {
							j = downDevice.getIfIndexOfIPAddress(id);
						}
					}
					PortInfo downPort = new PortInfo();
					downPort.device = downDevice;
					downPort.port = j;
					downPort.isUseIfIndexPort = true;

					boolean flag = true;//内部链接
					if(upDevice != downDevice){
						flag = false;//外部链接
					}
					LinkInfo link = LinkInfo.creatLink(upPort, downPort, flag);
					addLink(link);
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	private void DiscoveryIPRouterLinks(List<DeviceInfo> deviceInfoList) {
		Iterator<DeviceInfo> it1 = deviceInfoList.iterator();
		while (it1.hasNext()) {
			DeviceInfo upDevice = it1.next();
			try {
				Iterator<IpRouteEntry> it2 = upDevice.getIpRouteEntries().iterator();
				while (it2.hasNext()) {
					IpRouteEntry ipRouterEntry = it2.next();
					String nextHop = ipRouterEntry.getIpRouteNextHop();// 下一跳
					String routeDest = ipRouterEntry.getIpRouteDest();// 路由地址
					if (ipRouterEntry.getIpRouteType() == 3
							|| nextHop.equalsIgnoreCase(upDevice.getDeviceIP())
							|| upDevice.getIpAddrEntries().contains(nextHop)) {
						continue;
					}
					DeviceInfo downDevice = this.deviceManager
							.getDeviceInfoByIp(nextHop);
					if (downDevice == null) {
						downDevice = new DeviceInfo();
						downDevice.setDeviceIP(nextHop);
					}

					PortInfo upPort = new PortInfo();
					upPort.device = upDevice;
					upPort.port = ipRouterEntry.getIpRouteIfIndex();
					upPort.isUseIfIndexPort = true;
					int j = downDevice.getIfIndexOfIPAddress(nextHop);

					PortInfo downPort = new PortInfo();
					downPort.device = downDevice;
					downPort.port = j;
					downPort.isUseIfIndexPort = true;

					boolean flag = true;//内部链接
					if(upDevice != downDevice){
						flag = false;//外部链接
					}
					LinkInfo link = LinkInfo.creatLink(upPort, downPort, flag);
					addLink(link);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setRemoteCDPPort(PortInfo downPort,
								  CdpCacheEntry cdpCacheEntry) {
		DeviceInfo device = downPort.device;
		String port = cdpCacheEntry.getCdpCacheDevicePort();
		if (port.length() >= 1) {
			Iterator<IfEntry> it = device.getIfEntries().iterator();
			while (it.hasNext()) {
				IfEntry localMibIfEntry = it.next();
				if ((!localMibIfEntry.getIfDescr().equalsIgnoreCase(port))
						&& (!localMibIfEntry.getIfDescr()
						.equalsIgnoreCase(port))
						&& (!localMibIfEntry.getIfPhysAddress()
						.equalsIgnoreCase(port))) {
					continue;
				}
				downPort.port = localMibIfEntry.getIfIndex();
				downPort.isUseIfIndexPort = true;
				return;
			}
		}
		try {
			if(REUtil.isNumber(port)){
				downPort.port = Integer.parseInt(port);
				downPort.isUseIfIndexPort = true;
			}else{
				if(port.contains(Constants.Slash)){	//		Gi1/0/3
					String[] strs = port.split(Constants.Slash);
					if(REUtil.isNumber(strs[strs.length-1])) {
						downPort.port = Integer.parseInt(strs[strs.length - 1]);
						downPort.isUseIfIndexPort = true;
					}
				}
			}
			return ;
		} catch (Exception e) {
			downPort.port = device.getIfIndexOfIPAddress(cdpCacheEntry
					.getCdpCacheAddress());
			downPort.isUseIfIndexPort = true;
		}
	}

	private void setRemoteLLDPPort(PortInfo downPort, LldpRemEntry lldpRemEntry) {
		String portId = lldpRemEntry.getLldpRemPortId();// 端口ID GigabitEthernet1/0/11
		String portDesc = lldpRemEntry.getLldpRemPortDesc();// 端口描述 GigabitEthernet1/0/11 Interface

		if (Constants.UnknownPort == downPort.port && portId.length() >= 1 && downPort.device.getIfEntries() != null) {
			Iterator<IfEntry> it = downPort.device.getIfEntries().iterator();
			while (it.hasNext()) {
				IfEntry ifEntry = it.next();
				if (!ifEntry.getIfDescr().equalsIgnoreCase(portId)
						&& !ifEntry.getIfPhysAddress().equalsIgnoreCase(portId)) {
					continue;
				}
				downPort.port = ifEntry.getIfIndex();
				downPort.isUseIfIndexPort = true;
				break;
			}
		}

		if (Constants.UnknownPort == downPort.port && portDesc.length() >= 1 && downPort.device.getIfEntries() != null) {
			Iterator<IfEntry> it = downPort.device.getIfEntries().iterator();
			while (it.hasNext()) {
				IfEntry ifEntry = it.next();
				if (!ifEntry.getIfDescr().equalsIgnoreCase(portDesc)
						&& !ifEntry.getIfPhysAddress().equalsIgnoreCase(
						portDesc)) {
					continue;
				}
				downPort.port = ifEntry.getIfIndex();
				downPort.isUseIfIndexPort = true;
				break;
			}
		}

		if ((Constants.UnknownPort == downPort.port) && (portId.length() >= 1) && downPort.device.getLldpLocPortEntries() != null) {
			Iterator<LldpLocPortEntry> it = downPort.device.getLldpLocPortEntries().iterator();
			while (it.hasNext()) {
				LldpLocPortEntry lldpLocPortEntry = it.next();
				if (!lldpLocPortEntry.getLldpLocPortId().equalsIgnoreCase(
						portId)) {
					continue;
				}
				downPort.port = lldpLocPortEntry.getLldpLocPortNum();
				downPort.isUseIfIndexPort = false;
				break;
			}
		}

		try {
			if(REUtil.isNumber(portId)){
				downPort.port = Integer.parseInt(portId);
				downPort.isUseIfIndexPort = false;
			}else{
				if(portId.contains(Constants.Slash)){	//		Gi1/0/3
					String[] strs = portId.split(Constants.Slash);
					if(REUtil.isNumber(strs[strs.length-1])) {
						downPort.port = Integer.parseInt(strs[strs.length - 1]);
						downPort.isUseIfIndexPort = false;
					}
				}
			}
		} catch (Exception e) {
		}

		if (Constants.UnknownPort == downPort.port && downPort.device.getDot1dBasePortEntries() != null) {
			Iterator<Dot1dBasePortEntry> it = downPort.device.getDot1dBasePortEntries().iterator();
			while (it.hasNext()) {
				Dot1dBasePortEntry localDot1dBasePortEntry = it.next();
				int i = localDot1dBasePortEntry.getDot1dBasePort();
				if (!portId.contains(i + "")) {
					continue;
				}
				downPort.port = i;
				downPort.isUseIfIndexPort = false;
				break;
			}
		}
	}

	private void addLink(LinkInfo linkInfo) {

		if (linkInfo.startDevice.equals(linkInfo.endDevice)) {
			return;
		}
		if (this.allLinkList.contains(linkInfo)) {
			return;
		}
		System.out.println("发现链路"+linkInfo.toString());
		ListIterator<LinkInfo> it = this.allLinkList.listIterator();
		while (it.hasNext()) {
			LinkInfo link = it.next();
			int option = link.checkFuzzyOrAccurate(linkInfo);
			if (option == Constants.RemoveOld) {
				it.remove();
			} else {
				if (option != Constants.DropNew) {
					continue;
				}
				return;
			}
		}
		this.allLinkList.add(linkInfo);
	}

	private void checkPortLeafDevice(PortInfo upPort) {
		if (upPort.subDeviceList.size() == 1) {
			DeviceInfo deviceInfo = upPort.subDeviceList.get(0);
			PortInfo downPort = new PortInfo();
			downPort.device = deviceInfo ;
			downPort.port = Constants.UnknownPort;
			LinkInfo linkInfo = LinkInfo.creatLink(upPort, downPort, true);
			addLink(linkInfo);
			return;
		}
		if (upPort.portMacList.size() == 1) {
			String mac = upPort.portMacList.get(0);
			if (this.deviceManager.getDeviceInfoByMAC(mac) == null) {
				String ip = this.deviceManager.getIpofMac(mac);
				if (ip != null) {
					DeviceInfo deviceInfo = new DeviceInfo();
					deviceInfo.setDeviceIP(ip);
					deviceInfo.setDeviceMAC(mac.toUpperCase());
					PortInfo downPort = new PortInfo();
					downPort.device = (deviceInfo);
					downPort.port = Constants.UnknownPort;
					LinkInfo link = LinkInfo.creatLink(upPort,downPort, true);
					addLink(link);
				} else {
					DeviceInfo deviceInfo = new DeviceInfo();
					deviceInfo.setDeviceIP("");
					deviceInfo.setDeviceMAC(mac.toUpperCase());
					PortInfo downPort = new PortInfo();
					downPort.device = deviceInfo;
					downPort.port = Constants.UnknownPort;
					LinkInfo link = LinkInfo.creatLink(upPort,downPort, false);
					addLink(link);
				}
			}
		}
	}

	private void checkTwoPortLink(PortInfo port1, PortInfo port2) {
		DeviceInfo device1 = port1.device;
		List<DeviceInfo> connDeviceList1 = port1.subDeviceList;
		DeviceInfo device2 = port2.device;
		List<DeviceInfo> connDeviceList2 = port2.subDeviceList;

		if (connDeviceList1.contains(device2) && connDeviceList2.contains(device1)) {
			ArrayList<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
			Iterator<DeviceInfo>  it = connDeviceList1.iterator();
			while (it.hasNext()) {
				DeviceInfo device = it.next();
				if (!connDeviceList2.contains(device)){
					continue;
				}
				deviceInfoList.add(device);
			}
			if (deviceInfoList.size() <= 0) {
				LinkInfo link = LinkInfo.creatLink(port1,port2, true);
				addLink(link);

			} else if (deviceInfoList.size() == 1) {
				DeviceInfo device = deviceInfoList.get(0);
				PortInfo port = new PortInfo();
				port.device = device;
				port.port = Constants.UnknownPort;
				LinkInfo link1 = LinkInfo.creatLink(port1, port, true);
				LinkInfo link2 = LinkInfo.creatLink(port2, port, true);
				addLink(link1);
				addLink(link2);
			}
		}
	}
}