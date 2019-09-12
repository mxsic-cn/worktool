package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.3.1.1")
public class AtEntry {
    @SNMPOID("1")
    public int atIfIndex;
    @SNMPOID("2")
    public String atPhysAddress;
    @SNMPOID("3")
    public String atNetAddress;

    public int getAtIfIndex() {
        return atIfIndex;
    }

    public void setAtIfIndex(int atIfIndex) {
        this.atIfIndex = atIfIndex;
    }

    public String getAtPhysAddress() {
        return atPhysAddress;
    }

    public void setAtPhysAddress(String atPhysAddress) {
        this.atPhysAddress = atPhysAddress;
    }

    public String getAtNetAddress() {
        return atNetAddress;
    }

    public void setAtNetAddress(String atNetAddress) {
        this.atNetAddress = atNetAddress;
    }

    @Override
    public String toString() {
        return "AtEntry{" +
                "atIfIndex=" + atIfIndex +
                ", atPhysAddress='" + atPhysAddress + '\'' +
                ", atNetAddress='" + atNetAddress + '\'' +
                '}';
    }
}
