package cn.mxsic.discovery.util;

import java.util.regex.Pattern;

/**
 * Created by liuchuan on 1/5/17.
 */
public class REUtil {

    public static boolean isMac(String mac){
        if(mac == null || mac.equals("00:00:00:00:00:00")){
            return false;
        }
        if (Pattern.compile(Constants.PatternMac).matcher(mac.toUpperCase()).find()){
            return true;
        }
        return false;
    }

    public static String upperMac(String mac){
        if(REUtil.isMac(mac)){
            return mac.toUpperCase();
        }else{
            return null;
        }
    }
    public static boolean isIp(String ip){
        if(ip == null){
            return false;
        }
        if (Pattern.compile(Constants.PatternIp).matcher(ip.toUpperCase()).find()){
            return true;
        }
        return false;
    }

    public static boolean isEffectiveIp(String ip) {
        if(REUtil.isIp(ip)){
            if(ip.startsWith("127.") ||  ip.equals("0.0.0.0")
             || ip.startsWith("224.0") || ip.endsWith(".0")
             || ip.endsWith(".255")){
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean isNumber(String portId) {
        if(Pattern.compile(Constants.Number).matcher(portId).find()){
            return true;
        }
        return false;
    }
}
