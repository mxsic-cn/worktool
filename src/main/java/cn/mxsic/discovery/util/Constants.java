package cn.mxsic.discovery.util;

public class Constants {
	// 发现协议
	public static final String DeviceProtocolSNMP = "SNMP";
	public static final String DeviceProtocolPing = "Ping";

	// 发现设备类型
	public static final String DeviceTypeUnkown = "未知设备";
	public static final String DeviceTypeSwitch = "交换机";
	public static final String DeviceTypeRouter = "路由器";
	public static final String DeviceTypeComputer = "计算机";
	public static final String LinkTypeInbound = "内部链路";
	public static final String LinkTypeOutbound = "外部链路";
	public static final int UnknownPort = -1;
	public static final int RemoveOld = 1;
	public static final int DropNew = 0;
	public static final int NextOne = -1;

	public static final String SwitchMacOid = ".1.3.6.1.2.1.17.1.1.0";
	public static final String GatewayOid = ".1.3.6.1.2.1.4.1.0";//ipForwarding
	public static final String PatternMac = new String("^[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]:[0-9A-F][0-9A-F]?$");
	public static final String PatternIp = new String("^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])\\.){1,3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9]?[0-9])?$");
	public static final String Number = new String("^[0-9]*$");

	public static final String Slash = "/";
}
