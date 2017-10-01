package cn.mysic.snmp;

/**
 * Created by liuchuan on 12/15/16.
 */
public class DeviceSnmp {
    private String deviceId;
    private int snmpVersion;
    private String readCommunity;
    private String writeCommunity;
    private int port;
    private String snmpUser;
    private int securityLevel;
    private int authAlgorithm;
    private String authPassword;
    private int privacyAlgorithm;
    private String privacyPassword;
    private String sysName;
    private String sysDesc;
    private String sysObjectID;
    private String sysContact;
    private String sysLocation;
    private String sysUptime;
    private int ifNumber;
    private int sysService;
    private String infoTime;
    private boolean deleted;

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getSnmpUser() {
        return snmpUser;
    }

    public void setSnmpUser(String snmpUser) {
        this.snmpUser = snmpUser;
    }

    public int getAuthAlgorithm() {
        return authAlgorithm;
    }

    public void setAuthAlgorithm(int authAlgorithm) {
        this.authAlgorithm = authAlgorithm;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

    public int getPrivacyAlgorithm() {
        return privacyAlgorithm;
    }

    public void setPrivacyAlgorithm(int privacyAlgorithm) {
        this.privacyAlgorithm = privacyAlgorithm;
    }

    public String getPrivacyPassword() {
        return privacyPassword;
    }

    public void setPrivacyPassword(String privacyPassword) {
        this.privacyPassword = privacyPassword;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getSnmpVersion() {
        return snmpVersion;
    }

    public void setSnmpVersion(int snmpVersion) {
        this.snmpVersion = snmpVersion;
    }

    public String getReadCommunity() {
        return readCommunity;
    }

    public void setReadCommunity(String readCommunity) {
        this.readCommunity = readCommunity;
    }

    public String getWriteCommunity() {
        return writeCommunity;
    }

    public void setWriteCommunity(String writeCommunity) {
        this.writeCommunity = writeCommunity;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

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

    public String getSysContact() {
        return sysContact;
    }

    public void setSysContact(String sysContact) {
        this.sysContact = sysContact;
    }

    public String getSysLocation() {
        return sysLocation;
    }

    public void setSysLocation(String sysLocation) {
        this.sysLocation = sysLocation;
    }

    public String getSysUptime() {
        return sysUptime;
    }

    public void setSysUptime(String sysUptime) {
        this.sysUptime = sysUptime;
    }

    public int getIfNumber() {
        return ifNumber;
    }

    public void setIfNumber(int ifNumber) {
        this.ifNumber = ifNumber;
    }

    public int getSysService() {
        return sysService;
    }

    public void setSysService(int sysService) {
        this.sysService = sysService;
    }

    public String getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(String infoTime) {
        this.infoTime = infoTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}