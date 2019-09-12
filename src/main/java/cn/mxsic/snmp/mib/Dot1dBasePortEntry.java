package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 1/3/17.
 */
@SNMPOID(".1.3.6.1.2.1.17.1.4.1")
public class Dot1dBasePortEntry {

    @SNMPOID("1")
    public Integer dot1dBasePort;

    @SNMPOID("2")
    public Integer dot1dBasePortIfIndex;

    @SNMPOID("3")
    public String dot1dBasePortCircuit;

    @SNMPOID("4")
    public Integer dot1dBasePortDelayExceededDiscards;

    @SNMPOID("5")
    public Integer dot1dBasePortMtuExceededDiscards;

    public Integer getDot1dBasePort() {
        return dot1dBasePort;
    }

    public void setDot1dBasePort(Integer dot1dBasePort) {
        this.dot1dBasePort = dot1dBasePort;
    }

    public Integer getDot1dBasePortIfIndex() {
        return dot1dBasePortIfIndex;
    }

    public void setDot1dBasePortIfIndex(Integer dot1dBasePortIfIndex) {
        this.dot1dBasePortIfIndex = dot1dBasePortIfIndex;
    }

    public String getDot1dBasePortCircuit() {
        return dot1dBasePortCircuit;
    }

    public void setDot1dBasePortCircuit(String dot1dBasePortCircuit) {
        this.dot1dBasePortCircuit = dot1dBasePortCircuit;
    }

    public Integer getDot1dBasePortDelayExceededDiscards() {
        return dot1dBasePortDelayExceededDiscards;
    }

    public void setDot1dBasePortDelayExceededDiscards(Integer dot1dBasePortDelayExceededDiscards) {
        this.dot1dBasePortDelayExceededDiscards = dot1dBasePortDelayExceededDiscards;
    }

    public Integer getDot1dBasePortMtuExceededDiscards() {
        return dot1dBasePortMtuExceededDiscards;
    }

    public void setDot1dBasePortMtuExceededDiscards(Integer dot1dBasePortMtuExceededDiscards) {
        this.dot1dBasePortMtuExceededDiscards = dot1dBasePortMtuExceededDiscards;
    }

    @Override
    public String toString() {
        return "Dot1dBasePortEntry{" +
                "dot1dBasePort=" + dot1dBasePort +
                ", dot1dBasePortIfIndex=" + dot1dBasePortIfIndex +
                ", dot1dBasePortCircuit='" + dot1dBasePortCircuit + '\'' +
                ", dot1dBasePortDelayExceededDiscards=" + dot1dBasePortDelayExceededDiscards +
                ", dot1dBasePortMtuExceededDiscards=" + dot1dBasePortMtuExceededDiscards +
                '}';
    }
}
