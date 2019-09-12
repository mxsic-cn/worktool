package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.4.20.1")
public class IpAddrEntry {
    @SNMPOID("1")
    public String ipAdEntAddr;
    @SNMPOID("2")
    public int ipAdEntIfIndex;
    @SNMPOID("3")
    public String ipAdEntNetMask;
    @SNMPOID("4")
    public int ipAdEntBcastAddr;
    @SNMPOID("5")
    public int ipAdEntReasmMaxSize;

    public String getIpAdEntAddr() {
        return ipAdEntAddr;
    }

    public void setIpAdEntAddr(String ipAdEntAddr) {
        this.ipAdEntAddr = ipAdEntAddr;
    }

    public int getIpAdEntIfIndex() {
        return ipAdEntIfIndex;
    }

    public void setIpAdEntIfIndex(int ipAdEntIfIndex) {
        this.ipAdEntIfIndex = ipAdEntIfIndex;
    }

    public String getIpAdEntNetMask() {
        return ipAdEntNetMask;
    }

    public void setIpAdEntNetMask(String ipAdEntNetMask) {
        this.ipAdEntNetMask = ipAdEntNetMask;
    }

    public int getIpAdEntBcastAddr() {
        return ipAdEntBcastAddr;
    }

    public void setIpAdEntBcastAddr(int ipAdEntBcastAddr) {
        this.ipAdEntBcastAddr = ipAdEntBcastAddr;
    }

    public int getIpAdEntReasmMaxSize() {
        return ipAdEntReasmMaxSize;
    }

    public void setIpAdEntReasmMaxSize(int ipAdEntReasmMaxSize) {
        this.ipAdEntReasmMaxSize = ipAdEntReasmMaxSize;
    }

    @Override
    public String toString() {
        return "IpAddrEntry{" +
                "ipAdEntAddr='" + ipAdEntAddr + '\'' +
                ", ipAdEntIfIndex=" + ipAdEntIfIndex +
                ", ipAdEntNetMask='" + ipAdEntNetMask + '\'' +
                ", ipAdEntBcastAddr=" + ipAdEntBcastAddr +
                ", ipAdEntReasmMaxSize=" + ipAdEntReasmMaxSize +
                '}';
    }
}
