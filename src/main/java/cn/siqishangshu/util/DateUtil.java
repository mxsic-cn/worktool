package cn.siqishangshu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by siqishangshu on 17/10/10.
 */
public class DateUtil {


    public static String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }
    public static String formatTime(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        return formatter.format(date);
    }
    public static String formatDateTime(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        return formatter.format(date);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println(String.valueOf(System.currentTimeMillis()/1000));


        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.update("15475428072106561016c80a38a59e4a4342b08e930db9d7b234".getBytes());
        byte messageDigest[] = digest.digest();
        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        System.out.println(hexString.toString());
    }
}
