package cn.mxsic.discovery.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import cn.mxsic.discovery.util.Constants;
import cn.mxsic.snmp.mib.CdpCacheEntry;
import cn.mxsic.snmp.mib.Dot1dBasePortEntry;
import cn.mxsic.snmp.mib.Dot1dStpPortEntry;
import cn.mxsic.snmp.mib.Dot1dTpFdbEntry;
import cn.mxsic.snmp.mib.IfEntry;
import cn.mxsic.snmp.mib.IpAddrEntry;
import cn.mxsic.snmp.mib.IpNetToMediaEntry;
import cn.mxsic.snmp.mib.IpRouteEntry;
import cn.mxsic.snmp.mib.LldpLocPortEntry;
import cn.mxsic.snmp.mib.LldpRemEntry;
import cn.mxsic.snmp.mib.OspfNbrEntry;
import cn.mxsic.snmp.mib.SystemInfo;
import cn.mxsic.snmp.mib.TcpConnEntry;
import cn.mxsic.snmp.mib.UdpEntry;
import cn.mxsic.snmp.util.SNMPConfig;
public class DeviceInfo {
	private String deviceIP = "";  //IP地址
	private String deviceMAC = ""; //MAC地址
	private String deviceProtocol = ""; //发现协议
	private String deviceName = ""; //名称
	private SNMPConfig snmpConfig = new SNMPConfig();        //SNMPConfig
	private String deviceType = Constants.DeviceTypeUnkown;// 类型
	private SystemInfo systemInfo = new SystemInfo();
	private Set<String> deviceMacs = new HashSet<>();//MAC地址列表
	private Set<String> deviceIps = new HashSet<>();//IP地址列表
	private List<PortInfo> portInfos = new ArrayList<PortInfo>();//端口信息集合
	private List<IfEntry> ifEntries = new ArrayList<IfEntry>();//端口表
	private List<IpAddrEntry> ipAddrEntries = new ArrayList<IpAddrEntry>();//IP地址列表
	private List<IpNetToMediaEntry> ipNetToMediaEntries = new ArrayList<IpNetToMediaEntry>();//ARP表
	private List<IpRouteEntry> ipRouteEntries = new ArrayList<IpRouteEntry>();//路由表
	private List<TcpConnEntry> tcpConnEntries = new ArrayList<TcpConnEntry>();//TCP连接表
	private List<UdpEntry> udpEntries = new ArrayList<UdpEntry>();//UDP连接表
	private List<LldpRemEntry> lldpRemEntries = new ArrayList<LldpRemEntry>();//LLDP
	private List<LldpLocPortEntry> lldpLocPortEntries = new ArrayList<LldpLocPortEntry>();//LLDP
	private List<CdpCacheEntry> cdpCacheEntries = new ArrayList<CdpCacheEntry>();//CDP
	private List<Dot1dBasePortEntry> dot1dBasePortEntries = new ArrayList<Dot1dBasePortEntry>();//交换机端口基本信息
	private List<Dot1dStpPortEntry> dot1dStpPortEntries = new ArrayList<Dot1dStpPortEntry>();//STP生成树
	private List<OspfNbrEntry> ospfNbrEntries = new ArrayList<OspfNbrEntry>();//开放式最短路径优先
	private List<Dot1dTpFdbEntry> dot1dTpFdbEntries = new ArrayList<Dot1dTpFdbEntry>();// 交换机转发MAC地址和状态

	public String toString(){
		return "deviceIP:"+deviceIP + ";deviceMAC:" +deviceMAC+";deviceName:"+systemInfo.getSysName()+";deviceType:"+deviceType+" ";
	}
	
	/**
	 * 
	 * 根据IP查询对应端口信息
	 * 
	 * @param IP
	 * @return
	 */
	public int getIfIndexOfIPAddress(String IP) {
		Iterator<IpAddrEntry> localIterator = getIpAddrEntries().iterator();
		while (localIterator.hasNext()) {
			IpAddrEntry ipAddrEntry = localIterator.next();
			if (ipAddrEntry.getIpAdEntAddr().equals(IP))
				return ipAddrEntry.getIpAdEntIfIndex();
		}
		return -1;
	}

	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public String getDeviceMAC() {
		return deviceMAC;
	}

	public void setDeviceMAC(String deviceMAC) {
		this.deviceMAC = deviceMAC;
	}

	public String getDeviceProtocol() {
		return deviceProtocol;
	}

