package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.java8.5.1")
public class EgpNeighEntry {
    @SNMPOID("1")
    public int egpNeighState;
    @SNMPOID("2")
    public String egpNeighAddr;
    @SNMPOID("3")
    public int egpNeighAs;
    @SNMPOID("4")
    public long egpNeighInMsgs;
    @SNMPOID("5")
    public long egpNeighInErrs;
    @SNMPOID("6")
    public long egpNeighOutMsgs;
    @SNMPOID("7")
    public long egpNeighOutErrs;
    @SNMPOID("8")
    public long egpNeighInErrMsgs;
    @SNMPOID("9")
    public long egpNeighOutErrMsgs;
    @SNMPOID("10")
    public long egpNeighStateUps;
    @SNMPOID("11")
    public long egpNeighStateDowns;
    @SNMPOID("12")
    public int egpNeighIntervalHello;
    @SNMPOID("13")
    public int egpNeighIntervalPoll;
    @SNMPOID("14")
    public int egpNeighMode;
    @SNMPOID("15")
    public int egpNeighEventTrigger;

    public int getEgpNeighState() {
        return egpNeighState;
    }

    public void setEgpNeighState(int egpNeighState) {
        this.egpNeighState = egpNeighState;
    }

    public String getEgpNeighAddr() {
        return egpNeighAddr;
    }

    public void setEgpNeighAddr(String egpNeighAddr) {
        this.egpNeighAddr = egpNeighAddr;
    }

    public int getEgpNeighAs() {
        return egpNeighAs;
    }

    public void setEgpNeighAs(int egpNeighAs) {
        this.egpNeighAs = egpNeighAs;
    }

    public long getEgpNeighInMsgs() {
        return egpNeighInMsgs;
    }

    public void setEgpNeighInMsgs(long egpNeighInMsgs) {
        this.egpNeighInMsgs = egpNeighInMsgs;
    }

    public long getEgpNeighInErrs() {
        return egpNeighInErrs;
    }

    public void setEgpNeighInErrs(long egpNeighInErrs) {
        this.egpNeighInErrs = egpNeighInErrs;
    }

    public long getEgpNeighOutMsgs() {
        return egpNeighOutMsgs;
    }

    public void setEgpNeighOutMsgs(long egpNeighOutMsgs) {
        this.egpNeighOutMsgs = egpNeighOutMsgs;
    }

    public long getEgpNeighOutErrs() {
        return egpNeighOutErrs;
    }

    public void setEgpNeighOutErrs(long egpNeighOutErrs) {
        this.egpNeighOutErrs = egpNeighOutErrs;
    }

    public long getEgpNeighInErrMsgs() {
        return egpNeighInErrMsgs;
    }

    public void setEgpNeighInErrMsgs(long egpNeighInErrMsgs) {
        this.egpNeighInErrMsgs = egpNeighInErrMsgs;
    }

    public long getEgpNeighOutErrMsgs() {
        return egpNeighOutErrMsgs;
    }

    public void setEgpNeighOutErrMsgs(long egpNeighOutErrMsgs) {
        this.egpNeighOutErrMsgs = egpNeighOutErrMsgs;
    }

    public long getEgpNeighStateUps() {
        return egpNeighStateUps;
    }

    public void setEgpNeighStateUps(long egpNeighStateUps) {
        this.egpNeighStateUps = egpNeighStateUps;
    }

    public long getEgpNeighStateDowns() {
        return egpNeighStateDowns;
    }

    public void setEgpNeighStateDowns(long egpNeighStateDowns) {
        this.egpNeighStateDowns = egpNeighStateDowns;
    }

    public int getEgpNeighIntervalHello() {
        return egpNeighIntervalHello;
    }

    public void setEgpNeighIntervalHello(int egpNeighIntervalHello) {
        this.egpNeighIntervalHello = egpNeighIntervalHello;
    }

    public int getEgpNeighIntervalPoll() {
        return egpNeighIntervalPoll;
    }

    public void setEgpNeighIntervalPoll(int egpNeighIntervalPoll) {
        this.egpNeighIntervalPoll = egpNeighIntervalPoll;
    }

    public int getEgpNeighMode() {
        return egpNeighMode;
    }

    public void setEgpNeighMode(int egpNeighMode) {
        this.egpNeighMode = egpNeighMode;
    }

    public int getEgpNeighEventTrigger() {
        return egpNeighEventTrigger;
    }

    public void setEgpNeighEventTrigger(int egpNeighEventTrigger) {
        this.egpNeighEventTrigger = egpNeighEventTrigger;
    }

    @Override
    public String toString() {
        return "EgpNeighTable{" +
                "egpNeighState=" + egpNeighState +
                ", egpNeighAddr='" + egpNeighAddr + '\'' +
                ", egpNeighAs=" + egpNeighAs +
                ", egpNeighInMsgs=" + egpNeighInMsgs +
                ", egpNeighInErrs=" + egpNeighInErrs +
                ", egpNeighOutMsgs=" + egpNeighOutMsgs +
                ", egpNeighOutErrs=" + egpNeighOutErrs +
                ", egpNeighInErrMsgs=" + egpNeighInErrMsgs +
                ", egpNeighOutErrMsgs=" + egpNeighOutErrMsgs +
                ", egpNeighStateUps=" + egpNeighStateUps +
                ", egpNeighStateDowns=" + egpNeighStateDowns +
                ", egpNeighIntervalHello=" + egpNeighIntervalHello +
                ", egpNeighIntervalPoll=" + egpNeighIntervalPoll +
                ", egpNeighMode=" + egpNeighMode +
                ", egpNeighEventTrigger=" + egpNeighEventTrigger +
                '}';
    }
}
