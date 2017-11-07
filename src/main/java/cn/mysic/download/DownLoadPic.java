package cn.mysic.download;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
 

public class DownLoadPic { 
	
public static final String POST_URL = "http://www.guofenchaxun.com/captchaa.php"; 
 
   
public static void readContentFromPost(String filename) throws IOException{ 
        URL postUrl = new URL(POST_URL); 
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection(); 
       connection.setDoOutput(true); 
        connection.setRequestMethod("GET"); 
        connection.connect(); 
        DataInputStream in = new DataInputStream(connection.getInputStream());
    	String filePath = "/Users/siqishangshu/Downloads/guofenpic/"+filename;
//    	 DataOutputStream out1 = new DataOutputStream(connection 
//                 .getOutputStream()); 
//         String content = "?0.590208588866517";  
//         out1.writeBytes(content); 
//         out1.flush(); 
//         out1.close(); 
        DataOutputStream out = new DataOutputStream(new FileOutputStream(filePath));

		byte[] buffer = new byte[4096];
		int count = 0;
		while ((count = in.read(buffer)) > 0) {
			out.write(buffer, 0, count);
//			System.out.println(buffer);
		}
		out.close();
		in.close();
		connection.disconnect();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
//         String line = "";
//        while ((line = reader.readLine()) != null){ 
//             line = new String(line.getBytes(), "utf-java8");
//        		System.out.println(line); 
//        } 
//        reader.close(); 
//        connection.disconnect(); 
    }

public static void main(String[] args) {  
	 
	List<String> list = new ArrayList<String>();
	
	
	try {
		for(int i=200;i<300;i++){
//			DownLoadPic.readContentFromPost(list.get(i),i);
			String pic = "tem"+i+".png";
			DownLoadPic.readContentFromPost(pic);
			System.out.println(pic);
			Thread.sleep(2000);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


} 
