package cn.siqishangshu.snmp.mib;

import cn.siqishangshu.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 1/3/17.
 */
@SNMPOID(".1.3.6.1.4.1.9.9.23.1.2.1.1")
public class CdpCacheEntry {

    @SNMPOID("1")
    public int cdpCacheIfIndex;

    @SNMPOID("2")
    public int cdpCacheDeviceIndex;

    @SNMPOID("3")
    public String cdpCacheAddressType;

    @SNMPOID("4")
    public String cdpCacheAddress;

    @SNMPOID("5")
    public String cdpCacheVersion;

    @SNMPOID("6")
    public String cdpCacheDeviceId;

    @SNMPOID("7")
    public String cdpCacheDevicePort;

    @SNMPOID("8")
    public String cdpCachePlatform;

    @SNMPOID("9")
    public String cdpCacheCapabilities;

    @SNMPOID("10")
    public String cdpCacheVTPMgmtDomain;

    @SNMPOID("11")
    public String cdpCacheNativeVLAN;

    @SNMPOID("12")
    public String cdpCacheDuplex;

    @SNMPOID("13")
    public String cdpCacheApplianceID;

    @SNMPOID("14")
    public String cdpCacheVlanID;

    @SNMPOID("15")
    public String cdpCachePowerConsumption;

    @SNMPOID("16")
    public String cdpCacheMTU;

    @SNMPOID("17")
    public String cdpCacheSysName;

    @SNMPOID("18")
    public String cdpCacheSysObjectID;

    @SNMPOID("19")
    public String cdpCachePrimaryMgmtAddrType;

    @SNMPOID("20")
    public String cdpCachePrimaryMgmtAddr;

    @SNMPOID("21")
    public String cdpCacheSecondaryMgmtAddrType;

    @SNMPOID("22")
    public String cdpCacheSecondaryMgmtAddr;

    @SNMPOID("23")
    public String cdpCachePhysLocation;

    @SNMPOID("24")
    public String cdpCacheLastChange;

    public int getCdpCacheIfIndex() {
        return cdpCacheIfIndex;
    }

    public void setCdpCacheIfIndex(int cdpCacheIfIndex) {
        this.cdpCacheIfIndex = cdpCacheIfIndex;
    }

    public int getCdpCacheDeviceIndex() {
        return cdpCacheDeviceIndex;
    }

    public void setCdpCacheDeviceIndex(int cdpCacheDeviceIndex) {
        this.cdpCacheDeviceIndex = cdpCacheDeviceIndex;
    }

    public String getCdpCacheAddressType() {
        return cdpCacheAddressType;
    }

    public void setCdpCacheAddressType(String cdpCacheAddressType) {
        this.cdpCacheAddressType = cdpCacheAddressType;
    }

    public String getCdpCacheAddress() {
        return cdpCacheAddress;
    }

    public void setCdpCacheAddress(String cdpCacheAddress) {
        this.cdpCacheAddress = cdpCacheAddress;
    }

    public String getCdpCacheVersion() {
        return cdpCacheVersion;
    }

    public void setCdpCacheVersion(String cdpCacheVersion) {
        this.cdpCacheVersion = cdpCacheVersion;
    }

    public String getCdpCacheDeviceId() {
        return cdpCacheDeviceId;
    }

    public void setCdpCacheDeviceId(String cdpCacheDeviceId) {
        this.cdpCacheDeviceId = cdpCacheDeviceId;
    }

    public String getCdpCacheDevicePort() {
        return cdpCacheDevicePort;
    }

    public void setCdpCacheDevicePort(String cdpCacheDevicePort) {
        this.cdpCacheDevicePort = cdpCacheDevicePort;
    }

    public String getCdpCachePlatform() {
        return cdpCachePlatform;
    }

    public void setCdpCachePlatform(String cdpCachePlatform) {
        this.cdpCachePlatform = cdpCachePlatform;
    }

    public String getCdpCacheCapabilities() {
        return cdpCacheCapabilities;
    }

    public void setCdpCacheCapabilities(String cdpCacheCapabilities) {
        this.cdpCacheCapabilities = cdpCacheCapabilities;
    }

    public String getCdpCacheVTPMgmtDomain() {
        return cdpCacheVTPMgmtDomain;
    }

    public void setCdpCacheVTPMgmtDomain(String cdpCacheVTPMgmtDomain) {
        this.cdpCacheVTPMgmtDomain = cdpCacheVTPMgmtDomain;
    }

    public String getCdpCacheNativeVLAN() {
        return cdpCacheNativeVLAN;
    }

    public void setCdpCacheNativeVLAN(String cdpCacheNativeVLAN) {
        this.cdpCacheNativeVLAN = cdpCacheNativeVLAN;
    }

    public String getCdpCacheDuplex() {
        return cdpCacheDuplex;
    }

    public void setCdpCacheDuplex(String cdpCacheDuplex) {
        this.cdpCacheDuplex = cdpCacheDuplex;
    }

    public String getCdpCacheApplianceID() {
        return cdpCacheApplianceID;
    }

    public void setCdpCacheApplianceID(String cdpCacheApplianceID) {
        this.cdpCacheApplianceID = cdpCacheApplianceID;
    }

    public String getCdpCacheVlanID() {
        return cdpCacheVlanID;
    }

