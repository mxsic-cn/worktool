package cn.siqishangshu.snmp.mib;

import cn.siqishangshu.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 1/3/17.
 */
@SNMPOID(".1.0.8802.1.1.2.1.4.1.1")
public class LldpRemEntry {


    @SNMPOID("1")
    public String lldpRemTimeMark;

    @SNMPOID("2")
    public int lldpRemLocalPortNum;

    @SNMPOID("3")
    public int lldpRemIndex;

    @SNMPOID("4")
    public int lldpRemChassisIdSubtype;

    @SNMPOID("5")
    public String lldpRemChassisId;

    @SNMPOID("6")
    public int lldpRemPortIdSubtype;

    @SNMPOID("7")
    public String lldpRemPortId;

    @SNMPOID("8")
    public String lldpRemPortDesc;

    @SNMPOID("9")
    public String lldpRemSysName;

    @SNMPOID("10")
    public String lldpRemSysDesc;

    @SNMPOID("11")
    public String lldpRemSysCapSupported;

    @SNMPOID("12")
    public String lldpRemSysCapEnabled;

    public String getLldpRemTimeMark() {
        return lldpRemTimeMark;
    }

    public void setLldpRemTimeMark(String lldpRemTimeMark) {
        this.lldpRemTimeMark = lldpRemTimeMark;
    }

    public int getLldpRemLocalPortNum() {
        return lldpRemLocalPortNum;
    }

    public void setLldpRemLocalPortNum(int lldpRemLocalPortNum) {
        this.lldpRemLocalPortNum = lldpRemLocalPortNum;
    }

    public int getLldpRemIndex() {
        return lldpRemIndex;
    }

    public void setLldpRemIndex(int lldpRemIndex) {
        this.lldpRemIndex = lldpRemIndex;
    }

    public int getLldpRemChassisIdSubtype() {
        return lldpRemChassisIdSubtype;
    }

    public void setLldpRemChassisIdSubtype(int lldpRemChassisIdSubtype) {
        this.lldpRemChassisIdSubtype = lldpRemChassisIdSubtype;
    }

    public String getLldpRemChassisId() {
        return lldpRemChassisId;
    }

    public void setLldpRemChassisId(String lldpRemChassisId) {
        this.lldpRemChassisId = lldpRemChassisId;
    }

    public int getLldpRemPortIdSubtype() {
        return lldpRemPortIdSubtype;
    }

    public void setLldpRemPortIdSubtype(int lldpRemPortIdSubtype) {
        this.lldpRemPortIdSubtype = lldpRemPortIdSubtype;
    }

    public String getLldpRemPortId() {
        return lldpRemPortId;
    }

    public void setLldpRemPortId(String lldpRemPortId) {
        this.lldpRemPortId = lldpRemPortId;
    }

    public String getLldpRemPortDesc() {
        return lldpRemPortDesc;
    }

    public void setLldpRemPortDesc(String lldpRemPortDesc) {
        this.lldpRemPortDesc = lldpRemPortDesc;
    }

    public String getLldpRemSysName() {
        return lldpRemSysName;
    }

    public void setLldpRemSysName(String lldpRemSysName) {
        this.lldpRemSysName = lldpRemSysName;
    }

    public String getLldpRemSysDesc() {
        return lldpRemSysDesc;
    }

    public void setLldpRemSysDesc(String lldpRemSysDesc) {
        this.lldpRemSysDesc = lldpRemSysDesc;
    }

    public String getLldpRemSysCapSupported() {
        return lldpRemSysCapSupported;
    }

    public void setLldpRemSysCapSupported(String lldpRemSysCapSupported) {
        this.lldpRemSysCapSupported = lldpRemSysCapSupported;
    }

    public String getLldpRemSysCapEnabled() {
        return lldpRemSysCapEnabled;
    }

    public void setLldpRemSysCapEnabled(String lldpRemSysCapEnabled) {
        this.lldpRemSysCapEnabled = lldpRemSysCapEnabled;
    }

    @Override
    public String toString() {
        return "LldpRemEntry{" +
                "lldpRemTimeMark='" + lldpRemTimeMark + '\'' +
                ", lldpRemLocalPortNum='" + lldpRemLocalPortNum + '\'' +
                ", lldpRemIndex=" + lldpRemIndex +
                ", lldpRemChassisIdSubtype='" + lldpRemChassisIdSubtype + '\'' +
                ", lldpRemChassisId='" + lldpRemChassisId + '\'' +
                ", lldpRemPortIdSubtype='" + lldpRemPortIdSubtype + '\'' +
                ", lldpRemPortId='" + lldpRemPortId + '\'' +
                ", lldpRemPortDesc='" + lldpRemPortDesc + '\'' +
                ", lldpRemSysName='" + lldpRemSysName + '\'' +
                ", lldpRemSysDesc='" + lldpRemSysDesc + '\'' +
                ", lldpRemSysCapSupported='" + lldpRemSysCapSupported + '\'' +
                ", lldpRemSysCapEnabled='" + lldpRemSysCapEnabled + '\'' +
                '}';
    }

}
