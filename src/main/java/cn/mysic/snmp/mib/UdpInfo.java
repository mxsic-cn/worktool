package cn.mysic.snmp.mib;

import cn.mysic.snmp.util.SNMPOID;

import java.util.List;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.7")
public class UdpInfo {
    @SNMPOID("1")
    public long udpInDatagrams;
    @SNMPOID("2")
    public long udpNoPorts;
    @SNMPOID("3")
    public long udpInErrors;
    @SNMPOID("4")
    public long udpOutDatagrams;
    @SNMPOID("5")
    public List<UdpEntry> udpEntry;

    public long getUdpInDatagrams() {
        return udpInDatagrams;
    }

    public void setUdpInDatagrams(long udpInDatagrams) {
        this.udpInDatagrams = udpInDatagrams;
    }

    public long getUdpNoPorts() {
        return udpNoPorts;
    }

    public void setUdpNoPorts(long udpNoPorts) {
        this.udpNoPorts = udpNoPorts;
    }

    public long getUdpInErrors() {
        return udpInErrors;
    }

    public void setUdpInErrors(long udpInErrors) {
        this.udpInErrors = udpInErrors;
    }

    public long getUdpOutDatagrams() {
        return udpOutDatagrams;
    }

    public void setUdpOutDatagrams(long udpOutDatagrams) {
        this.udpOutDatagrams = udpOutDatagrams;
    }

    public List<UdpEntry> getUdpEntry() {
        return udpEntry;
    }

    public void setUdpEntry(List<UdpEntry> udpEntry) {
        this.udpEntry = udpEntry;
    }

    @Override
    public String toString() {
        return "UdpInfo{" +
                "udpInDatagrams=" + udpInDatagrams +
                ", udpNoPorts=" + udpNoPorts +
                ", udpInErrors=" + udpInErrors +
                ", udpOutDatagrams=" + udpOutDatagrams +
                ", udpEntry=" + udpEntry +
                '}';
    }
}
