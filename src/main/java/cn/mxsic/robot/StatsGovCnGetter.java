package cn.mxsic.robot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Function: StatsGovCnGetter <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-25 16:50:00
 */
public class StatsGovCnGetter {

    public static String getDataFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000);
//            List l = new ArrayList();
//            l.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1)");l.add("AppleWebKit/537.36 (KHTML, like Gecko) ");l.add("Chrome/75.0.3770.142 Safari/537.36");
//            conn.getHeaderFields().put("User-Agent",l);
            InputStreamReader reader = new InputStreamReader(conn.getInputStream(),"gb2312");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String content = null;
            String result = null;
            while ((content = bufferedReader.readLine()) != null) {
//                if (content.contains("class=\"provincetr\"")) {
                    result += content;
//                }
            }
            return result;

        } catch (Exception e) {

        }
        return null;

    }
}
