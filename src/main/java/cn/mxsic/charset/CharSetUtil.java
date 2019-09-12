package cn.mxsic.charset;

import java.io.UnsupportedEncodingException;

/**
 * Function: 编码转换器 <br>
 *
 * @author: siqishangshu <br>
 * @date: 2017-12-20 10:36:00
 */
public class CharSetUtil {

    public static byte[] getBytesFromStringToGBK(String tranMessage) {
        try {
            return tranMessage.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromBytesToGBK(byte[] bRspCode) {
        try {
            return new String(bRspCode,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getBytesFromStringByUTF_8(String tranMessage) {
        try {
            return tranMessage.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromBytesByUTF_8(byte[] bRspCode) {
        try {
            return new String(bRspCode,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
