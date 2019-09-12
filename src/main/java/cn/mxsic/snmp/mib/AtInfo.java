package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

import java.util.List;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.3")
public class AtInfo {
    @SNMPOID("1")
    public List<AtEntry> atEntry;

    public List<AtEntry> getAtEntry() {
        return atEntry;
    }

    public void setAtEntry(List<AtEntry> atEntry) {
        this.atEntry = atEntry;
    }

    @Override
    public String toString() {
        return "AtInfo{" +
                "atEntry=" + atEntry +
                '}';
    }
}
