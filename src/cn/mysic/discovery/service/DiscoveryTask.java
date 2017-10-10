package cn.mysic.discovery.service;

import cn.mysic.discovery.model.DeviceInfo;
import cn.mysic.discovery.model.DiscoveryConfig;
import cn.mysic.log.LogUtil;
import cn.mysic.snmp.util.SNMPConfig;

import java.util.List;

public class DiscoveryTask implements Runnable {
	private String ip;
	private List<SNMPConfig> configList;
	private List<DeviceInfo> rsList;
	private DiscoveryConfig discoveryConfig;

	public DiscoveryTask(String ip, List<SNMPConfig> configList,
						 List<DeviceInfo> deviceInfoList,  DiscoveryConfig discoveryConfig) {
		this.configList = configList;
		this.ip = ip;
		this.rsList = deviceInfoList;
		this.discoveryConfig = discoveryConfig;
	}

	public void run() {
		try {
				DeviceInfo deviceInfo = new DiscoveryDeviceInfoService().
						getDeviceBasicInfo(this.ip, this.configList, this.discoveryConfig);
				if (deviceInfo != null) {
					synchronized (this.rsList) {
						if (!this.rsList.contains(deviceInfo)){
							for (DeviceInfo device : this.rsList) {
								if (device.getDeviceIps().contains(this.ip) || (deviceInfo.getDeviceMAC() != null
										&& device.getDeviceMacs().contains(deviceInfo.getDeviceMAC()))) {
									return;
								}
							}
							LogUtil.writeSqllog(deviceInfo.getDeviceIP() + "--->"+ deviceInfo.getDeviceMAC() + "--->" + deviceInfo.getDeviceName()+"  IPS:"+deviceInfo.getDeviceIps()+ "  MACS:"+deviceInfo.getDeviceMacs(),"Device.txt");
							this.rsList.add(deviceInfo);
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}