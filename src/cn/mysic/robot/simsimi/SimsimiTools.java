package cn.mxsic.robot.simsimi;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by liuchuan on 6/13/17.
 */

/**
 * 小黄鸡机器人
 */
public class SimsimiTools {

    public static void main(String[] args) {
        try {
            System.out.println(getSimsimiContentByNiuren("哈哈"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 小黄鸡机器人
     *
     * @author ghma
     */
    public static String getSimsimiContentByNiuren(String params) throws UnsupportedEncodingException {
        StringBuffer bufferRes = new StringBuffer();
        try {
            URL realUrl = new URL("http://i.itpk.cn/api.php?question=笑话");
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 连接超时
            conn.setConnectTimeout(25000);
            // 读取超时 --服务器响应比较慢，增大时间
            conn.setReadTimeout(25000);

            HttpURLConnection.setFollowRedirects(true);
            // 请求方式
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Referer",
                    "http://i.itpk.cn/api.php");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            OutputStreamWriter out = new OutputStreamWriter(
                    conn.getOutputStream());
            // 发送请求参数
            out.write("txt=" + URLEncoder.encode(params, "UTF-8"));
            out.flush();
            out.close();

            InputStream in = conn.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String valueString = null;
            while ((valueString = read.readLine()) != null) {
                bufferRes.append(valueString);
            }
            in.close();
            if (conn != null) {
                // 关闭连接
                conn.disconnect();
            }
        } catch (Exception e) {
            System.out.println("小黄鸡接口调用出错！" + e.getMessage());
            return XiaoDouMachine.getXiaoDouMsg(params);
        }

        if (bufferRes.toString().equals("干嘛")) {
            return XiaoDouMachine.getXiaoDouMsg(params);
        }

        String finalRes = removeNews(bufferRes.toString());
        String str = new String(finalRes.getBytes(),"UTF-8");
        System.out.println("小黄鸡机器人回复:" + str );
        return finalRes;
    }

    /**
     * 屏蔽当前接口中的广告
     *
     * @return
     */
    public static String removeNews(String sendMsgs) {
        // 去除广告
        if (sendMsgs.indexOf("simsimi2") != -1) {
            sendMsgs = "偶是毛小驴，女，还木有男友，欢迎南华学子调戏   O(∩_∩)O";
        } else if (sendMsgs.indexOf("Database") != -1
                || sendMsgs.indexOf("Failed") != -1) {
            int random = (int) (Math.random() * 5);
            switch (random) {
                case 1:
                    sendMsgs = "嗯";
                    break;
                case 2:
                    sendMsgs = "聊天其它的吧";
                    break;
                case 3:
                    sendMsgs = "嗯哼";
                    break;
                case 4:
                    sendMsgs = "哎呀";
                    break;
                case 5:
                    sendMsgs = "额";
                    break;
                default:
                    sendMsgs = "嗯";
                    break;
            }
        } else if (sendMsgs.indexOf("Unauthorized access") != -1) {
            sendMsgs = "我怎么听不懂你说的什么意思呀[大哭]。咱们能换个话题吗！";
        } else if (sendMsgs.indexOf("你可以教我回答") != -1) {
            sendMsgs = "好吧";
        }
        // 替换部分内容
        sendMsgs = sendMsgs.replaceAll("傻逼", "sb");
        sendMsgs = sendMsgs.replaceAll("小九", "毛小驴");
        // sendMsgs = sendMsgs.replaceAll("小豆", "小贱贱");
        sendMsgs = sendMsgs
                .replaceAll(
                        "小豆机器人网页版地址：http://xiao.douqq.com QQ个性网http://www.xiugexing.com",
                        "伦家不懂官人的话了啦~");
        sendMsgs = sendMsgs.replaceAll("小豆", "毛小驴");
        sendMsgs = sendMsgs.replaceAll("人家", "伦家");
        sendMsgs = sendMsgs.replaceAll("林晨爱你QQ个性网http://www.xiugexing.com",
                "伦家不懂官人的话了啦~");
        return sendMsgs;
    }
}