package cn.mysic.snmp.mib;

import cn.mysic.snmp.util.SNMPOID;

import java.util.List;

/**
 * Created by liuchuan on 8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.6")
public class TcpInfo {
    @SNMPOID("1")
    public int tcpRtoAlgorithm;
    @SNMPOID("2")
    public int tcpRtoMin;
    @SNMPOID("3")
    public int tcpRtoMax;
    @SNMPOID("4")
    public int tcpMaxConn;
    @SNMPOID("5")
    public long tcpActiveOpens;
    @SNMPOID("6")
    public long tcpPassiveOpens;
    @SNMPOID("7")
    public long tcpAttemptFails;
    @SNMPOID("8")
    public long tcpEstabResets;
    @SNMPOID("9")
    public long tcpCurrEstab;
    @SNMPOID("10")
    public long tcpInSegs;
    @SNMPOID("11")
    public long tcpOutSegs;
    @SNMPOID("12")
    public long tcpRetransSegs;
    @SNMPOID("13")
    public List<TcpConnEntry> tcpConnEntry;
    @SNMPOID("14")
    public long tcpInErrs;
    @SNMPOID("15")
    public long tcpOutRsts;

    public int getTcpRtoAlgorithm() {
        return tcpRtoAlgorithm;
    }

    public void setTcpRtoAlgorithm(int tcpRtoAlgorithm) {
        this.tcpRtoAlgorithm = tcpRtoAlgorithm;
    }

    public int getTcpRtoMin() {
        return tcpRtoMin;
    }

    public void setTcpRtoMin(int tcpRtoMin) {
        this.tcpRtoMin = tcpRtoMin;
    }

    public int getTcpRtoMax() {
        return tcpRtoMax;
    }

    public void setTcpRtoMax(int tcpRtoMax) {
        this.tcpRtoMax = tcpRtoMax;
    }

    public int getTcpMaxConn() {
        return tcpMaxConn;
    }

    public void setTcpMaxConn(int tcpMaxConn) {
        this.tcpMaxConn = tcpMaxConn;
    }

    public long getTcpActiveOpens() {
        return tcpActiveOpens;
    }

    public void setTcpActiveOpens(long tcpActiveOpens) {
        this.tcpActiveOpens = tcpActiveOpens;
    }

    public long getTcpPassiveOpens() {
        return tcpPassiveOpens;
    }

    public void setTcpPassiveOpens(long tcpPassiveOpens) {
        this.tcpPassiveOpens = tcpPassiveOpens;
    }

    public long getTcpAttemptFails() {
        return tcpAttemptFails;
    }

    public void setTcpAttemptFails(long tcpAttemptFails) {
        this.tcpAttemptFails = tcpAttemptFails;
    }

    public long getTcpEstabResets() {
        return tcpEstabResets;
    }

    public void setTcpEstabResets(long tcpEstabResets) {
        this.tcpEstabResets = tcpEstabResets;
    }

    public long getTcpCurrEstab() {
        return tcpCurrEstab;
    }

    public void setTcpCurrEstab(long tcpCurrEstab) {
        this.tcpCurrEstab = tcpCurrEstab;
    }

    public long getTcpInSegs() {
        return tcpInSegs;
    }

    public void setTcpInSegs(long tcpInSegs) {
        this.tcpInSegs = tcpInSegs;
    }

    public long getTcpOutSegs() {
        return tcpOutSegs;
    }

    public void setTcpOutSegs(long tcpOutSegs) {
        this.tcpOutSegs = tcpOutSegs;
    }

    public long getTcpRetransSegs() {
        return tcpRetransSegs;
    }

    public void setTcpRetransSegs(long tcpRetransSegs) {
        this.tcpRetransSegs = tcpRetransSegs;
    }

    public List<TcpConnEntry> getTcpConnEntry() {
        return tcpConnEntry;
    }

    public void setTcpConnEntry(List<TcpConnEntry> tcpConnEntry) {
        this.tcpConnEntry = tcpConnEntry;
    }

    public long getTcpInErrs() {
        return tcpInErrs;
    }

    public void setTcpInErrs(long tcpInErrs) {
        this.tcpInErrs = tcpInErrs;
    }

    public long getTcpOutRsts() {
        return tcpOutRsts;
    }

    public void setTcpOutRsts(long tcpOutRsts) {
        this.tcpOutRsts = tcpOutRsts;
    }

    @Override
    public String toString() {
        return "TcpInfo{" +
                "tcpRtoAlgorithm=" + tcpRtoAlgorithm +
                ", tcpRtoMin=" + tcpRtoMin +
                ", tcpRtoMax=" + tcpRtoMax +
                ", tcpMaxConn=" + tcpMaxConn +
                ", tcpActiveOpens=" + tcpActiveOpens +
                ", tcpPassiveOpens=" + tcpPassiveOpens +
                ", tcpAttemptFails=" + tcpAttemptFails +
                ", tcpEstabResets=" + tcpEstabResets +
                ", tcpCurrEstab=" + tcpCurrEstab +
                ", tcpInSegs=" + tcpInSegs +
                ", tcpOutSegs=" + tcpOutSegs +
                ", tcpRetransSegs=" + tcpRetransSegs +
                ", tcpConnEntry=" + tcpConnEntry +
                ", tcpInErrs=" + tcpInErrs +
                ", tcpOutRsts=" + tcpOutRsts +
                '}';
    }
}
