package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.4.22.1")
public class IpNetToMediaEntry {
    @SNMPOID("1")
    public int ipNetToMediaIfIndex;
    @SNMPOID("2")
    public String ipNetToMediaPhysAddress;
    @SNMPOID("3")
    public String ipNetToMediaNetAddress;
    @SNMPOID("4")
    public int ipNetToMediaType;

    public int getIpNetToMediaIfIndex() {
        return ipNetToMediaIfIndex;
    }

    public void setIpNetToMediaIfIndex(int ipNetToMediaIfIndex) {
        this.ipNetToMediaIfIndex = ipNetToMediaIfIndex;
    }

    public String getIpNetToMediaPhysAddress() {
        return ipNetToMediaPhysAddress;
    }

    public void setIpNetToMediaPhysAddress(String ipNetToMediaPhysAddress) {
        this.ipNetToMediaPhysAddress = ipNetToMediaPhysAddress;
    }

    public String getIpNetToMediaNetAddress() {
        return ipNetToMediaNetAddress;
    }

    public void setIpNetToMediaNetAddress(String ipNetToMediaNetAddress) {
        this.ipNetToMediaNetAddress = ipNetToMediaNetAddress;
    }

    public int getIpNetToMediaType() {
        return ipNetToMediaType;
    }

    public void setIpNetToMediaType(int ipNetToMediaType) {
        this.ipNetToMediaType = ipNetToMediaType;
    }

    @Override
    public String toString() {
        return "IpNetToMediaEntry{" +
                "ipNetToMediaIfIndex=" + ipNetToMediaIfIndex +
                ", ipNetToMediaPhysAddress='" + ipNetToMediaPhysAddress + '\'' +
                ", ipNetToMediaNetAddress='" + ipNetToMediaNetAddress + '\'' +
                ", ipNetToMediaType=" + ipNetToMediaType +
                '}';
    }
}
