package cn.mxsic.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by liuchuan on 3/1/17.
 */
public class LoaclIP {

    public static void main(String[] agrs) throws Exception{
      InetAddress addr = InetAddress.getLocalHost();
//      System.out.println(addr.getHostAddress().toString());//获得本机IP
//      System.out.println(addr.getHostName().toString());//获得本机名称

        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements())
        {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            System.out.println(netInterface.getName());
            Enumeration addresses = netInterface.getInetAddresses();

            while (addresses.hasMoreElements())
            {
                ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address)
                {
                    System.out.println("本机的IP = " + ip.getHostAddress());
                }
            }
        }
    }

}
