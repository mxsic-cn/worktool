package cn.mysic.snmp.mib;

import cn.mysic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on java8/14/16.
 */
@SNMPOID(".1.3.6.1.2.1.5")
public class IcmpInfo {
    @SNMPOID("1")
    public long icmpInMsgs;
    @SNMPOID("2")
    public long icmpInErrors;
    @SNMPOID("3")
    public long icmpInDestUnreachs;
    @SNMPOID("4")
    public long icmpInTimeExcds;
    @SNMPOID("5")
    public long icmpInParmProbs;
    @SNMPOID("6")
    public long icmpInSrcQuenchs;
    @SNMPOID("7")
    public long icmpInRedirects;
    @SNMPOID("8")
    public long icmpInEchos;
    @SNMPOID("9")
    public long  icmpInEchoReps;
    @SNMPOID("10")
    public long  icmpInTimestamps;
    @SNMPOID("11")
    public long icmpInTimestampReps;
    @SNMPOID("12")
    public long icmpInAddrMasks;
    @SNMPOID("13")
    public long icmpInAddrMaskReps;
    @SNMPOID("14")
    public long icmpOutMsgs;
    @SNMPOID("15")
    public long icmpOutErrors;
    @SNMPOID("16")
    public long icmpOutDestUnreachs;
    @SNMPOID("17")
    public long icmpOutTimeExcds;
    @SNMPOID("18")
    public long icmpOutParmProbs;
    @SNMPOID("19")
    public long icmpOutSrcQuenchs;
    @SNMPOID("20")
    public long icmpOutRedirects;
    @SNMPOID("21")
    public long icmpOutEchos;
    @SNMPOID("22")
    public long icmpOutEchoReps;
    @SNMPOID("23")
    public long icmpOutTimestamps;
    @SNMPOID("24")
    public long icmpOutTimestampReps;
    @SNMPOID("25")
    public long icmpOutAddrMasks;
    @SNMPOID("26")
    public long icmpOutAddrMaskReps;

    public long getIcmpInMsgs() {
        return icmpInMsgs;
    }

    public void setIcmpInMsgs(long icmpInMsgs) {
        this.icmpInMsgs = icmpInMsgs;
    }

    public long getIcmpInErrors() {
        return icmpInErrors;
    }

    public void setIcmpInErrors(long icmpInErrors) {
        this.icmpInErrors = icmpInErrors;
    }

    public long getIcmpInDestUnreachs() {
        return icmpInDestUnreachs;
    }

    public void setIcmpInDestUnreachs(long icmpInDestUnreachs) {
        this.icmpInDestUnreachs = icmpInDestUnreachs;
    }

    public long getIcmpInTimeExcds() {
        return icmpInTimeExcds;
    }

    public void setIcmpInTimeExcds(long icmpInTimeExcds) {
        this.icmpInTimeExcds = icmpInTimeExcds;
    }

    public long getIcmpInParmProbs() {
        return icmpInParmProbs;
    }

    public void setIcmpInParmProbs(long icmpInParmProbs) {
        this.icmpInParmProbs = icmpInParmProbs;
    }

    public long getIcmpInSrcQuenchs() {
        return icmpInSrcQuenchs;
    }

    public void setIcmpInSrcQuenchs(long icmpInSrcQuenchs) {
        this.icmpInSrcQuenchs = icmpInSrcQuenchs;
    }

    public long getIcmpInRedirects() {
        return icmpInRedirects;
    }

    public void setIcmpInRedirects(long icmpInRedirects) {
        this.icmpInRedirects = icmpInRedirects;
    }

    public long getIcmpInEchos() {
        return icmpInEchos;
    }

    public void setIcmpInEchos(long icmpInEchos) {
        this.icmpInEchos = icmpInEchos;
    }

    public long getIcmpInEchoReps() {
        return icmpInEchoReps;
    }

    public void setIcmpInEchoReps(long icmpInEchoReps) {
        this.icmpInEchoReps = icmpInEchoReps;
    }

    public long getIcmpInTimestamps() {
        return icmpInTimestamps;
    }

    public void setIcmpInTimestamps(long icmpInTimestamps) {
        this.icmpInTimestamps = icmpInTimestamps;
    }

    public long getIcmpInTimestampReps() {
        return icmpInTimestampReps;
    }

    public void setIcmpInTimestampReps(long icmpInTimestampReps) {
        this.icmpInTimestampReps = icmpInTimestampReps;
    }

    public long getIcmpInAddrMasks() {
        return icmpInAddrMasks;
    }

    public void setIcmpInAddrMasks(long icmpInAddrMasks) {
        this.icmpInAddrMasks = icmpInAddrMasks;
    }

    public long getIcmpInAddrMaskReps() {
        return icmpInAddrMaskReps;
    }

    public void setIcmpInAddrMaskReps(long icmpInAddrMaskReps) {
        this.icmpInAddrMaskReps = icmpInAddrMaskReps;
    }

    public long getIcmpOutMsgs() {
        return icmpOutMsgs;
    }

