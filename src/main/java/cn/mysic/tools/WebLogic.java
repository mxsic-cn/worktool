package com;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebLogic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Auto-generated method stub
		String resoruceIp = "10.142.50.227";
		int port = 7001;
		String username = "weblogic";
		String password = "weblogic";
		String column1 = "WebLogicAgent/agentServlet";
		String column2 = "AdminServer";
		HttpURLConnection con = null;
		String result = "";
		try {
			String url = "http://" + resoruceIp + ":" + port + "/" + column1
					+ "?user=" + username + "&pwd=" + password + "&serverName="
					+ column2 + "";
			con = (HttpURLConnection) new URL(url).openConnection();
			int state = con.getResponseCode();
			if (200 == state) {
				result = "WEBLOGIC AGENT连接成功";
			}else{
				result = "WEBLOGIC AGENT连接失败";
			}
		} catch (IOException e) {
			result = "WEBLOGIC AGENT连接失败";
		} finally {
			System.out.println(result);
			if(con!=null){
				con.disconnect();
			}
		}

	}

}
