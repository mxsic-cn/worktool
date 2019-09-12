package cn.mxsic.snmp.util;

import org.snmp4j.mp.SnmpConstants;

/**
 * Created by liuchuan on java8/17/16.
 */
public class SNMPConfig {
    private int version;
    private int port;
    private int timeout;
    private int reTryies;
    private String readCommunity;
    private String writeCommunity;
    private String snmpUser;
    private int securityLevel;
    private int authAlgorithm;
    private String authPassword;
    private int privacyAlgorithm;
    private String privacyPassword;
    private static int TIMEOUT = 1000;
    private static int RETRY = 2;
    public SNMPConfig() {
        this.version = SnmpConstants.version2c;
        this.readCommunity = "public";
        this.port = SnmpConstants.DEFAULT_COMMAND_RESPONDER_PORT;
        this.timeout = this.TIMEOUT;
        this.reTryies = this.RETRY;
    }

    public SNMPConfig(int version, String readCommunity, int port) {
        this.version = version;
        this.readCommunity = readCommunity;
        this.port = port;
        this.timeout = this.TIMEOUT;
        this.reTryies = this.RETRY;
    }

    public SNMPConfig(int version, String readCommunity, int port, int timeout, int reTryies) {
        this.version = version;
        this.readCommunity = readCommunity;
        this.port = port;
        this.timeout = timeout;
        this.reTryies = reTryies;
    }

    public SNMPConfig(int version, int port, int timeout, int reTryies, String readCommunity, String writeCommunity) {
        this.version = version;
        this.port = port;
        this.timeout = timeout;
        this.reTryies = reTryies;
        this.readCommunity = readCommunity;
        this.writeCommunity = writeCommunity;
    }
    public SNMPConfig(int version, int port, String readCommunity, String writeCommunity) {
        this.version = version;
        this.port = port;
        this.timeout = this.TIMEOUT;
        this.reTryies = this.RETRY;
        this.readCommunity = readCommunity;
        this.writeCommunity = writeCommunity;
    }

    public SNMPConfig(int port, int timeout, int reTryies, String snmpUser, int securityLevel, int authAlgorithm, String authPassword, int privacyAlgorithm, String privacyPassword) {
        this.version = SnmpConstants.version3;
        this.port = port;
        this.timeout = timeout;
        this.reTryies = reTryies;
        this.snmpUser = snmpUser;
        this.securityLevel = securityLevel;
        this.authAlgorithm = authAlgorithm;
        this.authPassword = authPassword;
        this.privacyAlgorithm = privacyAlgorithm;
        this.privacyPassword = privacyPassword;
    }

    public SNMPConfig(String snmpUser, int securityLevel, int authAlgorithm, String authPassword, int privacyAlgorithm, String privacyPassword, int port) {
        this.timeout = this.TIMEOUT;
        this.reTryies = this.RETRY;
        this.version = SnmpConstants.version3;
        this.snmpUser = snmpUser;
        this.securityLevel = securityLevel;
        this.authAlgorithm = authAlgorithm;
        this.authPassword = authPassword;
        this.privacyAlgorithm = privacyAlgorithm;
        this.privacyPassword = privacyPassword;
        this.port = port;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getReTryies() {
        return reTryies;
    }

    public void setReTryies(int reTryies) {
        this.reTryies = reTryies;
    }
}
