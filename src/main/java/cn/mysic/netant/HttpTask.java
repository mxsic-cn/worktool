package cn.mysic.netant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuchuan on 6/12/17.
 */
public class HttpTask implements Runnable{
    private static String PATTERNS = "(>[^>]*</a>&nbsp)";
    private static String ALEXAURL = "http://www.alexa.cn/siterank/";
    private   int index = 0;
    private ResultInfo resultInfo ;


    public HttpTask(int i,ResultInfo resultInfo) {
        this.index = i;
        this.resultInfo = resultInfo;
    }


    public void run() {
        try {
                String html = getHttpResponse(ALEXAURL + this.index);
                List<String> list = getPatterns(html, PATTERNS);
                for (String domain : list) {
                    LogUtil.writeLog(domain + "." + "    0");
                }
                System.out.println("page: " + this.index);
            } catch (Exception e) {
                this.resultInfo.addFiledIndex(this.index);
                System.out.println("ERROR Page " + this.index);
            }
    }
    public static List getPatterns(String source, String matcher){
        List<String> result=new ArrayList<>();
        List<String> temp=new ArrayList<>();
        Pattern p=Pattern.compile(matcher);
        Matcher m=p.matcher(source);
        while(m.find())
        {
            temp.add(m.group());
        }
        temp.remove(temp.size()-1);
        for(String s1:temp){
            s1 = s1.replace(">","");
            s1 = s1.replace("</a&nbsp","");
            result.add(s1.toLowerCase());
        }
        return result;
    }
    public static String getHttpResponse(String url) throws IOException {
        return  sendGet(url,null);
    }


    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ;//+ "?" + param;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            e.printStackTrace();
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}
