package cn.mxsic.snmp.mib;

import cn.mxsic.snmp.util.SNMPOID;

/**
 * Created by liuchuan on 1/3/17.
 */
@SNMPOID(".1.3.6.1.2.1.17.4.3.1")
public class Dot1dTpFdbEntry {

        @SNMPOID("1")
        public String dot1dTpFdbAddress;

        @SNMPOID("2")
        public Integer dot1dTpFdbPort;

        @SNMPOID("3")
        public Integer dot1dTpFdbStatus;

        public String getDot1dTpFdbAddress() {
                return dot1dTpFdbAddress;
        }

        public void setDot1dTpFdbAddress(String dot1dTpFdbAddress) {
                this.dot1dTpFdbAddress = dot1dTpFdbAddress;
        }

        public Integer getDot1dTpFdbPort() {
                return dot1dTpFdbPort;
        }

        public void setDot1dTpFdbPort(Integer dot1dTpFdbPort) {
                this.dot1dTpFdbPort = dot1dTpFdbPort;
        }

        public Integer getDot1dTpFdbStatus() {
                return dot1dTpFdbStatus;
        }

        public void setDot1dTpFdbStatus(Integer dot1dTpFdbStatus) {
                this.dot1dTpFdbStatus = dot1dTpFdbStatus;
        }

        @Override
        public String toString() {
                return "Dot1dTpFdbEntry{" +
                        "dot1dTpFdbAddress='" + dot1dTpFdbAddress + '\'' +
                        ", dot1dTpFdbPort=" + dot1dTpFdbPort +
                        ", dot1dTpFdbStatus=" + dot1dTpFdbStatus +
                        '}';
        }
}
