package cn.mysic.snmp.util;

import cn.mysic.snmp.DeviceSnmp;
import org.snmp4j.mp.SnmpConstants;

/**
 * Created by liuchuan on 12/28/16.
 */
public class ConfigUtil {
    public static SNMPConfig makeConfig(DeviceSnmp deviceSnmp){
        if(deviceSnmp.getSnmpVersion() == SnmpConstants.version3){
            SNMPConfig config = new SNMPConfig(deviceSnmp.getSnmpUser(),deviceSnmp.getSecurityLevel(),deviceSnmp.getAuthAlgorithm(),deviceSnmp.getAuthPassword(),deviceSnmp.getPrivacyAlgorithm(),deviceSnmp.getPrivacyPassword(),deviceSnmp.getPort());
            return config;
        }else{
            SNMPConfig config = new SNMPConfig(deviceSnmp.getSnmpVersion(),deviceSnmp.getPort(),deviceSnmp.getReadCommunity(),deviceSnmp.getWriteCommunity());
            return config;
        }
    }
}
