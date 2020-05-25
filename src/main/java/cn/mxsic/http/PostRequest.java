package cn.mxsic.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
 

public class PostRequest {
	
public static final String GET_URL = "http://www.jiucool.com/request.php?key=j0r56u2"; 

 public static final String POST_URL = "https://selfsolve.apple.com/wcResults.do"; 
 
    public static void readContentFromGet() throws IOException{ 
        // ƴ��get�����URL�ִ���ʹ��URLEncoder.encode������Ͳ��ɼ��ַ����б��� 
        String getURL = GET_URL + "&activatecode=" + URLEncoder.encode("�ÿᲩ��", "utf-8"); 
        URL getUrl = new URL(getURL); 
        // ����ƴ�յ�URL�������ӣ�URL.openConnection���������URL�����ͣ� 
        // ���ز�ͬ��URLConnection����Ķ�������URL��һ��http�����ʵ�ʷ��ص���HttpURLConnection 
        HttpURLConnection connection = (HttpURLConnection) getUrl .openConnection(); 
        // �������ӣ�����ʵ����get requestҪ����һ���connection.getInputStream()�����вŻ��������� 
        // ������ 
        connection.connect(); 
        // ȡ������������ʹ��Reader��ȡ 
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//���ñ���,������������ 
        System.out.println("============================="); 
        System.out.println("Contents of get request"); 
        System.out.println("============================="); 
        String lines; 
        while ((lines = reader.readLine()) != null){ 
        //lines = new String(lines.getBytes(), "utf-java8");
            System.out.println(lines); 
        } 
        reader.close(); 
        // �Ͽ����� 
        connection.disconnect(); 
        System.out.println("============================="); 
        System.out.println("Contents of get request ends"); 
        System.out.println("============================="); 
    } 
public static void readContentFromPost(String serid,int i) throws IOException{ 
        // Post�����url����get��ͬ���ǲ���Ҫ������ 
        URL postUrl = new URL(POST_URL); 
        // ������ 
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection(); 
        // Output to the connection. Default is 
        // false, set to true because post 
        // method must write something to the 
        // connection 
        // �����Ƿ���connection�������Ϊ�����post���󣬲���Ҫ���� 
        // http�����ڣ������Ҫ��Ϊtrue 
       connection.setDoOutput(true); 
        // Read from the connection. Default is true. 
//        connection.setDefaultRequestProperty("Remote Address", "127.0.0.1:8580");
// connection.setDoInput(true); 
        // Set the post method. Default is GET 
        connection.setRequestMethod("POST"); 
        // Post cannot use caches 
        // Post ������ʹ�û��� 
// connection.setUseCaches(false); 
        // This method takes effects to 
        // every instances of this class. 
        // URLConnection.setFollowRedirects��static���������������е�URLConnection���� 
        // connection.setFollowRedirects(true); 

        // This methods only 
        // takes effacts to this 
        // instance. 
        // URLConnection.setInstanceFollowRedirects�ǳ�Ա�������������ڵ�ǰ���� 
//  connection.setInstanceFollowRedirects(true); 
        // Set the content type to urlencoded, 
        // because we will write 
        // some URL-encoded content to the 
        // connection. Settings above must be set before connect! 
        // ���ñ������ӵ�Content-type������Ϊapplication/x-www-form-urlencoded�� 
        // ��˼��������urlencoded�������form�������������ǿ��Կ������Ƕ���������ʹ��URLEncoder.encode 
        // ���б��� 
//   connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); 
//  connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.java8");
        // ���ӣ���postUrl.openConnection()���˵����ñ���Ҫ��connect֮ǰ��ɣ� 
        // Ҫע�����connection.getOutputStream�������Ľ���connect�� 
        connection.connect(); 
        DataOutputStream out = new DataOutputStream(connection 
                .getOutputStream()); 
        // The URL-encoded contend 
        // ���ģ�����������ʵ��get��URL��'?'��Ĳ����ַ���һ�� 
        
        String content = "sn="+serid+"&cn=&locale=&caller=&num=115859"; // + URLEncoder.encode("", "utf-java8");
        // DataOutputStream.writeBytes���ַ����е�16λ��unicode�ַ���8λ���ַ���ʽд�������� 
        out.writeBytes(content); 
        out.flush(); 
        out.close(); // flush and close 
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//���ñ���,������������ 
        String line=""; 
//        System.out.println("============"+serid+"=============="); 
        while ((line = reader.readLine()) != null){ 
             line = new String(line.getBytes(), "utf-8");
//             System.out.println(line);
        	if(line.contains("warrantyPage.warrantycheck.displayProductInfo")){
        		 System.out.println("====��"+i+"����"+serid+":��δ����====="); 
        	}else if(line.contains("var errorMsg")){
        		System.out.println("====��"+i+"����"+serid+":�Ѿ�����====="); 
//        		System.out.println(line); 
        	}
        } 
        reader.close(); 
        connection.disconnect(); 
    }

public static void main(String[] args) {  
	 
	List<String> list = new ArrayList<String>();
						
	list.add("356982066990705");
	list.add("358375065303368"); 
	list.add("359245061690754"); 
	
	
	try {
		for(int i=0;i<list.size();i++){
			PostRequest.readContentFromPost(list.get(i),i);
			Thread.sleep(50000);
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


} 
