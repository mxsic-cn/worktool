package cn.mysic.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class PingUtil {
	public static void main(String[] args) {
			List<String> hosts = new ArrayList<String>();
			hosts.add("120.1.1.1");
			while(true){
				for (String host : hosts) {
				
				boolean flag = false;
				int status = 0;
				int rate = 100;
				String line = null;
				
				BufferedReader buffer = null;
				try {
					String os_name = System.getProperty("os.name");
					/**
					 * �����Windows����ϵͳ��ʹ������{ping -w 1250}
					 */
					if(os_name.startsWith( "Windows")){
						String command = "ping -w 1250";
						command = command + " " + host;
						Process process = Runtime.getRuntime().exec(command);
						buffer = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
						/**
						 * �Է��ص��ַ������н���
						 */
						while ((line = buffer.readLine())!=null) {
							if(line.toUpperCase().indexOf("PING")==-1 && line.indexOf(host) != -1){
								flag = true;
							}
							if(flag){
								if(line.toUpperCase().contains("TTL")) {
									status = 1;
									continue;
								} else if(line.contains("(")) {
							 		String result = line.substring(line.indexOf("(") + 1, line.indexOf("%"));
							 		if(result != null ) {
							 			boolean rs = Pattern.compile("[0-9]{1,}").matcher(result).find();
							 			if(rs){
							 				rate = Integer.parseInt(result);
							 				break;
							 			}
							 		}
							 		result = null;
							 	}
							}
						}
						
						line = null;
						buffer.close();
						buffer = null;
						process.destroy();
					//������ϵͳ����Windows�ģ���������{ping -c 4 -w 5}
					}else
					{
						String command = "ping -c 4 ";
						command = command + " " + host;
						Process process = Runtime.getRuntime().exec(command);
						buffer = new BufferedReader(new InputStreamReader(process.getInputStream(),"UTF-8"));
						/**
						 * �Է��ص��ַ������н���
						 */
						while ((line = buffer.readLine()) != null) {
							if(line.toUpperCase().indexOf("PING")==-1 && line.indexOf(host) != -1){
								flag = true;
							}
							if(flag){
								if(line.toUpperCase().contains("TTL")) {
									status = 1;
									continue;
								} else if(line.contains("%")) {
									String[] strs = line.split(",");
									for (String str : strs) {
										if(str.contains("%")){
											String result = str.substring(0, str.indexOf("%"));
											if(result != null) {
												rate = Integer.parseInt(result.trim());
											}
											break;
										}
										str = null;
									}
									strs = null;
								}
							}
						}
						
						line = null;
						buffer.close();
						buffer = null;
						process.destroy();
					}	
				
				} catch (Exception e) {
				}finally{
					if(buffer != null){
						try {
							buffer.close();
							buffer = null;
						} catch (IOException e) {
						}
					}
				}
				
				if(status == 0){
					rate = 100;
				}
				//��ͨ��ָ��
				System.out.println(new Date()+":"+host+":��ͨ��ָ��:"+status);
				//PING������
				System.out.println(new Date()+":"+host+":PING������:"+rate);
				}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
	}
	
	public static boolean ping(String host) {
		try
		{
			InetAddress address = InetAddress.getByName(host);
			return address.isReachable(3000);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
