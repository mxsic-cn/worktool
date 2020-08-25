package cn.mxsic.util;

import com.alibaba.fastjson.JSON;
import com.app.icargo.core.ConfigManager;
import com.app.icargo.core.ICargoClient;
import com.app.icargo.core.ICargoConfig;
import com.app.icargo.domain.ho.booking.entity.NoticeCondition;
import com.app.icargo.domain.ho.booking.response.NoticeInfoResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

/**
 * Function: MathUtil <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-18 14:10:00
 */
public class MathUtil {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                NoticeCondition condition = new NoticeCondition();
                try {
                    long time = 5;//new  Double(Math.random()*100.0).intValue();
                    TimeUnit.SECONDS.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                condition.setUserId("SHAFXH");
                condition.setPageSize(5);
                condition.setPageNum(1);
                condition.setType(2);
                condition.setScStatus("N");
                ICargoConfig iCargoConfig = new ICargoConfig("http://cargo.juneyaoair.com:8003");
                ConfigManager.setConfig(iCargoConfig);
                NoticeInfoResponse response = ICargoClient.queryNoticeInfo(condition);
                System.out.println(JSON.toJSONString(response));

//                String json = "{\"MsgId\":0,\"PageNum\":200,\"PageSize\":5,\"RecId\":0,\"ScStatus\":\"Y\",\"Type\":2,\"UserId\":\"SHAFXH\"}";
//                System.out.println(sendPost("http://cargo.juneyaoair.com:8003/Services/Messageboard.svc/QueryNoticeInfo", json));

            }).start();
        }
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
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());

            out.print(param);

            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
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


}
