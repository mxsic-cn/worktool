package cn.mysic.http;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
  
/** 
 *  
 * @author Administrator 
 * 
 */  
  
public class HttpHtml {  
  
      
    public static String getDataFromURL(String strURL, Map<String, Object> param) throws Exception {  
        URL url = new URL(strURL); 
        
        URLConnection conn = url.openConnection();  
        conn.setDoOutput(true);  
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());  
        final StringBuilder sb;   
        if (param != null) {  
            sb = new StringBuilder(param.size() << 4);// 4�η�  
            final Set<String> keys = param.keySet();  
            for (final String key : keys) {  
                final Object value = param.get(key);  
                sb.append(key); // ���ܰ������ַ�  
                sb.append('=');  
                sb.append(value);  
                sb.append('&');  
                sb.append('\n');  
            }  
            // ������ '&' ȥ��  
            sb.deleteCharAt(sb.length() - 1);  
        }else{  
            sb = new StringBuilder(100 << 4);  
        }  
        writer.write(sb.toString());  
        writer.flush();  
        writer.close();  
  
        InputStreamReader reder = new InputStreamReader(conn.getInputStream(), "utf-8");  
  
        BufferedReader breader = new BufferedReader(reder);  
 
        String content = null;  
        String result = null;  
        while ((content = breader.readLine()) != null) {  
//        	System.out.println(content);  
        	if(content.contains("设备型号")||content.contains("IMEI号")||content.contains("激活状态")
        			||content.contains("保修状态")||content.contains("保修到期")||content.contains("规格")
        			||content.contains("电话支持状态")||content.contains("颜色")
        			||content.contains("生产工厂")||content.contains("生产时间开始")||content.contains("生产时间结束")){
        		result += content;  
        	}
        }  
        return result;  
    }  
   
    public static void main(String[] args) {  
//    	List<String> list = new ArrayList<String>();

	       BufferedWriter w = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyy_mm_dd");
				Date time =new Date();
				w = new BufferedWriter(new FileWriter("d:/finder/"+sdf.format(time)+".txt"));
			} catch (IOException e2) {
				e2.printStackTrace();
			} 
	   
	        
			List<String> list = new ArrayList<String>();
								
			list.add("358352062141190");
			
			list.add("356997064135772");
			list.add("351984063314926");
			list.add("354427064539882");
			list.add("C37NR1PHG5MV");
			list.add("359244060647741");
			list.add("354384069241065");
			list.add("352045065114380");
		//

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
		//
		//
		//
			list.add("358362062753605");		
			list.add("358843055015490");		
			list.add("356981066218737");		
			list.add("356998061508797");		
			list.add("359227066518271");		
			list.add("352023069337695");		
			list.add("356996068614527");		
			
			
			list.add("359251064649979");
			

			//2015-7-27
			list.add("358352063108479");
			list.add("354439065977292");
			list.add("359227067187035");
			list.add("356996068363463");
			list.add("354428068222491");
			list.add("354440067269845");
			list.add("359260060578363");
			
//			//2015-07-30
			list.add("359257067481831");
			//2015-08-11
			list.add("355899062093402");	
			
			list.add("356995067324377");	
				
			list.add("358359069504197");	
			list.add("356959067341831");	
			list.add("FK4NN530G5QT");	
			list.add("356979064475515");	
//			list.add("358353062367355");	
			list.add("356955066434932");	
			list.add("354376065063436");
 		String  URL = "http://www.apple110.com/?sn="; 
// 		String URL = "http://www.pingguo110.com/index.php/index/search.htm?sn=";
// 			String URL = "https://getsupport.apple.com/GetproductgroupList.do?caller=wc&PGF=PGF66001&category_id=SC0200&locale=zh_CN&sn=";//C8WN1QGDFMLD"
 		int i = 0;
        for (String id : list) {
        	String url = URL + id;
//        	System.out.println(url);
        	i++;
	        try {  
	            
//	        	System.out.println("-----id/imei:"+id+"------");
	            String result = getDataFromURL(url, null);
//	            System.out.println(result);
	            if(result!=null){
		             result =result.replaceAll("\t", ""); 
		             result =result.replaceAll("null", "");
		             result =result.replaceAll("<li class=\"clear\"><div class=\"row1 firstrow\">", "");
		             result =result.replaceAll("</div><div class=\"row2 firstrow\">", ":");
		             result =result.replaceAll("/div></li><li class=\"clear\"><div class=\"row1 \"", "");
		             result =result.replaceAll("</div><div class=\"row2 \">", ":");
		             result =result.replaceAll("</div></li>", "");
		           String[] array = result.split("<>");
		           System.out.println("---第"+i+"部-----"+id+":正常-------");
		            for (String string : array) {
		            	// System.out.println(string);
		            	 try {
		     				w.write(string);
		     			} catch (IOException e1) {
		     				e1.printStackTrace();
		     			}  
					}
	            }else{
	            	 System.out.println("---第"+i+"部-----"+id+":该设备已经被换机-------");
	            	 try {
	    				w.write("---第"+i+"部----"+id+":该设备已经被换机-------\n\r");
	    			} catch (IOException e1) {
	    				e1.printStackTrace();
	    			}  
	            }
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
//        System.out.println("-----------------------------");
//        try {
//				w.write("------------------------------");
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
		}
        try {
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }  
} 
