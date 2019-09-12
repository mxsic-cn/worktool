package cn.mxsic.breaker;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Function: PasswordBreaker <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-11-12 14:10:00
 */
public class PasswordBreaker {

    public static void main(String[] args) {
//        String url = "http://www.zgsljsw.com/login.do";
//        String data = "loginname=admin&pwd=123456&ValidateCode=5413&RememberMe=false";
        String domain="http://ems.juneyaoair.com:9001";
        String url =domain+"/api/sendSmsBatch.action";
        String data ="{\n" +
                "    \"userId\": \"10242\", \n" +
                "    \"password\": \"c0d6f2a93d8f04fc75e7a062c4262689\", \n" +
                "    \"smsType\": \"Logistics\", \n" +
                "    \"batchMobiles\": [\"15114867126\"], \n" +
                "    \"batchContent\": [\"短信内容1\"], \n" +
                "    \"endSendTime\": \"2018-11-14 10:00:00\", \n" +
                "    \"subPort\": \"005\", \n" +
                "    \"remark1\": \"001\"\n" +
                "}";
        System.out.println(sendPost(url, data));
    }

//    public static String sendPost(String url, String param) {
//        PrintWriter out = null;
//        BufferedReader in = null;
//        String result = "";
//        try {
//            URL realUrl = new URL(url);
//            URLConnection conn = realUrl.openConnection();
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("origin", "https://www.baidu.com");
//            conn.setRequestProperty("referer", "https://www.baidu.com/wcResults.do?newid=y");
//            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            out = new PrintWriter(conn.getOutputStream());
//
//            out.print(CharSetUtil.getMessageBytes(param, "UTF-8"));
//
//            out.flush();
//            in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return result;
//    }

    private static String sendPost(String url, String message) {
        String res = null;
        PostMethod postMethod = new PostMethod(url);
        try {
            postMethod.setRequestEntity(new StringRequestEntity(
                    message, "text/html", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        postMethod.setRequestHeader("Content-Type",
                "text/xml; charset=utf-8");

        HttpClient httpClient = new HttpClient();
        try {
            int resultint = httpClient.executeMethod(postMethod);
            System.out.println(resultint);
            res = new String(postMethod.getResponseBody(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("http response:" + res);
        postMethod.releaseConnection();
        return res;
    }


}
