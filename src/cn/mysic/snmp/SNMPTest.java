package cn.mysic.snmp;

import cn.mysic.snmp.mib.*;
import cn.mysic.snmp.util.SNMPConfig;
import cn.mysic.snmp.util.SNMPUtils;
import com.istuary.common.idl.AuthType;
import com.istuary.common.idl.PrivType;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuchuan on 12/15/16.
 */
public class SNMPTest {
    public static void main(String[] args ) throws Exception{

        System.out.println(  SnmpConstants.sysName.toString());
        SNMPUtils snmpUtil = new SNMPUtils();
        SNMPConfig snmpConfig = new SNMPConfig();
        snmpConfig.setVersion(SnmpConstants.version2c);
        snmpConfig.setReadCommunity("acorn-public");
//        snmpConfig.setAuthAlgorithm(AuthType.AuthSHA.getValue());
//        snmpConfig.setAuthPassword("admin@123");
//        snmpConfig.setPrivacyAlgorithm(PrivType.PrivDES.getValue());
//        snmpConfig.setPrivacyPassword("admin@123");
//        snmpConfig.setSnmpUser("snmpv3");
//        snmpConfig.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
//        snmpConfig.setSecurityLevel(SecurityLevel.AUTH_NOPRIV);
        snmpConfig.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
        List<String> list = new ArrayList<>();
        list.add("172.18.50.217");
        list.add("172.18.50.218");
        list.add("172.18.50.219");
        list.add("172.18.50.220");
//        list.add("192.168.110.249");
//        list.add("192.168.110.172");
//        list.add("192.168.110.255");
        for (String ip : list) {
            System.out.println("----------IS "+ip+"OPEN : "+snmpUtil.connectTest(ip,snmpConfig)+"-----------");
//            if(snmpUtil.connectTest(ip,snmpConfig)){
            snmpUtil.start(ip,snmpConfig);
//            Mib mib = snmpUtil.getMIBInfo();
            List<IpNetToMediaEntry> IpNetToMediaEntry = (List<IpNetToMediaEntry>)snmpUtil.getTable(IpNetToMediaEntry.class);
//            IpAddrEntry ipAddrEntry = (IpAddrEntry)snmpUtil.getObjectInfo(IpAddrEntry.class);
//              System.out.println(snmpUtil.getVariable(".1.3.6.1.2.1.17.1.1.0"));
//              System.out.println(mib);
              System.out.println(IpNetToMediaEntry.toString());
//              System.out.println(interfacesInfo.toString());
//        }
//            System.out.println(":"+snmpUtil.getVariable(".1.3.6.1.2.1.1.1.0"));
//            System.out.println(":"+snmpUtil.getVariable(".1.3.6.1.2.1.1.2.0"));
//            InterfacesInfo  interfacesInfo = (InterfacesInfo)snmpUtil.getObjectInfo(InterfacesInfo.class);
//            List<IfEntry>  ifEntries = snmpUtil.getMIBInfo().getInterfaces().getIfEntry();
//            for (IfEntry entry : interfacesInfo.getIfEntry()) {
//                if(entry.getIfType() == 6 && entry.getIfAdminStatus() == 1)
//                    System.out.println(entry.getIfDescr()+"  "+entry.getIfType()+"  "+entry.getIfOperStatus()+"  "+entry.getIfAdminStatus()+"   "+entry.getIfPhysAddress()+"   "+entry.getIfSpeed());
//            }
        }
//        List<IpNetToMediaEntry> ipNetToMediaEntries = snmpUtil.getMIBInfo().getIp().getIpNetToMediaEntry();
//        for (IpNetToMediaEntry entry : ipNetToMediaEntries) {
//            System.out.println(entry.getIpNetToMediaNetAddress()+"  "+entry.getIpNetToMediaPhysAddress()+"   "+entry.getIpNetToMediaType());
//        }
//        System.out.println(Math.round(Double.parseDouble(snmpUtil.getVariable(".1.3.6.1.2.1.25.2.2.0")+"")/(1024*1024))+"GB");
    }
}
