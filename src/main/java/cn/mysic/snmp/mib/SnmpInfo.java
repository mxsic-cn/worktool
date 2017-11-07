package cn.mysic.snmp.mib;

import cn.mysic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.11")
public class SnmpInfo {
    @SNMPOID("1")
    public long snmpInPkts;
    @SNMPOID("2")
    public long snmpOutPkts;
    @SNMPOID("3")
    public long snmpInBadVersions;
    @SNMPOID("4")
    public long snmpInBadCommunityNames;
    @SNMPOID("5")
    public long snmpInBadCommunityUses;
    @SNMPOID("6")
    public long snmpInASNParseErrs;
    @SNMPOID("8")
    public long snmpInTooBigs;
    @SNMPOID("9")
    public long snmpInNoSuchNames;
    @SNMPOID("10")
    public long snmpInBadValues;
    @SNMPOID("11")
    public long snmpInReadOnlys;
    @SNMPOID("12")
    public long snmpInGenErrs;
    @SNMPOID("13")
    public long snmpInTotalReqVars;
    @SNMPOID("14")
    public long snmpInTotalSetVars;
    @SNMPOID("15")
    public long snmpInGetRequests;
    @SNMPOID("16")
    public long snmpInGetNexts;
    @SNMPOID("17")
    public long snmpInSetRequests;
    @SNMPOID("18")
    public long snmpInGetResponses;
    @SNMPOID("19")
    public long snmpInTraps;
    @SNMPOID("20")
    public long snmpOutTooBigs;
    @SNMPOID("21")
    public long snmpOutNoSuchNames;
    @SNMPOID("22")
    public long snmpOutBadValues;
    @SNMPOID("24")
    public long snmpOutGenErrs;
    @SNMPOID("25")
    public long snmpOutGetRequests;
    @SNMPOID("26")
    public long snmpOutGetNexts;
    @SNMPOID("27")
    public long snmpOutSetRequests;
    @SNMPOID("28")
    public long snmpOutGetResponses;
    @SNMPOID("29")
    public long snmpOutTraps;
    @SNMPOID("30")
    public int snmpEnableAuthenTraps;

    public long getSnmpInPkts() {
        return snmpInPkts;
    }

    public void setSnmpInPkts(long snmpInPkts) {
        this.snmpInPkts = snmpInPkts;
    }

    public long getSnmpOutPkts() {
        return snmpOutPkts;
    }

    public void setSnmpOutPkts(long snmpOutPkts) {
        this.snmpOutPkts = snmpOutPkts;
    }

    public long getSnmpInBadVersions() {
        return snmpInBadVersions;
    }

    public void setSnmpInBadVersions(long snmpInBadVersions) {
        this.snmpInBadVersions = snmpInBadVersions;
    }

    public long getSnmpInBadCommunityNames() {
        return snmpInBadCommunityNames;
    }

    public void setSnmpInBadCommunityNames(long snmpInBadCommunityNames) {
        this.snmpInBadCommunityNames = snmpInBadCommunityNames;
    }

    public long getSnmpInBadCommunityUses() {
        return snmpInBadCommunityUses;
    }

    public void setSnmpInBadCommunityUses(long snmpInBadCommunityUses) {
        this.snmpInBadCommunityUses = snmpInBadCommunityUses;
    }

    public long getSnmpInASNParseErrs() {
        return snmpInASNParseErrs;
    }

    public void setSnmpInASNParseErrs(long snmpInASNParseErrs) {
        this.snmpInASNParseErrs = snmpInASNParseErrs;
    }

    public long getSnmpInTooBigs() {
        return snmpInTooBigs;
    }

    public void setSnmpInTooBigs(long snmpInTooBigs) {
        this.snmpInTooBigs = snmpInTooBigs;
    }

    public long getSnmpInNoSuchNames() {
        return snmpInNoSuchNames;
    }

    public void setSnmpInNoSuchNames(long snmpInNoSuchNames) {
        this.snmpInNoSuchNames = snmpInNoSuchNames;
    }

    public long getSnmpInBadValues() {
        return snmpInBadValues;
    }

    public void setSnmpInBadValues(long snmpInBadValues) {
        this.snmpInBadValues = snmpInBadValues;
    }

    public long getSnmpInReadOnlys() {
        return snmpInReadOnlys;
    }

    public void setSnmpInReadOnlys(long snmpInReadOnlys) {
        this.snmpInReadOnlys = snmpInReadOnlys;
    }

    public long getSnmpInGenErrs() {
        return snmpInGenErrs;
    }

    public void setSnmpInGenErrs(long snmpInGenErrs) {
        this.snmpInGenErrs = snmpInGenErrs;
    }

    public long getSnmpInTotalReqVars() {
        return snmpInTotalReqVars;
    }

    public void setSnmpInTotalReqVars(long snmpInTotalReqVars) {
        this.snmpInTotalReqVars = snmpInTotalReqVars;
    }

    public long getSnmpInTotalSetVars() {
        return snmpInTotalSetVars;
    }

    public void setSnmpInTotalSetVars(long snmpInTotalSetVars) {
        this.snmpInTotalSetVars = snmpInTotalSetVars;
    }

    public long getSnmpInGetRequests() {
        return snmpInGetRequests;
    }

    public void setSnmpInGetRequests(long snmpInGetRequests) {
        this.snmpInGetRequests = snmpInGetRequests;
    }

