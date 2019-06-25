package cn.siqishangshu.discovery.model;

import cn.siqishangshu.snmp.util.SNMPConfig;

import java.util.List;

/**
 * Created by liuchuan on 1/9/17.
 */
public class DiscoveryConfig {
    private List<SNMPConfig> snmpConfigs;
    private String startIP;
    private String endIP;
    private boolean usePing = true;
    private boolean LLDP = true;
    private boolean CDP = true;
    private boolean STP = true;
    private boolean OSPFNBR = true;
    private boolean ARP = true;
    private boolean PortForward = true;

    public DiscoveryConfig() {
    }

    public DiscoveryConfig(List<SNMPConfig> snmpConfigs, String startIP, String endIP, boolean usePing, boolean LLDP, boolean CDP, boolean STP, boolean OSPFNBR, boolean ARP, boolean portForward) {
        this.snmpConfigs = snmpConfigs;
        this.startIP = startIP;
        this.endIP = endIP;
        this.usePing = usePing;
        this.LLDP = LLDP;
        this.CDP = CDP;
        this.STP = STP;
        this.OSPFNBR = OSPFNBR;
        this.ARP = ARP;
        PortForward = portForward;
    }

    public boolean isARP() {
        return ARP;
    }

    public void setARP(boolean ARP) {
        this.ARP = ARP;
    }

    public List<SNMPConfig> getSnmpConfigs() {
        return snmpConfigs;
    }

    public void setSnmpConfigs(List<SNMPConfig> snmpConfigs) {
        this.snmpConfigs = snmpConfigs;
    }

    public String getStartIP() {
        return startIP;
    }

    public void setStartIP(String startIP) {
        this.startIP = startIP;
    }

    public String getEndIP() {
        return endIP;
    }

    public void setEndIP(String endIP) {
        this.endIP = endIP;
    }

    public boolean isUsePing() {
        return usePing;
    }

    public void setUsePing(boolean usePing) {
        this.usePing = usePing;
    }

    public boolean isLLDP() {
        return LLDP;
    }

    public void setLLDP(boolean LLDP) {
        this.LLDP = LLDP;
    }

    public boolean isCDP() {
        return CDP;
    }

    public void setCDP(boolean CDP) {
        this.CDP = CDP;
    }

    public boolean isSTP() {
        return STP;
    }

    public void setSTP(boolean STP) {
        this.STP = STP;
    }

    public boolean isOSPFNBR() {
        return OSPFNBR;
    }

    public void setOSPFNBR(boolean OSPFNBR) {
        this.OSPFNBR = OSPFNBR;
    }

    public boolean isPortForward() {
        return PortForward;
    }

    public void setPortForward(boolean portForward) {
        PortForward = portForward;
    }
}
