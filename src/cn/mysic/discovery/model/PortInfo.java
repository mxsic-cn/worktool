package cn.mysic.discovery.model;

import cn.mysic.discovery.util.Constants;

import java.util.ArrayList;
import java.util.List;


public class PortInfo {
	public DeviceInfo device;
	public int port = Constants.UnknownPort;
	public boolean isUseIfIndexPort = false;
	public boolean isBlocking = false;
	public List<String> portMacList = new ArrayList<String>();
	public List<DeviceInfo> subDeviceList = new ArrayList<DeviceInfo>();
	public String toString() {
		return this.device + "-->" + this.port;
	}

 	public int hashCode() {
		int j = 1;
		j = 31 * j + (this.device == null ? 0 : this.device.hashCode());
		j = 31 * j + this.port;
		return j;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PortInfo)) {
			return false;
		}
		PortInfo portInfo = (PortInfo) obj;
		if (this.device == null) {
			if (portInfo.device != null) {
				return false;
			}
		} else if (!this.device.equals(portInfo.device)) {
			return false;
		}
		return this.port == portInfo.port;
	}
}
