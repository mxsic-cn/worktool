package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 1/3/17.
 */
@SNMPOID(".1.3.6.1.2.1.17.2.15.1")
public class Dot1dStpPortEntry {

    @SNMPOID("1")
    public Integer dot1dStpPort;

    @SNMPOID("2")
    public Integer dot1dStpPortPriority;

    @SNMPOID("3")
    public Integer dot1dStpPortState;

    @SNMPOID("4")
    public Integer dot1dStpPortEnable;

    @SNMPOID("5")
    public Integer dot1dStpPortPathCost;

    @SNMPOID("6")
    public String dot1dStpPortDesignatedRoot;

    @SNMPOID("7")
    public Integer dot1dStpPortDesignatedCost;

    @SNMPOID("8")
    public String dot1dStpPortDesignatedBridge;

    @SNMPOID("9")
    public String dot1dStpPortDesignatedPort;

    @SNMPOID("10")
    public Integer dot1dStpPortForwardTransitions;

    @SNMPOID("11")
    public Integer dot1dStpPortPathCost32;

    public Integer getDot1dStpPort() {
        return dot1dStpPort;
    }

    public void setDot1dStpPort(Integer dot1dStpPort) {
        this.dot1dStpPort = dot1dStpPort;
    }

    public Integer getDot1dStpPortPriority() {
        return dot1dStpPortPriority;
    }

    public void setDot1dStpPortPriority(Integer dot1dStpPortPriority) {
        this.dot1dStpPortPriority = dot1dStpPortPriority;
    }

    public Integer getDot1dStpPortState() {
        return dot1dStpPortState;
    }

    public void setDot1dStpPortState(Integer dot1dStpPortState) {
        this.dot1dStpPortState = dot1dStpPortState;
    }

    public Integer getDot1dStpPortEnable() {
        return dot1dStpPortEnable;
    }

    public void setDot1dStpPortEnable(Integer dot1dStpPortEnable) {
        this.dot1dStpPortEnable = dot1dStpPortEnable;
    }

    public Integer getDot1dStpPortPathCost() {
        return dot1dStpPortPathCost;
    }

    public void setDot1dStpPortPathCost(Integer dot1dStpPortPathCost) {
        this.dot1dStpPortPathCost = dot1dStpPortPathCost;
    }

    public String getDot1dStpPortDesignatedRoot() {
        return dot1dStpPortDesignatedRoot;
    }

    public void setDot1dStpPortDesignatedRoot(String dot1dStpPortDesignatedRoot) {
        this.dot1dStpPortDesignatedRoot = dot1dStpPortDesignatedRoot;
    }

    public Integer getDot1dStpPortDesignatedCost() {
        return dot1dStpPortDesignatedCost;
    }

    public void setDot1dStpPortDesignatedCost(Integer dot1dStpPortDesignatedCost) {
        this.dot1dStpPortDesignatedCost = dot1dStpPortDesignatedCost;
    }

    public String getDot1dStpPortDesignatedBridge() {
        return dot1dStpPortDesignatedBridge;
    }

    public void setDot1dStpPortDesignatedBridge(String dot1dStpPortDesignatedBridge) {
        this.dot1dStpPortDesignatedBridge = dot1dStpPortDesignatedBridge;
    }

    public String getDot1dStpPortDesignatedPort() {
        return dot1dStpPortDesignatedPort;
    }

    public void setDot1dStpPortDesignatedPort(String dot1dStpPortDesignatedPort) {
        this.dot1dStpPortDesignatedPort = dot1dStpPortDesignatedPort;
    }

    public Integer getDot1dStpPortForwardTransitions() {
        return dot1dStpPortForwardTransitions;
    }

    public void setDot1dStpPortForwardTransitions(Integer dot1dStpPortForwardTransitions) {
        this.dot1dStpPortForwardTransitions = dot1dStpPortForwardTransitions;
    }

    public Integer getDot1dStpPortPathCost32() {
        return dot1dStpPortPathCost32;
    }

    public void setDot1dStpPortPathCost32(Integer dot1dStpPortPathCost32) {
        this.dot1dStpPortPathCost32 = dot1dStpPortPathCost32;
    }

    @Override
    public String toString() {
        return "Dot1dStpPortEntry{" +
                "dot1dStpPort=" + dot1dStpPort +
                ", dot1dStpPortPriority=" + dot1dStpPortPriority +
                ", dot1dStpPortState=" + dot1dStpPortState +
                ", dot1dStpPortEnable=" + dot1dStpPortEnable +
                ", dot1dStpPortPathCost=" + dot1dStpPortPathCost +
                ", dot1dStpPortDesignatedRoot='" + dot1dStpPortDesignatedRoot + '\'' +
                ", dot1dStpPortDesignatedCost=" + dot1dStpPortDesignatedCost +
                ", dot1dStpPortDesignatedBridge='" + dot1dStpPortDesignatedBridge + '\'' +
                ", dot1dStpPortDesignatedPort='" + dot1dStpPortDesignatedPort + '\'' +
                ", dot1dStpPortForwardTransitions=" + dot1dStpPortForwardTransitions +
                ", dot1dStpPortPathCost32=" + dot1dStpPortPathCost32 +
                '}';
    }
}
