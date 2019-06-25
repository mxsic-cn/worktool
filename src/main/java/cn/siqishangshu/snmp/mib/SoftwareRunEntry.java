package cn.siqishangshu.snmp.mib;

import cn.siqishangshu.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 1/3/17.
 */
@SNMPOID(".1.3.6.1.2.1.25.4.2.1")
public class SoftwareRunEntry {

    @SNMPOID("1")
    public int hrSWRunIndex;

    @SNMPOID("2")
    public String hrSWRunName;

    @SNMPOID("3")
    public String hrSWRunID;

    @SNMPOID("4")
    public String hrSWRunPath;

    @SNMPOID("5")
    public String hrSWRunParameters;

    @SNMPOID("6")
    public int hrSWRunType;

    @SNMPOID("7")
    public int hrSWRunStatus;

    @SNMPOID("8")
    public String hrSWRunCpu;

    @SNMPOID("9")
    public String hrSWRunMem;

    public int getHrSWRunIndex() {
        return hrSWRunIndex;
    }

    public void setHrSWRunIndex(int hrSWRunIndex) {
        this.hrSWRunIndex = hrSWRunIndex;
    }

    public String getHrSWRunName() {
        return hrSWRunName;
    }

    public void setHrSWRunName(String hrSWRunName) {
        this.hrSWRunName = hrSWRunName;
    }

    public String getHrSWRunID() {
        return hrSWRunID;
    }

    public void setHrSWRunID(String hrSWRunID) {
        this.hrSWRunID = hrSWRunID;
    }

    public String getHrSWRunPath() {
        return hrSWRunPath;
    }

    public void setHrSWRunPath(String hrSWRunPath) {
        this.hrSWRunPath = hrSWRunPath;
    }

    public String getHrSWRunParameters() {
        return hrSWRunParameters;
    }

    public void setHrSWRunParameters(String hrSWRunParameters) {
        this.hrSWRunParameters = hrSWRunParameters;
    }

    public int getHrSWRunType() {
        return hrSWRunType;
    }

    public void setHrSWRunType(int hrSWRunType) {
        this.hrSWRunType = hrSWRunType;
    }

    public int getHrSWRunStatus() {
        return hrSWRunStatus;
    }

    public void setHrSWRunStatus(int hrSWRunStatus) {
        this.hrSWRunStatus = hrSWRunStatus;
    }

    public String getHrSWRunCpu() {
        return hrSWRunCpu;
    }

    public void setHrSWRunCpu(String hrSWRunCpu) {
        this.hrSWRunCpu = hrSWRunCpu;
    }

    public String getHrSWRunMem() {
        return hrSWRunMem;
    }

    public void setHrSWRunMem(String hrSWRunMem) {
        this.hrSWRunMem = hrSWRunMem;
    }

    @Override
    public String toString() {
        return "SoftwareRunEntry{" +
                "hrSWRunIndex=" + hrSWRunIndex +
                ", hrSWRunName='" + hrSWRunName + '\'' +
                ", hrSWRunID='" + hrSWRunID + '\'' +
                ", hrSWRunPath='" + hrSWRunPath + '\'' +
                ", hrSWRunParameters='" + hrSWRunParameters + '\'' +
                ", hrSWRunType=" + hrSWRunType +
                ", hrSWRunStatus=" + hrSWRunStatus +
                ", hrSWRunCpu='" + hrSWRunCpu + '\'' +
                ", hrSWRunMem='" + hrSWRunMem + '\'' +
                '}';
    }
}
