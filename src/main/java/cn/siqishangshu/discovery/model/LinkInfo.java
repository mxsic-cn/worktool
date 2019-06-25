package cn.siqishangshu.discovery.model;

import cn.siqishangshu.discovery.util.ComUtil;
import cn.siqishangshu.discovery.util.Constants;

import java.io.Serializable;

public class LinkInfo implements Serializable {

	private static final long serialVersionUID = 3516554595871814117L;

	public DeviceInfo startDevice;
	public DeviceInfo endDevice;
	public PortInfo startPortInfo;
	public PortInfo endPortInfo;
	public int startPort = Constants.UnknownPort;
	public int endPort = Constants.UnknownPort;
	public String linkType = Constants.LinkTypeInbound;

	public static LinkInfo creatLink(PortInfo startPort,
			PortInfo endPort, boolean isInLink) {
		DeviceInfo startDevice = startPort.device;
		int i = startPort.port;
		DeviceInfo endDevice = endPort.device;
		int j = endPort.port;
		LinkInfo localLinkInfo = new LinkInfo();
		if (!isInLink)
			localLinkInfo.linkType = Constants.LinkTypeOutbound;
		if ((startDevice.getDeviceIP().compareTo(endDevice.getDeviceIP()) <= 0) || (!isInLink)) {
			localLinkInfo.startDevice = startDevice;
			localLinkInfo.startPortInfo = startPort;
			localLinkInfo.startPort = i;
			localLinkInfo.endDevice = endDevice;
			localLinkInfo.endPortInfo = endPort;
			localLinkInfo.endPort = j;
		} else {
			localLinkInfo.startDevice = endDevice;
			localLinkInfo.startPortInfo = endPort;
			localLinkInfo.startPort = j;
			localLinkInfo.endDevice = startDevice;
			localLinkInfo.endPortInfo = startPort;
			localLinkInfo.endPort = i;
		}
		return localLinkInfo;
	}

	public String toString() {
		return this.linkType
				+ ","
				+ this.startDevice
				+ ",端口："
				+ (this.startPort == -1 ? "未知" : Integer
						.valueOf(this.startPort)) + ",MAC:"
				+ this.startDevice.getDeviceMAC() + " <------> "
				+ this.endDevice + ",端口："
				+ (this.endPort == -1 ? "未知" : Integer.valueOf(this.endPort))
				+ ",MAC:" + this.endDevice.getDeviceMAC();
	}

	public boolean equals(Object paramObject) {
		if ((paramObject instanceof LinkInfo)) {
			LinkInfo localLinkInfo = (LinkInfo) paramObject;
			if ((this.startDevice.equals(localLinkInfo.startDevice))
					&& (this.startPort == localLinkInfo.startPort)
					&& (this.endDevice.equals(localLinkInfo.endDevice))
					&& (this.endPort == localLinkInfo.endPort))
				return true;
			if ((this.startDevice.equals(localLinkInfo.endDevice))
					&& (this.startPort == localLinkInfo.endPort)
					&& (this.endDevice.equals(localLinkInfo.startDevice))
					&& (this.endPort == localLinkInfo.startPort))
				return true;
		}
		return false;
	}

	public int checkFuzzyOrAccurate(LinkInfo newLink) {
		if (equals(newLink))
			return Constants.DropNew;
		if ((newLink.startDevice.equals(this.startDevice))
				&& (newLink.endDevice.equals(this.endDevice))) {
			if (ComUtil.isUnknownPort(this.startPort) && ComUtil.isUnknownPort(this.endPort))
				return Constants.RemoveOld;
			if (ComUtil.isUnknownPort(this.startPort) && !ComUtil.isUnknownPort(newLink.startPort)
					&& (newLink.endPort == this.endPort))
				return Constants.RemoveOld;
			if (ComUtil.isUnknownPort(this.endPort) && !ComUtil.isUnknownPort(newLink.endPort)
					&& (newLink.startPort == this.startPort))
				return Constants.RemoveOld;
			if (ComUtil.isUnknownPort(newLink.startPort) && ComUtil.isUnknownPort(newLink.endPort))
				return Constants.DropNew;
			if (  !ComUtil.isUnknownPort(this.startPort) &&   !ComUtil.isUnknownPort(this.endPort)) {
				if ((this.startPort == newLink.startPort) && ComUtil.isUnknownPort(newLink.endPort))
					return Constants.DropNew;
				if ((this.endPort == newLink.endPort) && ComUtil.isUnknownPort(newLink.startPort))
					return Constants.DropNew;
			}
			if (ComUtil.isUnknownPort(this.startPort) && ComUtil.isUnknownPort(newLink.endPort)) {
				newLink.endPort = this.endPort;
				newLink.endPortInfo.port = this.endPort;
				return Constants.RemoveOld;
			}
			if (ComUtil.isUnknownPort(this.endPort) && ComUtil.isUnknownPort(newLink.startPort)) {
				newLink.startPort = this.startPort;
				newLink.startPortInfo.port = this.startPort;
				return Constants.RemoveOld;
			}
			return Constants.NextOne;
		}
		return Constants.NextOne;
	}
}