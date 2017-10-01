package cn.mysic.netant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        return HttpRequestUtils.httpGet(url);
    }



}
