package cn.mysic.snmp.mib;

import cn.mysic.snmp.util.SNMPOID;

import java.util.List;

/**
 * Created by liuchuan on 8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.4")
public class IpInfo {
    @SNMPOID("1")
    public int ipForwarding;
    @SNMPOID("2")
    public int DefaultTTL;
    @SNMPOID("3")
    public long ipInReceives;
    @SNMPOID("4")
    public long ipInHdrErrors;
    @SNMPOID("5")
    public long ipInAddrErrors;
    @SNMPOID("6")
    public long ipForwDatagrams;
    @SNMPOID("7")
    public long InUnkownProtos;
    @SNMPOID("8")
    public long InDiscards;
    @SNMPOID("9")
    public long InDelivers;
    @SNMPOID("10")
    public long OutRequests;
    @SNMPOID("11")
    public long ipOutDiscards;
    @SNMPOID("12")
    public long ipOutNoRoutes;
    @SNMPOID("13")
    public int ipReasmTimeout;
    @SNMPOID("14")
    public long ipReasmReqds;
    @SNMPOID("15")
    public long ipReasmOKs;
    @SNMPOID("16")
    public long ipReasmFails;
    @SNMPOID("17")
    public long ipFragOKs;
    @SNMPOID("18")
    public long ipFragFails;
    @SNMPOID("19")
    public long ipFragCreates;
    @SNMPOID("20")
    public List<IpAddrEntry> ipAddrEntry;
    @SNMPOID("21")
    public List<IpRouteEntry> ipRouteEntry;
    @SNMPOID("22")
    public List<IpNetToMediaEntry> ipNetToMediaEntry;
    @SNMPOID("23")
    public long ipRoutingDiscards;

    public int getIpForwarding() {
        return ipForwarding;
    }

    public void setIpForwarding(int ipForwarding) {
        this.ipForwarding = ipForwarding;
    }

    public int getDefaultTTL() {
        return DefaultTTL;
    }

    public void setDefaultTTL(int defaultTTL) {
        DefaultTTL = defaultTTL;
    }

    public long getIpInReceives() {
        return ipInReceives;
    }

    public void setIpInReceives(long ipInReceives) {
        this.ipInReceives = ipInReceives;
    }

    public long getIpInHdrErrors() {
        return ipInHdrErrors;
    }

    public void setIpInHdrErrors(long ipInHdrErrors) {
        this.ipInHdrErrors = ipInHdrErrors;
    }

    public long getIpInAddrErrors() {
        return ipInAddrErrors;
    }

    public void setIpInAddrErrors(long ipInAddrErrors) {
        this.ipInAddrErrors = ipInAddrErrors;
    }

    public long getIpForwDatagrams() {
        return ipForwDatagrams;
    }

    public void setIpForwDatagrams(long ipForwDatagrams) {
        this.ipForwDatagrams = ipForwDatagrams;
    }

    public long getInUnkownProtos() {
        return InUnkownProtos;
    }

    public void setInUnkownProtos(long inUnkownProtos) {
        InUnkownProtos = inUnkownProtos;
    }

    public long getInDiscards() {
        return InDiscards;
    }

    public void setInDiscards(long inDiscards) {
        InDiscards = inDiscards;
    }

    public long getInDelivers() {
        return InDelivers;
    }

    public void setInDelivers(long inDelivers) {
        InDelivers = inDelivers;
    }

    public long getOutRequests() {
        return OutRequests;
    }

    public void setOutRequests(long outRequests) {
        OutRequests = outRequests;
    }

    public long getIpOutDiscards() {
        return ipOutDiscards;
    }

    public void setIpOutDiscards(long ipOutDiscards) {
        this.ipOutDiscards = ipOutDiscards;
    }

    public long getIpOutNoRoutes() {
        return ipOutNoRoutes;
    }

    public void setIpOutNoRoutes(long ipOutNoRoutes) {
        this.ipOutNoRoutes = ipOutNoRoutes;
    }

    public int getIpReasmTimeout() {
        return ipReasmTimeout;
    }

    public void setIpReasmTimeout(int ipReasmTimeout) {
        this.ipReasmTimeout = ipReasmTimeout;
    }

    public long getIpReasmReqds() {
        return ipReasmReqds;
    }

    public void setIpReasmReqds(long ipReasmReqds) {
        this.ipReasmReqds = ipReasmReqds;
    }

    public long getIpReasmOKs() {
        return ipReasmOKs;
    }

    public void setIpReasmOKs(long ipReasmOKs) {
        this.ipReasmOKs = ipReasmOKs;
    }

    public long getIpReasmFails() {
        return ipReasmFails;
    }

    public void setIpReasmFails(long ipReasmFails) {
        this.ipReasmFails = ipReasmFails;
    }

    public long getIpFragOKs() {
        return ipFragOKs;
    }

    public void setIpFragOKs(long ipFragOKs) {
        this.ipFragOKs = ipFragOKs;
    }

    public long getIpFragFails() {
        return ipFragFails;
    }

    public void setIpFragFails(long ipFragFails) {
        this.ipFragFails = ipFragFails;
    }

    public long getIpFragCreates() {
        return ipFragCreates;
    }

    public void setIpFragCreates(long ipFragCreates) {
        this.ipFragCreates = ipFragCreates;
    }

    public List<IpAddrEntry> getIpAddrEntry() {
        return ipAddrEntry;
    }

    public void setIpAddrEntry(List<IpAddrEntry> ipAddrEntry) {
        this.ipAddrEntry = ipAddrEntry;
    }

    public List<IpRouteEntry> getIpRouteEntry() {
        return ipRouteEntry;
    }

    public void setIpRouteEntry(List<IpRouteEntry> ipRouteEntry) {
        this.ipRouteEntry = ipRouteEntry;
    }

    public List<IpNetToMediaEntry> getIpNetToMediaEntry() {
        return ipNetToMediaEntry;
    }

    public void setIpNetToMediaEntry(List<IpNetToMediaEntry> ipNetToMediaEntry) {
        this.ipNetToMediaEntry = ipNetToMediaEntry;
    }

    public long getIpRoutingDiscards() {
        return ipRoutingDiscards;
    }

    public void setIpRoutingDiscards(long ipRoutingDiscards) {
        this.ipRoutingDiscards = ipRoutingDiscards;
    }

    @Override
    public String toString() {
        return "IpInfo{" +
                "ipForwarding=" + ipForwarding +
                ", DefaultTTL=" + DefaultTTL +
                ", ipInReceives=" + ipInReceives +
                ", ipInHdrErrors=" + ipInHdrErrors +
                ", ipInAddrErrors=" + ipInAddrErrors +
                ", ipForwDatagrams=" + ipForwDatagrams +
                ", InUnkownProtos=" + InUnkownProtos +
                ", InDiscards=" + InDiscards +
                ", InDelivers=" + InDelivers +
                ", OutRequests=" + OutRequests +
                ", ipOutDiscards=" + ipOutDiscards +
                ", ipOutNoRoutes=" + ipOutNoRoutes +
                ", ipReasmTimeout=" + ipReasmTimeout +
                ", ipReasmReqds=" + ipReasmReqds +
                ", ipReasmOKs=" + ipReasmOKs +
                ", ipReasmFails=" + ipReasmFails +
                ", ipFragOKs=" + ipFragOKs +
                ", ipFragFails=" + ipFragFails +
                ", ipFragCreates=" + ipFragCreates +
                ", ipAddrEntry=" + ipAddrEntry +
                ", ipRouteEntry=" + ipRouteEntry +
                ", ipNetToMediaEntry=" + ipNetToMediaEntry +
                ", ipRoutingDiscards=" + ipRoutingDiscards +
                '}';
    }
}
