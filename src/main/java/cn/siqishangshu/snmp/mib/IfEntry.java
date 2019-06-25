package cn.siqishangshu.snmp.mib;

import cn.siqishangshu.snmp.util.SNMPOID;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.2.2.1")
public class IfEntry {
    @SNMPOID("1")
    public int ifIndex;
    @SNMPOID("2")
    public String ifDescr;
    @SNMPOID("3")
    public int ifType;
    @SNMPOID("4")
    public int ifMtu;
    @SNMPOID("5")
    public long ifSpeed;
    @SNMPOID("6")
    public String ifPhysAddress;
    @SNMPOID("7")
    public int ifAdminStatus;
    @SNMPOID("8")
    public int ifOperStatus;
    @SNMPOID("9")
    public String ifLastChange;
    @SNMPOID("10")
    public long ifInOctets;
    @SNMPOID("11")
    public long ifInUcastPkts;
    @SNMPOID("12")
    public long ifInNUcastPkts;
    @SNMPOID("13")
    public long ifInDiscards;
    @SNMPOID("14")
    public long ifInErrors;
    @SNMPOID("15")
    public long ifInUnknownProtos;
    @SNMPOID("16")
    public long ifOutOctets;
    @SNMPOID("17")
    public long ifOutUcastPkts;
    @SNMPOID("18")
    public long ifOutNUcastPkts;
    @SNMPOID("19")
    public long ifOutDiscasrds;
    @SNMPOID("20")
    public long ifOutErrors;
    @SNMPOID("21")
    public long ifOutQlen;
    @SNMPOID("22")
    public String ifSpecific;

    public int getIfIndex() {
        return ifIndex;
    }

    public void setIfIndex(int ifIndex) {
        this.ifIndex = ifIndex;
    }

    public String getIfDescr() {
        return ifDescr;
    }

    public void setIfDescr(String ifDescr) {
        this.ifDescr = ifDescr;
    }

    public int getIfType() {
        return ifType;
    }

    public void setIfType(int ifType) {
        this.ifType = ifType;
    }

    public int getIfMtu() {
        return ifMtu;
    }

    public void setIfMtu(int ifMtu) {
        this.ifMtu = ifMtu;
    }

    public long getIfSpeed() {
        return ifSpeed;
    }

    public void setIfSpeed(long ifSpeed) {
        this.ifSpeed = ifSpeed;
    }

    public String getIfPhysAddress() {
        return ifPhysAddress;
    }

    public void setIfPhysAddress(String ifPhysAddress) {
        this.ifPhysAddress = ifPhysAddress;
    }

    public int getIfAdminStatus() {
        return ifAdminStatus;
    }

    public void setIfAdminStatus(int ifAdminStatus) {
        this.ifAdminStatus = ifAdminStatus;
    }

    public int getIfOperStatus() {
        return ifOperStatus;
    }

    public void setIfOperStatus(int ifOperStatus) {
        this.ifOperStatus = ifOperStatus;
    }

    public String getIfLastChange() {
        return ifLastChange;
    }

    public void setIfLastChange(String ifLastChange) {
        this.ifLastChange = ifLastChange;
    }

    public long getIfInOctets() {
        return ifInOctets;
    }

    public void setIfInOctets(long ifInOctets) {
        this.ifInOctets = ifInOctets;
    }

    public long getIfInUcastPkts() {
        return ifInUcastPkts;
    }

    public void setIfInUcastPkts(long ifInUcastPkts) {
        this.ifInUcastPkts = ifInUcastPkts;
    }

    public long getIfInNUcastPkts() {
        return ifInNUcastPkts;
    }

    public void setIfInNUcastPkts(long ifInNUcastPkts) {
        this.ifInNUcastPkts = ifInNUcastPkts;
    }

    public long getIfInDiscards() {
        return ifInDiscards;
    }

    public void setIfInDiscards(long ifInDiscards) {
        this.ifInDiscards = ifInDiscards;
    }

    public long getIfInErrors() {
        return ifInErrors;
    }

    public void setIfInErrors(long ifInErrors) {
        this.ifInErrors = ifInErrors;
    }

    public long getIfInUnknownProtos() {
        return ifInUnknownProtos;
    }

    public void setIfInUnknownProtos(long ifInUnknownProtos) {
        this.ifInUnknownProtos = ifInUnknownProtos;
    }

    public long getIfOutOctets() {
        return ifOutOctets;
    }

    public void setIfOutOctets(long ifOutOctets) {
        this.ifOutOctets = ifOutOctets;
    }

    public long getIfOutUcastPkts() {
        return ifOutUcastPkts;
    }

    public void setIfOutUcastPkts(long ifOutUcastPkts) {
        this.ifOutUcastPkts = ifOutUcastPkts;
    }

    public long getIfOutNUcastPkts() {
        return ifOutNUcastPkts;
    }

    public void setIfOutNUcastPkts(long ifOutNUcastPkts) {
        this.ifOutNUcastPkts = ifOutNUcastPkts;
    }

    public long getIfOutDiscasrds() {
        return ifOutDiscasrds;
    }

    public void setIfOutDiscasrds(long ifOutDiscasrds) {
        this.ifOutDiscasrds = ifOutDiscasrds;
    }

    public long getIfOutErrors() {
        return ifOutErrors;
    }

    public void setIfOutErrors(long ifOutErrors) {
        this.ifOutErrors = ifOutErrors;
    }

    public long getIfOutQlen() {
        return ifOutQlen;
    }

    public void setIfOutQlen(long ifOutQlen) {
        this.ifOutQlen = ifOutQlen;
    }

    public String getIfSpecific() {
        return ifSpecific;
    }

    public void setIfSpecific(String ifSpecific) {
        this.ifSpecific = ifSpecific;
    }

    @Override
    public String toString() {
        return "IfTableInfo{" +
                "ifIndex=" + ifIndex +
                ", ifDescr='" + ifDescr + '\'' +
                ", ifType=" + ifType +
                ", ifMtu=" + ifMtu +
                ", ifSpeed=" + ifSpeed +
                ", ifPhysAddress='" + ifPhysAddress + '\'' +
                ", ifAdminStatus=" + ifAdminStatus +
                ", ifOperStatus=" + ifOperStatus +
                ", ifLastChange='" + ifLastChange + '\'' +
                ", ifInOctets=" + ifInOctets +
                ", ifInUcastPkts=" + ifInUcastPkts +
                ", ifInNUcastPkts=" + ifInNUcastPkts +
                ", ifInDiscards=" + ifInDiscards +
                ", ifInErrors=" + ifInErrors +
                ", ifInUnknownProtos=" + ifInUnknownProtos +
                ", ifOutOctets=" + ifOutOctets +
                ", ifOutUcastPkts=" + ifOutUcastPkts +
                ", ifOutNUcastPkts=" + ifOutNUcastPkts +
                ", ifOutDiscasrds=" + ifOutDiscasrds +
                ", ifOutErrors=" + ifOutErrors +
                ", ifOutQlen=" + ifOutQlen +
                ", ifSpecific='" + ifSpecific + '\'' +
                '}';
    }
}

