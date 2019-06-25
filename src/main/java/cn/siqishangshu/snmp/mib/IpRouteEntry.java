package cn.siqishangshu.snmp.mib;

import cn.siqishangshu.snmp.util.SNMPOID;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.4.21.1")
public class IpRouteEntry {
    @SNMPOID("1")
    public String ipRouteDest;
    @SNMPOID("2")
    public int ipRouteIfIndex;
    @SNMPOID("3")
    public int ipRouteMetric1;
    @SNMPOID("4")
    public int ipRouteMetric2;
    @SNMPOID("5")
    public int ipRouteMetric3;
    @SNMPOID("6")
    public int ipRouteMetric4;
    @SNMPOID("7")
    public String ipRouteNextHop;
    @SNMPOID("8")
    public int ipRouteType;
    @SNMPOID("9")
    public int ipRouteProto;
    @SNMPOID("10")
    public int ipRouteAge;
    @SNMPOID("11")
    public String ipRouteMask;
    @SNMPOID("12")
    public int ipRouteMetric5;
    @SNMPOID("13")
    public String ipRouteInfo;

    public String getIpRouteDest() {
        return ipRouteDest;
    }

    public void setIpRouteDest(String ipRouteDest) {
        this.ipRouteDest = ipRouteDest;
    }

    public int getIpRouteIfIndex() {
        return ipRouteIfIndex;
    }

    public void setIpRouteIfIndex(int ipRouteIfIndex) {
        this.ipRouteIfIndex = ipRouteIfIndex;
    }

    public int getIpRouteMetric1() {
        return ipRouteMetric1;
    }

    public void setIpRouteMetric1(int ipRouteMetric1) {
        this.ipRouteMetric1 = ipRouteMetric1;
    }

    public int getIpRouteMetric2() {
        return ipRouteMetric2;
    }

    public void setIpRouteMetric2(int ipRouteMetric2) {
        this.ipRouteMetric2 = ipRouteMetric2;
    }

    public int getIpRouteMetric3() {
        return ipRouteMetric3;
    }

    public void setIpRouteMetric3(int ipRouteMetric3) {
        this.ipRouteMetric3 = ipRouteMetric3;
    }

    public int getIpRouteMetric4() {
        return ipRouteMetric4;
    }

    public void setIpRouteMetric4(int ipRouteMetric4) {
        this.ipRouteMetric4 = ipRouteMetric4;
    }

    public String getIpRouteNextHop() {
        return ipRouteNextHop;
    }

    public void setIpRouteNextHop(String ipRouteNextHop) {
        this.ipRouteNextHop = ipRouteNextHop;
    }

    public int getIpRouteType() {
        return ipRouteType;
    }

    public void setIpRouteType(int ipRouteType) {
        this.ipRouteType = ipRouteType;
    }

    public int getIpRouteProto() {
        return ipRouteProto;
    }

    public void setIpRouteProto(int ipRouteProto) {
        this.ipRouteProto = ipRouteProto;
    }

    public int getIpRouteAge() {
        return ipRouteAge;
    }

    public void setIpRouteAge(int ipRouteAge) {
        this.ipRouteAge = ipRouteAge;
    }

    public String getIpRouteMask() {
        return ipRouteMask;
    }

    public void setIpRouteMask(String ipRouteMask) {
        this.ipRouteMask = ipRouteMask;
    }

    public int getIpRouteMetric5() {
        return ipRouteMetric5;
    }

    public void setIpRouteMetric5(int ipRouteMetric5) {
        this.ipRouteMetric5 = ipRouteMetric5;
    }

    public String getIpRouteInfo() {
        return ipRouteInfo;
    }

    public void setIpRouteInfo(String ipRouteInfo) {
        this.ipRouteInfo = ipRouteInfo;
    }

    @Override
    public String toString() {
        return "IpRouteEntry{" +
                "ipRouteDest='" + ipRouteDest + '\'' +
                ", ipRouteIfIndex=" + ipRouteIfIndex +
                ", ipRouteMetric1=" + ipRouteMetric1 +
                ", ipRouteMetric2=" + ipRouteMetric2 +
                ", ipRouteMetric3=" + ipRouteMetric3 +
                ", ipRouteMetric4=" + ipRouteMetric4 +
                ", ipRouteNextHop='" + ipRouteNextHop + '\'' +
                ", ipRouteType=" + ipRouteType +
                ", ipRouteProto=" + ipRouteProto +
                ", ipRouteAge=" + ipRouteAge +
                ", ipRouteMask='" + ipRouteMask + '\'' +
                ", ipRouteMetric5=" + ipRouteMetric5 +
                ", ipRouteInfo='" + ipRouteInfo + '\'' +
                '}';
    }
}