    public long getSnmpInGetNexts() {
        return snmpInGetNexts;
    }

    public void setSnmpInGetNexts(long snmpInGetNexts) {
        this.snmpInGetNexts = snmpInGetNexts;
    }

    public long getSnmpInSetRequests() {
        return snmpInSetRequests;
    }

    public void setSnmpInSetRequests(long snmpInSetRequests) {
        this.snmpInSetRequests = snmpInSetRequests;
    }

    public long getSnmpInGetResponses() {
        return snmpInGetResponses;
    }

    public void setSnmpInGetResponses(long snmpInGetResponses) {
        this.snmpInGetResponses = snmpInGetResponses;
    }

    public long getSnmpInTraps() {
        return snmpInTraps;
    }

    public void setSnmpInTraps(long snmpInTraps) {
        this.snmpInTraps = snmpInTraps;
    }

    public long getSnmpOutTooBigs() {
        return snmpOutTooBigs;
    }

    public void setSnmpOutTooBigs(long snmpOutTooBigs) {
        this.snmpOutTooBigs = snmpOutTooBigs;
    }

    public long getSnmpOutNoSuchNames() {
        return snmpOutNoSuchNames;
    }

    public void setSnmpOutNoSuchNames(long snmpOutNoSuchNames) {
        this.snmpOutNoSuchNames = snmpOutNoSuchNames;
    }

    public long getSnmpOutBadValues() {
        return snmpOutBadValues;
    }

    public void setSnmpOutBadValues(long snmpOutBadValues) {
        this.snmpOutBadValues = snmpOutBadValues;
    }

    public long getSnmpOutGenErrs() {
        return snmpOutGenErrs;
    }

    public void setSnmpOutGenErrs(long snmpOutGenErrs) {
        this.snmpOutGenErrs = snmpOutGenErrs;
    }

    public long getSnmpOutGetRequests() {
        return snmpOutGetRequests;
    }

    public void setSnmpOutGetRequests(long snmpOutGetRequests) {
        this.snmpOutGetRequests = snmpOutGetRequests;
    }

    public long getSnmpOutGetNexts() {
        return snmpOutGetNexts;
    }

    public void setSnmpOutGetNexts(long snmpOutGetNexts) {
        this.snmpOutGetNexts = snmpOutGetNexts;
    }

    public long getSnmpOutSetRequests() {
        return snmpOutSetRequests;
    }

    public void setSnmpOutSetRequests(long snmpOutSetRequests) {
        this.snmpOutSetRequests = snmpOutSetRequests;
    }

    public long getSnmpOutGetResponses() {
        return snmpOutGetResponses;
    }

    public void setSnmpOutGetResponses(long snmpOutGetResponses) {
        this.snmpOutGetResponses = snmpOutGetResponses;
    }

    public long getSnmpOutTraps() {
        return snmpOutTraps;
    }

    public void setSnmpOutTraps(long snmpOutTraps) {
        this.snmpOutTraps = snmpOutTraps;
    }

    public int getSnmpEnableAuthenTraps() {
        return snmpEnableAuthenTraps;
    }

    public void setSnmpEnableAuthenTraps(int snmpEnableAuthenTraps) {
        this.snmpEnableAuthenTraps = snmpEnableAuthenTraps;
    }

    @Override
    public String toString() {
        return "SnmpInfo{" +
                "snmpInPkts=" + snmpInPkts +
                ", snmpOutPkts=" + snmpOutPkts +
                ", snmpInBadVersions=" + snmpInBadVersions +
                ", snmpInBadCommunityNames=" + snmpInBadCommunityNames +
                ", snmpInBadCommunityUses=" + snmpInBadCommunityUses +
                ", snmpInASNParseErrs=" + snmpInASNParseErrs +
                ", snmpInTooBigs=" + snmpInTooBigs +
                ", snmpInNoSuchNames=" + snmpInNoSuchNames +
                ", snmpInBadValues=" + snmpInBadValues +
                ", snmpInReadOnlys=" + snmpInReadOnlys +
                ", snmpInGenErrs=" + snmpInGenErrs +
                ", snmpInTotalReqVars=" + snmpInTotalReqVars +
                ", snmpInTotalSetVars=" + snmpInTotalSetVars +
                ", snmpInGetRequests=" + snmpInGetRequests +
                ", snmpInGetNexts=" + snmpInGetNexts +
                ", snmpInSetRequests=" + snmpInSetRequests +
                ", snmpInGetResponses=" + snmpInGetResponses +
                ", snmpInTraps=" + snmpInTraps +
                ", snmpOutTooBigs=" + snmpOutTooBigs +
                ", snmpOutNoSuchNames=" + snmpOutNoSuchNames +
                ", snmpOutBadValues=" + snmpOutBadValues +
                ", snmpOutGenErrs=" + snmpOutGenErrs +
                ", snmpOutGetRequests=" + snmpOutGetRequests +
                ", snmpOutGetNexts=" + snmpOutGetNexts +
                ", snmpOutSetRequests=" + snmpOutSetRequests +
                ", snmpOutGetResponses=" + snmpOutGetResponses +
                ", snmpOutTraps=" + snmpOutTraps +
                ", snmpEnableAuthenTraps=" + snmpEnableAuthenTraps +
                '}';
    }
}
