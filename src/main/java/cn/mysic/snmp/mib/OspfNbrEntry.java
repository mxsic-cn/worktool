package cn.mysic.snmp.mib;

import cn.mysic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 1/3/17.
 */
@SNMPOID(".1.3.6.1.2.1.14.10.1")
public class OspfNbrEntry {

    @SNMPOID("1")
    public String ospfNbrIpAddr;

    @SNMPOID("2")
    public Integer ospfNbrAddressLessIndex;

    @SNMPOID("3")
    public String ospfNbrRtrId;

    @SNMPOID("4")
    public Integer ospfNbrOptions;

    @SNMPOID("5")
    public String ospfNbrPriority;

    @SNMPOID("6")
    public Integer ospfNbrState;

    @SNMPOID("7")
    public Integer ospfNbrEvents;

    @SNMPOID("8")
    public Integer ospfNbrLsRetransQLen;

    @SNMPOID("9")
    public String ospfNbmaNbrStatus;

    @SNMPOID("10")
    public Integer ospfNbmaNbrPermanence;

    @SNMPOID("11")
    public String ospfNbrHelloSuppressed;

    @SNMPOID("12")
    public Integer ospfNbrRestartHelperStatus;

    @SNMPOID("13")
    public String ospfNbrRestartHelperAge;

    @SNMPOID("14")
    public Integer ospfNbrRestartHelperExitReason;

    public String getOspfNbrIpAddr() {
        return ospfNbrIpAddr;
    }

    public void setOspfNbrIpAddr(String ospfNbrIpAddr) {
        this.ospfNbrIpAddr = ospfNbrIpAddr;
    }

    public Integer getOspfNbrAddressLessIndex() {
        return ospfNbrAddressLessIndex;
    }

    public void setOspfNbrAddressLessIndex(Integer ospfNbrAddressLessIndex) {
        this.ospfNbrAddressLessIndex = ospfNbrAddressLessIndex;
    }

    public String getOspfNbrRtrId() {
        return ospfNbrRtrId;
    }

    public void setOspfNbrRtrId(String ospfNbrRtrId) {
        this.ospfNbrRtrId = ospfNbrRtrId;
    }

    public Integer getOspfNbrOptions() {
        return ospfNbrOptions;
    }

    public void setOspfNbrOptions(Integer ospfNbrOptions) {
        this.ospfNbrOptions = ospfNbrOptions;
    }

    public String getOspfNbrPriority() {
        return ospfNbrPriority;
    }

    public void setOspfNbrPriority(String ospfNbrPriority) {
        this.ospfNbrPriority = ospfNbrPriority;
    }

    public Integer getOspfNbrState() {
        return ospfNbrState;
    }

    public void setOspfNbrState(Integer ospfNbrState) {
        this.ospfNbrState = ospfNbrState;
    }

    public Integer getOspfNbrEvents() {
        return ospfNbrEvents;
    }

    public void setOspfNbrEvents(Integer ospfNbrEvents) {
        this.ospfNbrEvents = ospfNbrEvents;
    }

    public Integer getOspfNbrLsRetransQLen() {
        return ospfNbrLsRetransQLen;
    }

    public void setOspfNbrLsRetransQLen(Integer ospfNbrLsRetransQLen) {
        this.ospfNbrLsRetransQLen = ospfNbrLsRetransQLen;
    }

    public String getOspfNbmaNbrStatus() {
        return ospfNbmaNbrStatus;
    }

    public void setOspfNbmaNbrStatus(String ospfNbmaNbrStatus) {
        this.ospfNbmaNbrStatus = ospfNbmaNbrStatus;
    }

    public Integer getOspfNbmaNbrPermanence() {
        return ospfNbmaNbrPermanence;
    }

    public void setOspfNbmaNbrPermanence(Integer ospfNbmaNbrPermanence) {
        this.ospfNbmaNbrPermanence = ospfNbmaNbrPermanence;
    }

    public String getOspfNbrHelloSuppressed() {
        return ospfNbrHelloSuppressed;
    }

    public void setOspfNbrHelloSuppressed(String ospfNbrHelloSuppressed) {
        this.ospfNbrHelloSuppressed = ospfNbrHelloSuppressed;
    }

    public Integer getOspfNbrRestartHelperStatus() {
        return ospfNbrRestartHelperStatus;
    }

    public void setOspfNbrRestartHelperStatus(Integer ospfNbrRestartHelperStatus) {
        this.ospfNbrRestartHelperStatus = ospfNbrRestartHelperStatus;
    }

    public String getOspfNbrRestartHelperAge() {
        return ospfNbrRestartHelperAge;
    }

    public void setOspfNbrRestartHelperAge(String ospfNbrRestartHelperAge) {
        this.ospfNbrRestartHelperAge = ospfNbrRestartHelperAge;
    }

    public Integer getOspfNbrRestartHelperExitReason() {
        return ospfNbrRestartHelperExitReason;
    }

    public void setOspfNbrRestartHelperExitReason(Integer ospfNbrRestartHelperExitReason) {
        this.ospfNbrRestartHelperExitReason = ospfNbrRestartHelperExitReason;
    }

    @Override
    public String toString() {
        return "OspfNbrEntry{" +
                "ospfNbrIpAddr='" + ospfNbrIpAddr + '\'' +
                ", ospfNbrAddressLessIndex=" + ospfNbrAddressLessIndex +
                ", ospfNbrRtrId='" + ospfNbrRtrId + '\'' +
                ", ospfNbrOptions=" + ospfNbrOptions +
                ", ospfNbrPriority='" + ospfNbrPriority + '\'' +
                ", ospfNbrState=" + ospfNbrState +
                ", ospfNbrEvents=" + ospfNbrEvents +
                ", ospfNbrLsRetransQLen=" + ospfNbrLsRetransQLen +
                ", ospfNbmaNbrStatus='" + ospfNbmaNbrStatus + '\'' +
                ", ospfNbmaNbrPermanence=" + ospfNbmaNbrPermanence +
                ", ospfNbrHelloSuppressed='" + ospfNbrHelloSuppressed + '\'' +
                ", ospfNbrRestartHelperStatus=" + ospfNbrRestartHelperStatus +
                ", ospfNbrRestartHelperAge='" + ospfNbrRestartHelperAge + '\'' +
                ", ospfNbrRestartHelperExitReason=" + ospfNbrRestartHelperExitReason +
                '}';
    }
}
