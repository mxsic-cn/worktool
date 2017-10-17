package cn.mysic.http;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
  
/** 
 *  
 * @author Administrator 
 * 
 */  
  
public class Imei {  
  
      
    public static String getDataFromURL(String strURL, Map<String, Object> param) throws Exception {  
        URL url = new URL(strURL);  
        URLConnection conn = url.openConnection();  
        conn.setDoOutput(true);  
        conn.setConnectTimeout(30000); 

        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());  
        final StringBuilder sb;   
        if (param != null) {  
            sb = new StringBuilder(param.size() << 4);// 4�η�  
            final Set<String> keys = param.keySet();  
            for (final String key : keys) {  
                final Object value = param.get(key);  
                sb.append(key); // ���ܰ��������ַ�  
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
//  
        BufferedWriter w = new BufferedWriter(new FileWriter("d:/1.txt"));//��ȡ���������ָ���ļ�  
  
        String content = null;  
        String result = null;  
        while ((content = breader.readLine()) != null) {  
//        	if(content.contains("�豸�ͺ�")||content.contains("�豸���к�")||content.contains("����״̬")
//        			||content.contains("����״̬")||content.contains("���޵���")||content.contains("�绰֧��״̬")
//        			||content.contains("��������")||content.contains("����ʱ�俪ʼ")||content.contains("����ʱ�����")
//        			||content.contains("�豸���к�")||content.contains("�豸���к�")){
        		result += content;  
//        	}
        }  
        w.write(result);  
        w.flush();  
        w.close();  
          
        return result;  
  
    }  
      
    //main����   
    public static void main(String[] args) {  
    	List<String> list = new ArrayList<String>();
//    	list.add("358352062141190");
//    	list.add("356997064135772");
//    	list.add("351984063314926");
    	list.add("C8WN1QGDFMLD");

        String  URL = "http://www.pingguo110.com/index.php/index/search.htm?sn=C8WN1QGDFMLD"; 
         
        for (String id : list) {
        	 System.out.println("-------------------------------"+id+"-------------------------------");
        	String url = URL + id;
	        try {  
	            //���÷������󷽷�  
	            String result = getDataFromURL("http://www.imeidb.com/?imei=351984063314926", null);
	            if(result!=null){
//		             result =result.replaceAll("\t", ""); 
//		             result =result.replaceAll("null", "");
//		             result =result.replaceAll("<li class=\"clear\"><div class=\"row1 firstrow\">", "");
//		             result =result.replaceAll("</div><div class=\"row2 firstrow\">", ":");
//		             result =result.replaceAll("/div></li><li class=\"clear\"><div class=\"row1 \"", "");
//		             result =result.replaceAll("</div><div class=\"row2 \">", ":");
//		             result =result.replaceAll("</div></li>", "");
		             System.out.println("result:"+result);  
		           String[] array = result.split("<>");
		            //���url�������� �������htmҳ���Դ�ļ� ���ؽ��  
		            for (String string : array) {
//		            	 System.out.println(string);
					}
	            }else{
	            	 System.out.println("û�в鵽���豸��Ϣ");
	            }
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
        System.out.println("---------------------------------------------------------------------------");
		}
    }  
} 
