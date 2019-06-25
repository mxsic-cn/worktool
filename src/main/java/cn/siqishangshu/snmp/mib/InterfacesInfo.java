package cn.siqishangshu.snmp.mib;

import cn.siqishangshu.snmp.util.SNMPOID;

import java.util.List;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.2")
public class InterfacesInfo {
    @SNMPOID("1")
    public int ifNumber;
    @SNMPOID("2")
    public List<IfEntry> ifEntry;

    public int getIfNumber() {
        return ifNumber;
    }

    public void setIfNumber(int ifNumber) {
        this.ifNumber = ifNumber;
    }

    public List<IfEntry> getIfEntry() {
        return ifEntry;
    }

    public void setIfEntry(List<IfEntry> ifEntry) {
        this.ifEntry = ifEntry;
    }

    @Override
    public String toString() {
        return "InterfacesInfo{" +
                "ifNumber=" + ifNumber +
                ", ifEntry=" + ifEntry +
                '}';
    }
}