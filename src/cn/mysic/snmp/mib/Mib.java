package cn.mysic.snmp.mib;

import cn.mysic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1")
public class Mib {
    @SNMPOID("1")
    public SystemInfo system;
    @SNMPOID("2")
    public InterfacesInfo interfaces;
    @SNMPOID("3")
    public AtInfo at;
    @SNMPOID("4")
    public IpInfo ip;
    @SNMPOID("5")
    public IcmpInfo icmp;
    @SNMPOID("6")
    public TcpInfo tcp;
    @SNMPOID("7")
    public UdpInfo udp;
    @SNMPOID("8")
    public EgpInfo egp;
    @SNMPOID("11")
    public SnmpInfo snmp;

    public Mib() {
        this.system = new SystemInfo();
        this.interfaces = new InterfacesInfo();
        this.at = new AtInfo();
        this.ip = new IpInfo();
        this.icmp = new IcmpInfo();
        this.tcp = new TcpInfo();
        this.udp = new UdpInfo();
        this.egp = new EgpInfo();
        this.snmp = new SnmpInfo();
    }

    public SystemInfo getSystem() {
        return system;
    }

    public void setSystem(SystemInfo system) {
        this.system = system;
    }

    public InterfacesInfo getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(InterfacesInfo interfaces) {
        this.interfaces = interfaces;
    }

    public AtInfo getAt() {
        return at;
    }

    public void setAt(AtInfo at) {
        this.at = at;
    }

    public IpInfo getIp() {
        return ip;
    }

    public void setIp(IpInfo ip) {
        this.ip = ip;
    }

    public IcmpInfo getIcmp() {
        return icmp;
    }

    public void setIcmp(IcmpInfo icmp) {
        this.icmp = icmp;
    }

    public TcpInfo getTcp() {
        return tcp;
    }

    public void setTcp(TcpInfo tcp) {
        this.tcp = tcp;
    }

    public UdpInfo getUdp() {
        return udp;
    }

    public void setUdp(UdpInfo udp) {
        this.udp = udp;
    }

    public EgpInfo getEgp() {
        return egp;
    }

    public void setEgp(EgpInfo egp) {
        this.egp = egp;
    }

    public SnmpInfo getSnmp() {
        return snmp;
    }

    public void setSnmp(SnmpInfo snmp) {
        this.snmp = snmp;
    }

    @Override
    public String toString() {
        return "Mib{" +
                "system=" + system +
                ", interfaces=" + interfaces +
                ", at=" + at +
                ", ip=" + ip +
                ", icmp=" + icmp +
                ", tcp=" + tcp +
                ", udp=" + udp +
                ", egp=" + egp +
                ", snmp=" + snmp +
                '}';
    }
}
