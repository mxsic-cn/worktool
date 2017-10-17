package cn.mysic.snmp.mib;

import cn.mysic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.6.13.1")
public class TcpConnEntry {
    @SNMPOID("1")
    public int tcpConnState;
    @SNMPOID("2")
    public String tcpConnLocalAddress;
    @SNMPOID("3")
    public int tcpConnLocalPort;
    @SNMPOID("4")
    public String tcpConnRemAddress;
    @SNMPOID("5")
    public int tcpConnRemPort;

    public int getTcpConnState() {
        return tcpConnState;
    }

    public void setTcpConnState(int tcpConnState) {
        this.tcpConnState = tcpConnState;
    }

    public String getTcpConnLocalAddress() {
        return tcpConnLocalAddress;
    }

    public void setTcpConnLocalAddress(String tcpConnLocalAddress) {
        this.tcpConnLocalAddress = tcpConnLocalAddress;
    }

    public int getTcpConnLocalPort() {
        return tcpConnLocalPort;
    }

    public void setTcpConnLocalPort(int tcpConnLocalPort) {
        this.tcpConnLocalPort = tcpConnLocalPort;
    }

    public String getTcpConnRemAddress() {
        return tcpConnRemAddress;
    }

    public void setTcpConnRemAddress(String tcpConnRemAddress) {
        this.tcpConnRemAddress = tcpConnRemAddress;
    }

    public int getTcpConnRemPort() {
        return tcpConnRemPort;
    }

    public void setTcpConnRemPort(int tcpConnRemPort) {
        this.tcpConnRemPort = tcpConnRemPort;
    }

    @Override
    public String toString() {
        return "TcpConnTable{" +
                "tcpConnState=" + tcpConnState +
                ", tcpConnLocalAddress=" + tcpConnLocalAddress +
                ", tcpConnLocalPort=" + tcpConnLocalPort +
                ", tcpConnRemAddress='" + tcpConnRemAddress + '\'' +
                ", tcpConnRemPort=" + tcpConnRemPort +
                '}';
    }
}
