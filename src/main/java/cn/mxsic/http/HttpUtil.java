package cn.mxsic.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import cn.mxsic.util.CharSetUtil;

public class HttpUtil {

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;//+ "?" + param;
            URL realUrl = new URL(urlNameString);

            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("" + e);
            e.printStackTrace();
        } finally {
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


    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Cookie", "Hm_lvt_a02b007f29da9adf35dd0c3a17f08e73=1589766346,1589877015,1589956906,1590374392; Hm_lpvt_a02b007f29da9adf35dd0c3a17f08e73=1590375972");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestProperty("origin", "https://selfsolve.apple.com");
//            conn.setRequestProperty("referer", "https://selfsolve.apple.com/wcResults.do?newid=y");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());

            out.print(CharSetUtil.getMessageBytes(param, "gbk"));

            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"gbk"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }




    public static void main(String[] args) {
        String s= HttpUtil.sendPost("http://cus.3zang.net:22222/api/v1/alipay/notify", "");
        System.out.println(s);
//        String message = "A0012601013265                0000000213000000PA001012017122015493620171220154935000001999999                                                                                                    000000            00000000000131001                20171220154936999999                                          000000091PA00120171220154935000001326515000089773402&6217853600037518888&1232.12&3265000000001377&中国银行&RMB&20171220&充值测试&";
//        String sr = javaHttpRequest.sendPost("http://127.0.0.1:8080/api/v1/pingan/notifyByBank", message);
//        sr = javaHttpRequest.sendPost(message);
//        System.out.println(sr);
    }

    private static String sendPost(String message) {
        String res = null;

        PostMethod postMethod = new PostMethod("http://127.0.0.1:8080/api/v1/pingan/callBack");

        try {
            postMethod.setRequestEntity(new StringRequestEntity(
                    message, "text/html", "GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        postMethod.setRequestHeader("Content-Type",
                "text/xml; charset=GBK");

        HttpClient httpClient = new HttpClient();
        try {
            int resultint = httpClient.executeMethod(postMethod);
            res = new String(postMethod.getResponseBody(), "GBK");
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("http response:" + res);
        postMethod.releaseConnection();

        return null;
    }


}