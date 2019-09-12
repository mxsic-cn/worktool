package cn.mxsic.snmp;

import cn.mxsic.snmp.mib.Mib;
import cn.mxsic.snmp.util.SNMPConfig;
import cn.mxsic.snmp.util.SNMPUtils;
import org.snmp4j.mp.SnmpConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuchuan on 12/15/16.
 */
public class SNMPTest {
    public static void main(String[] args) throws Exception {

        System.out.println(SnmpConstants.sysName.toString());
        SNMPUtils snmpUtil = new SNMPUtils();
        SNMPConfig snmpConfig = new SNMPConfig();
        snmpConfig.setVersion(SnmpConstants.version2c);
        snmpConfig.setReadCommunity("hna_snmp");
        List<String> list = new ArrayList<>();
        list.add("120.132.124.37");
        for (String ip : list) {
            System.out.println("----------IS " + ip + " OPEN : " + snmpUtil.connectTest(ip, snmpConfig) + "-----------");
            snmpUtil.start(ip, snmpConfig);
            Mib mib = snmpUtil.getMIBInfo();
            System.out.println(mib.toString());
        }
    }
}
