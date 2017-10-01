package cn.mysic.discovery.util;

import cn.mysic.discovery.model.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuchuan on 1/10/17.
 */
public class ComUtil {

    public static List<String> getAllIPS(String ipfrom, String ipto) {
        ArrayList<String> ips = new ArrayList<String>();
        String[] ipfromd = ipfrom.split("\\.");
        String[] iptod = ipto.split("\\.");
        int[] int_ipf = new int[4];
        int[] int_ipt = new int[4];
        for (int i = 0; i < 4; i++) {
            int_ipf[i] = Integer.parseInt(ipfromd[i]);
            int_ipt[i] = Integer.parseInt(iptod[i]);
        }
        for (int A = int_ipf[0]; A <= int_ipt[0]; A++) {
            for (int B = (A == int_ipf[0] ? int_ipf[1] : 0); B <= (A == int_ipt[0] ? int_ipt[1] : 255); B++) {
                for (int C = (B == int_ipf[1] ? int_ipf[2] : 0); C <= (B == int_ipt[1] ? int_ipt[2] : 255); C++) {
                    for (int D = (C == int_ipf[2] ? int_ipf[3] : 0); D <= (C == int_ipt[2] ? int_ipt[3] : 255); D++) {
                        ips.add(new String(A + "." + B + "." + C + "." + D));
                    }
                }
            }
        }
        return ips;
    }

    public static boolean isUnknownPort(int port){
        if(Constants.UnknownPort == port){
            return true;
        }
        return false;
    }

    public static boolean ipOnList(String ip, List<DeviceInfo> allDeviceList) {
        for (DeviceInfo deviceInfo : allDeviceList) {
            if(deviceInfo.getDeviceIP().equals(ip) || deviceInfo.getDeviceIps().contains(ip)){
                return true;
            }
        }
        return false;
    }
}
