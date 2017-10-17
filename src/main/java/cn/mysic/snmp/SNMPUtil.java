package cn.mysic.snmp;

import java.net.Socket;

/**
 * Created by liuchuan on 12/23/16.
 */
public class SNMPUtil {

    public static boolean isPortOpen(String IP,int port){
        Socket client = null;
        try{
            client = new Socket(IP, port);
            client.setSoTimeout(1000);
            client.close();
            System.out.println("---------SNMP is OPEN -----------");
            return true;
        }catch(Exception e){
            System.out.println("---------SNMP is CLOSE -----------");
            return false;
        }
    }

}