	public void setDeviceProtocol(String deviceProtocol) {
		this.deviceProtocol = deviceProtocol;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public SNMPConfig getSnmpConfig() {
		return snmpConfig;
	}

	public void setSnmpConfig(SNMPConfig snmpConfig) {
		this.snmpConfig = snmpConfig;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public SystemInfo getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}

	public Set<String> getDeviceMacs() {
		return deviceMacs;
	}

	public void setDeviceMacs(Set<String> deviceMacs) {
		this.deviceMacs = deviceMacs;
	}

	public Set<String> getDeviceIps() {
		return deviceIps;
	}

	public void setDeviceIps(Set<String> deviceIps) {
		this.deviceIps = deviceIps;
	}

	public List<PortInfo> getPortInfos() {
		return portInfos;
	}

	public void setPortInfos(List<PortInfo> portInfos) {
		this.portInfos = portInfos;
	}

	public List<IfEntry> getIfEntries() {
		return ifEntries;
	}

	public void setIfEntries(List<IfEntry> ifEntries) {
		this.ifEntries = ifEntries;
	}

	public List<IpAddrEntry> getIpAddrEntries() {
		return ipAddrEntries;
	}

	public void setIpAddrEntries(List<IpAddrEntry> ipAddrEntries) {
		this.ipAddrEntries = ipAddrEntries;
	}

	public List<IpNetToMediaEntry> getIpNetToMediaEntries() {
		return ipNetToMediaEntries;
	}

	public void setIpNetToMediaEntries(List<IpNetToMediaEntry> ipNetToMediaEntries) {
		this.ipNetToMediaEntries = ipNetToMediaEntries;
	}

	public List<IpRouteEntry> getIpRouteEntries() {
		return ipRouteEntries;
	}

	public void setIpRouteEntries(List<IpRouteEntry> ipRouteEntries) {
		this.ipRouteEntries = ipRouteEntries;
	}

	public List<TcpConnEntry> getTcpConnEntries() {
		return tcpConnEntries;
	}

	public void setTcpConnEntries(List<TcpConnEntry> tcpConnEntries) {
		this.tcpConnEntries = tcpConnEntries;
	}

	public List<UdpEntry> getUdpEntries() {
		return udpEntries;
	}

	public void setUdpEntries(List<UdpEntry> udpEntries) {
		this.udpEntries = udpEntries;
	}

	public List<LldpRemEntry> getLldpRemEntries() {
		return lldpRemEntries;
	}

	public void setLldpRemEntries(List<LldpRemEntry> lldpRemEntries) {
		this.lldpRemEntries = lldpRemEntries;
	}

	public List<LldpLocPortEntry> getLldpLocPortEntries() {
		return lldpLocPortEntries;
	}

	public void setLldpLocPortEntries(List<LldpLocPortEntry> lldpLocPortEntries) {
		this.lldpLocPortEntries = lldpLocPortEntries;
	}

	public List<CdpCacheEntry> getCdpCacheEntries() {
		return cdpCacheEntries;
	}

	public void setCdpCacheEntries(List<CdpCacheEntry> cdpCacheEntries) {
		this.cdpCacheEntries = cdpCacheEntries;
	}

	public List<Dot1dBasePortEntry> getDot1dBasePortEntries() {
		return dot1dBasePortEntries;
	}

	public void setDot1dBasePortEntries(List<Dot1dBasePortEntry> dot1dBasePortEntries) {
		this.dot1dBasePortEntries = dot1dBasePortEntries;
	}

	public List<Dot1dStpPortEntry> getDot1dStpPortEntries() {
		return dot1dStpPortEntries;
	}

	public void setDot1dStpPortEntries(List<Dot1dStpPortEntry> dot1dStpPortEntries) {
		this.dot1dStpPortEntries = dot1dStpPortEntries;
	}

	public List<OspfNbrEntry> getOspfNbrEntries() {
		return ospfNbrEntries;
	}

	public void setOspfNbrEntries(List<OspfNbrEntry> ospfNbrEntries) {
		this.ospfNbrEntries = ospfNbrEntries;
	}

	public List<Dot1dTpFdbEntry> getDot1dTpFdbEntries() {
		return dot1dTpFdbEntries;
	}

	public void setDot1dTpFdbEntries(List<Dot1dTpFdbEntry> dot1dTpFdbEntries) {
		this.dot1dTpFdbEntries = dot1dTpFdbEntries;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DeviceInfo that = (DeviceInfo) o;
		return Objects.equals(deviceIP, that.deviceIP) &&
				Objects.equals(deviceMAC, that.deviceMAC);
	}

	@Override
	public int hashCode() {
		return Objects.hash(deviceIP, deviceMAC, deviceProtocol, deviceName);
	}
}