    public void setCdpCacheVlanID(String cdpCacheVlanID) {
        this.cdpCacheVlanID = cdpCacheVlanID;
    }

    public String getCdpCachePowerConsumption() {
        return cdpCachePowerConsumption;
    }

    public void setCdpCachePowerConsumption(String cdpCachePowerConsumption) {
        this.cdpCachePowerConsumption = cdpCachePowerConsumption;
    }

    public String getCdpCacheMTU() {
        return cdpCacheMTU;
    }

    public void setCdpCacheMTU(String cdpCacheMTU) {
        this.cdpCacheMTU = cdpCacheMTU;
    }

    public String getCdpCacheSysName() {
        return cdpCacheSysName;
    }

    public void setCdpCacheSysName(String cdpCacheSysName) {
        this.cdpCacheSysName = cdpCacheSysName;
    }

    public String getCdpCacheSysObjectID() {
        return cdpCacheSysObjectID;
    }

    public void setCdpCacheSysObjectID(String cdpCacheSysObjectID) {
        this.cdpCacheSysObjectID = cdpCacheSysObjectID;
    }

    public String getCdpCachePrimaryMgmtAddrType() {
        return cdpCachePrimaryMgmtAddrType;
    }

    public void setCdpCachePrimaryMgmtAddrType(String cdpCachePrimaryMgmtAddrType) {
        this.cdpCachePrimaryMgmtAddrType = cdpCachePrimaryMgmtAddrType;
    }

    public String getCdpCachePrimaryMgmtAddr() {
        return cdpCachePrimaryMgmtAddr;
    }

    public void setCdpCachePrimaryMgmtAddr(String cdpCachePrimaryMgmtAddr) {
        this.cdpCachePrimaryMgmtAddr = cdpCachePrimaryMgmtAddr;
    }

    public String getCdpCacheSecondaryMgmtAddrType() {
        return cdpCacheSecondaryMgmtAddrType;
    }

    public void setCdpCacheSecondaryMgmtAddrType(String cdpCacheSecondaryMgmtAddrType) {
        this.cdpCacheSecondaryMgmtAddrType = cdpCacheSecondaryMgmtAddrType;
    }

    public String getCdpCacheSecondaryMgmtAddr() {
        return cdpCacheSecondaryMgmtAddr;
    }

    public void setCdpCacheSecondaryMgmtAddr(String cdpCacheSecondaryMgmtAddr) {
        this.cdpCacheSecondaryMgmtAddr = cdpCacheSecondaryMgmtAddr;
    }

    public String getCdpCachePhysLocation() {
        return cdpCachePhysLocation;
    }

    public void setCdpCachePhysLocation(String cdpCachePhysLocation) {
        this.cdpCachePhysLocation = cdpCachePhysLocation;
    }

    public String getCdpCacheLastChange() {
        return cdpCacheLastChange;
    }

    public void setCdpCacheLastChange(String cdpCacheLastChange) {
        this.cdpCacheLastChange = cdpCacheLastChange;
    }

    @Override
    public String toString() {
        return "CdpCacheEntry{" +
                "cdpCacheIfIndex=" + cdpCacheIfIndex +
                ", cdpCacheDeviceIndex=" + cdpCacheDeviceIndex +
                ", cdpCacheAddressType='" + cdpCacheAddressType + '\'' +
                ", cdpCacheAddress='" + cdpCacheAddress + '\'' +
                ", cdpCacheVersion='" + cdpCacheVersion + '\'' +
                ", cdpCacheDeviceId='" + cdpCacheDeviceId + '\'' +
                ", cdpCacheDevicePort='" + cdpCacheDevicePort + '\'' +
                ", cdpCachePlatform='" + cdpCachePlatform + '\'' +
                ", cdpCacheCapabilities='" + cdpCacheCapabilities + '\'' +
                ", cdpCacheVTPMgmtDomain='" + cdpCacheVTPMgmtDomain + '\'' +
                ", cdpCacheNativeVLAN='" + cdpCacheNativeVLAN + '\'' +
                ", cdpCacheDuplex='" + cdpCacheDuplex + '\'' +
                ", cdpCacheApplianceID='" + cdpCacheApplianceID + '\'' +
                ", cdpCacheVlanID='" + cdpCacheVlanID + '\'' +
                ", cdpCachePowerConsumption='" + cdpCachePowerConsumption + '\'' +
                ", cdpCacheMTU='" + cdpCacheMTU + '\'' +
                ", cdpCacheSysName='" + cdpCacheSysName + '\'' +
                ", cdpCacheSysObjectID='" + cdpCacheSysObjectID + '\'' +
                ", cdpCachePrimaryMgmtAddrType='" + cdpCachePrimaryMgmtAddrType + '\'' +
                ", cdpCachePrimaryMgmtAddr='" + cdpCachePrimaryMgmtAddr + '\'' +
                ", cdpCacheSecondaryMgmtAddrType='" + cdpCacheSecondaryMgmtAddrType + '\'' +
                ", cdpCacheSecondaryMgmtAddr='" + cdpCacheSecondaryMgmtAddr + '\'' +
                ", cdpCachePhysLocation='" + cdpCachePhysLocation + '\'' +
                ", cdpCacheLastChange='" + cdpCacheLastChange + '\'' +
                '}';
    }
}
