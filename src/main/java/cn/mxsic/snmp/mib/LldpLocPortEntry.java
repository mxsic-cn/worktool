package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 1/3/17.
 */
@SNMPOID(".1.0.8802.1.1.2.1.3.7.1")
public class LldpLocPortEntry {

    @SNMPOID("1")
    public Integer lldpLocPortNum;

    @SNMPOID("2")
    public Integer lldpLocPortIdSubtype;

    @SNMPOID("3")
    public String lldpLocPortId;

    @SNMPOID("4")
    public String lldpLocPortDesc;

    @Override
    public String toString() {
        return "LldpLocPortEntry{" +
                "lldpLocPortNum=" + lldpLocPortNum +
                ", lldpLocPortIdSubtype=" + lldpLocPortIdSubtype +
                ", lldpLocPortId='" + lldpLocPortId + '\'' +
                ", lldpLocPortDesc='" + lldpLocPortDesc + '\'' +
                '}';
    }

    public Integer getLldpLocPortNum() {
        return lldpLocPortNum;
    }

    public void setLldpLocPortNum(Integer lldpLocPortNum) {
        this.lldpLocPortNum = lldpLocPortNum;
    }

    public Integer getLldpLocPortIdSubtype() {
        return lldpLocPortIdSubtype;
    }

    public void setLldpLocPortIdSubtype(Integer lldpLocPortIdSubtype) {
        this.lldpLocPortIdSubtype = lldpLocPortIdSubtype;
    }

    public String getLldpLocPortId() {
        return lldpLocPortId;
    }

    public void setLldpLocPortId(String lldpLocPortId) {
        this.lldpLocPortId = lldpLocPortId;
    }

    public String getLldpLocPortDesc() {
        return lldpLocPortDesc;
    }

    public void setLldpLocPortDesc(String lldpLocPortDesc) {
        this.lldpLocPortDesc = lldpLocPortDesc;
    }
}