    public void setIcmpOutMsgs(long icmpOutMsgs) {
        this.icmpOutMsgs = icmpOutMsgs;
    }

    public long getIcmpOutErrors() {
        return icmpOutErrors;
    }

    public void setIcmpOutErrors(long icmpOutErrors) {
        this.icmpOutErrors = icmpOutErrors;
    }

    public long getIcmpOutDestUnreachs() {
        return icmpOutDestUnreachs;
    }

    public void setIcmpOutDestUnreachs(long icmpOutDestUnreachs) {
        this.icmpOutDestUnreachs = icmpOutDestUnreachs;
    }

    public long getIcmpOutTimeExcds() {
        return icmpOutTimeExcds;
    }

    public void setIcmpOutTimeExcds(long icmpOutTimeExcds) {
        this.icmpOutTimeExcds = icmpOutTimeExcds;
    }

    public long getIcmpOutParmProbs() {
        return icmpOutParmProbs;
    }

    public void setIcmpOutParmProbs(long icmpOutParmProbs) {
        this.icmpOutParmProbs = icmpOutParmProbs;
    }

    public long getIcmpOutSrcQuenchs() {
        return icmpOutSrcQuenchs;
    }

    public void setIcmpOutSrcQuenchs(long icmpOutSrcQuenchs) {
        this.icmpOutSrcQuenchs = icmpOutSrcQuenchs;
    }

    public long getIcmpOutRedirects() {
        return icmpOutRedirects;
    }

    public void setIcmpOutRedirects(long icmpOutRedirects) {
        this.icmpOutRedirects = icmpOutRedirects;
    }

    public long getIcmpOutEchos() {
        return icmpOutEchos;
    }

    public void setIcmpOutEchos(long icmpOutEchos) {
        this.icmpOutEchos = icmpOutEchos;
    }

    public long getIcmpOutEchoReps() {
        return icmpOutEchoReps;
    }

    public void setIcmpOutEchoReps(long icmpOutEchoReps) {
        this.icmpOutEchoReps = icmpOutEchoReps;
    }

    public long getIcmpOutTimestamps() {
        return icmpOutTimestamps;
    }

    public void setIcmpOutTimestamps(long icmpOutTimestamps) {
        this.icmpOutTimestamps = icmpOutTimestamps;
    }

    public long getIcmpOutTimestampReps() {
        return icmpOutTimestampReps;
    }

    public void setIcmpOutTimestampReps(long icmpOutTimestampReps) {
        this.icmpOutTimestampReps = icmpOutTimestampReps;
    }

    public long getIcmpOutAddrMasks() {
        return icmpOutAddrMasks;
    }

    public void setIcmpOutAddrMasks(long icmpOutAddrMasks) {
        this.icmpOutAddrMasks = icmpOutAddrMasks;
    }

    public long getIcmpOutAddrMaskReps() {
        return icmpOutAddrMaskReps;
    }

    public void setIcmpOutAddrMaskReps(long icmpOutAddrMaskReps) {
        this.icmpOutAddrMaskReps = icmpOutAddrMaskReps;
    }

    @Override
    public String toString() {
        return "IcmpInfo{" +
                "icmpInMsgs=" + icmpInMsgs +
                ", icmpInErrors=" + icmpInErrors +
                ", icmpInDestUnreachs=" + icmpInDestUnreachs +
                ", icmpInTimeExcds=" + icmpInTimeExcds +
                ", icmpInParmProbs=" + icmpInParmProbs +
                ", icmpInSrcQuenchs=" + icmpInSrcQuenchs +
                ", icmpInRedirects=" + icmpInRedirects +
                ", icmpInEchos=" + icmpInEchos +
                ", icmpInEchoReps=" + icmpInEchoReps +
                ", icmpInTimestamps=" + icmpInTimestamps +
                ", icmpInTimestampReps=" + icmpInTimestampReps +
                ", icmpInAddrMasks=" + icmpInAddrMasks +
                ", icmpInAddrMaskReps=" + icmpInAddrMaskReps +
                ", icmpOutMsgs=" + icmpOutMsgs +
                ", icmpOutErrors=" + icmpOutErrors +
                ", icmpOutDestUnreachs=" + icmpOutDestUnreachs +
                ", icmpOutTimeExcds=" + icmpOutTimeExcds +
                ", icmpOutParmProbs=" + icmpOutParmProbs +
                ", icmpOutSrcQuenchs=" + icmpOutSrcQuenchs +
                ", icmpOutRedirects=" + icmpOutRedirects +
                ", icmpOutEchos=" + icmpOutEchos +
                ", icmpOutEchoReps=" + icmpOutEchoReps +
                ", icmpOutTimestamps=" + icmpOutTimestamps +
                ", icmpOutTimestampReps=" + icmpOutTimestampReps +
                ", icmpOutAddrMasks=" + icmpOutAddrMasks +
                ", icmpOutAddrMaskReps=" + icmpOutAddrMaskReps +
                '}';
    }
}
