package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

import java.io.Serializable;

/**
 * Created by liuchuan on java8/11/16.
 */
@SNMPOID(".1.3.6.1.2.1.1")
public class SystemInfo implements Serializable{

    @SNMPOID("1")
    public String sysDesc;
    @SNMPOID("2")
    public String sysObjectID;
    @SNMPOID("3")
    public String  sysUptime;
    @SNMPOID("4")
    public String sysContact;
    @SNMPOID("5")
    public String  sysName;
    @SNMPOID("6")
    public String  sysLocation;
    @SNMPOID("7")
    public String  sysService;

    public String getSysDesc() {
        return sysDesc;
    }

    public void setSysDesc(String sysDesc) {
        this.sysDesc = sysDesc;
    }

    public String getSysObjectID() {
        return sysObjectID;
    }

    public void setSysObjectID(String sysObjectID) {
        this.sysObjectID = sysObjectID;
    }

    public String getSysUptime() {
        return sysUptime;
    }

    public void setSysUptime(String sysUptime) {
        this.sysUptime = sysUptime;
    }

    public String getSysContact() {
        return sysContact;
    }

    public void setSysContact(String sysContact) {
        this.sysContact = sysContact;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysLocation() {
        return sysLocation;
    }

    public void setSysLocation(String sysLocation) {
        this.sysLocation = sysLocation;
    }

    public String getSysService() {
        return sysService;
    }

    public void setSysService(String sysService) {
        this.sysService = sysService;
    }

    @Override
    public String toString() {
        return "SystemInfo{" +
                "sysDesc='" + sysDesc + '\'' +
                ", sysObjectID='" + sysObjectID + '\'' +
                ", sysUptime='" + sysUptime + '\'' +
                ", sysContact='" + sysContact + '\'' +
                ", sysName='" + sysName + '\'' +
                ", sysLocation='" + sysLocation + '\'' +
                ", sysService='" + sysService + '\'' +
                '}';
    }
}
