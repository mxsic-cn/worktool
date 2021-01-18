package cn.mxsic.unique;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Function: IpMacId <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-01-18 09:54:00
 */
public class IpMacId {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
           String ip = address.getHostAddress();
           byte[] mac= NetworkInterface.getByInetAddress(address).getHardwareAddress();
            StringBuffer sb = new StringBuffer("");
            for(int i=0; i<mac.length; i++) {
                if(i!=0) {
                    sb.append("-");
                }
                //字节转换为整数
                int temp = mac[i]&0xff;
                String str = Integer.toHexString(temp);
                if(str.length()==1) {
                    sb.append("0"+str);
                }else {
                    sb.append(str);
                }
            }
            System.out.println("IP:"+ip+"MAC:"+sb);
            System.out.println((ip+sb).hashCode());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
