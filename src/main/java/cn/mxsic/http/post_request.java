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
 

public class post_request { 
	
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
						
	list.add("358352062141190");
	list.add("356997064135772");
	list.add("351984063314926");
	list.add("354427064539882");
	list.add("C37NR1PHG5MV");
	list.add("359244060647741");
	list.add("354384069241065");
	list.add("352045065114380");
	list.add("C7JPD8YKG5MN");		
	list.add("352045065308495");		
	list.add("C36NDXX6G5MQ");		
	list.add("F1JPG3PWG5MQ");		
	list.add("358352062141190");	 
	list.add("356997064135772");		
	list.add("354377062642081");		
	list.add("351984063314926");		
	list.add("358359066687367");		
	list.add("359282068455097");		
	list.add("356958066009530");	
	list.add("358353065846694");	 
	list.add("358362062753605");		
	list.add("358843055015490");		
	list.add("356981066218737");		
	list.add("356998061508797");		
	list.add("359227066518271");		
	list.add("352023069337695");		
	list.add("356996068614527");		
	list.add("358352063108479");//2015-7-27
	list.add("354439065977292");
	list.add("359227067187035");
	list.add("356996068363463");
	list.add("354428068222491");
	list.add("354440067269845");
	list.add("359260060578363");
	list.add("359257067481831");//2015-07-30
	list.add("355899062093402");	//2015-08-11
	list.add("356995067324377");	
	list.add("358359069504197");	
	list.add("356959067341831");	
	list.add("FK4NN530G5QT");	
	list.add("356955066434932");	
	list.add("354376065063436");
	list.add("354380064111196");//2015-08-23
	list.add("359241065682145"); 
	list.add("359259063620511"); 
	list.add("359259063346554"); 
	list.add("359244060595817"); 
	list.add("354377064511425"); 
	list.add("358359067724318"); 
	list.add("359252061529214"); 
	list.add("359245067198919"); 
	list.add("359246061408353"); 
	list.add("359246068724737"); 
	list.add("359248063091146"); 
	list.add("359242063320738"); 
	list.add("356982066990705"); 
	list.add("358375065303368"); 
	list.add("359245061690754"); 
	
	
	try {
		for(int i=0;i<list.size();i++){
			post_request.readContentFromPost(list.get(i),i);
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
