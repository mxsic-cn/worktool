package cn.mysic.snmp.mib;

import cn.mysic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.7.5.1")
public class UdpEntry {
    @SNMPOID("1")
    public String udpLocalAddress;
    @SNMPOID("2")
    public int udpLocalPort;

    public String getUdpLocalAddress() {
        return udpLocalAddress;
    }

    public void setUdpLocalAddress(String udpLocalAddress) {
        this.udpLocalAddress = udpLocalAddress;
    }

    public int getUdpLocalPort() {
        return udpLocalPort;
    }

    public void setUdpLocalPort(int udpLocalPort) {
        this.udpLocalPort = udpLocalPort;
    }

    @Override
    public String toString() {
        return "UdpEntry{" +
                "udpLocalAddress='" + udpLocalAddress + '\'' +
                ", udpLocalPort=" + udpLocalPort +
                '}';
    }
}
