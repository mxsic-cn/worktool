package cn.mxsic.snmp;

import cn.mxsic.net.PingUtil;

import java.util.Calendar;

/**
 * Created by liuchuan on 12/15/16.
 */
public class PIngTest {
    public static void main(String[] args ) throws Exception{

        //        snmpUtil.start("list.add("192.168.110.96",snmpConfig);
        String pre = "192.168.110.";
        int count = 1;
        for (int i = 1;i<255;i++) {
            Calendar calendar = Calendar.getInstance();
            long start = calendar.getTime().getTime();

            if(PingUtil.ping(pre + i)) {
                System.out.println("--"+count+++"---PING-----" + pre + i + "------"+ (Calendar.getInstance().getTime().getTime()-start)+"-----");
            }else{
//                System.out.println(   pre + i + "-----------");
            }

        }
    }
}
