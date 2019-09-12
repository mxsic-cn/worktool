package cn.mxsic.util;

import java.io.UnsupportedEncodingException;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2017-12-20 18:17:00
 */
public class CharSetUtil {


    public static byte[] getMessageBytes(String TranMessage,String charset) {
        try {
            return TranMessage.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String newStringByChartSet(byte[] bRspCode,String charset) {
        try {
            return new String(bRspCode, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
