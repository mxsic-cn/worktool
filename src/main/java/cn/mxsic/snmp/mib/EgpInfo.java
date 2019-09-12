package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

import java.util.List;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.java8")
public class EgpInfo {
    @SNMPOID("1")
    public long egpInMsgs;
    @SNMPOID("2")
    public long egpInErrors;
    @SNMPOID("3")
    public long egpOutMsgs;
    @SNMPOID("4")
    public long egpOutErrors;
    @SNMPOID("5")
    public List<EgpNeighEntry> egpNeighEntry;
    @SNMPOID("6")
    public int egpAs;


    public long getEgpInMsgs() {
        return egpInMsgs;
    }

    public void setEgpInMsgs(long egpInMsgs) {
        this.egpInMsgs = egpInMsgs;
    }

    public long getEgpInErrors() {
        return egpInErrors;
    }

    public void setEgpInErrors(long egpInErrors) {
        this.egpInErrors = egpInErrors;
    }

    public long getEgpOutMsgs() {
        return egpOutMsgs;
    }

    public void setEgpOutMsgs(long egpOutMsgs) {
        this.egpOutMsgs = egpOutMsgs;
    }

    public long getEgpOutErrors() {
        return egpOutErrors;
    }

    public void setEgpOutErrors(long egpOutErrors) {
        this.egpOutErrors = egpOutErrors;
    }

    public List<EgpNeighEntry> getEgpNeighEntry() {
        return egpNeighEntry;
    }

    public void setEgpNeighEntry(List<EgpNeighEntry> egpNeighEntry) {
        this.egpNeighEntry = egpNeighEntry;
    }

    public int getEgpAs() {
        return egpAs;
    }

    public void setEgpAs(int egpAs) {
        this.egpAs = egpAs;
    }

    @Override
    public String toString() {
        return "EgpInfo{" +
                "egpInMsgs=" + egpInMsgs +
                ", egpInErrors=" + egpInErrors +
                ", egpOutMsgs=" + egpOutMsgs +
                ", egpOutErrors=" + egpOutErrors +
                ", egpNeighEntry=" + egpNeighEntry +
                ", egpAs=" + egpAs +
                '}';
    }
}
