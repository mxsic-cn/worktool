package cn.mysic.netant;

import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Created by liuchuan on 6/12/17.
 */
public class HttpRequestUtils {


    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static String httpPost(String url,JsonObject jsonParam) throws IOException {
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String str = "";
        HttpPost method = new HttpPost(url);
        if (null != jsonParam) {
            //解决中文乱码问题
            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            method.setEntity(entity);
        }
        HttpResponse result = httpClient.execute(method);
        url = URLDecoder.decode(url, "UTF-8");
        /**请求发送成功，并得到响应**/
        if (result.getStatusLine().getStatusCode() == 200) {
//            try {
                /**读取服务器返回过来的json字符串数据**/
                str = EntityUtils.toString(result.getEntity());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        return str;
    }


    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static String httpGet(String url) throws IOException {
        //get请求返回结果
        String strResult = null;
        DefaultHttpClient client = new DefaultHttpClient();
        //发送get请求
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        /**请求发送成功，并得到响应**/
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            /**读取服务器返回过来的json字符串数据**/
            strResult = EntityUtils.toString(response.getEntity());
        } else {
            System.out.println("get请求提交失败:" + url);
        }
        return strResult;
    }
}