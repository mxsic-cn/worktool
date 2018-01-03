package cn.mysic.charset;


import java.io.UnsupportedEncodingException;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-01-02 10:05:00
 */
public class CharSetConvert {
    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] name = Chars.name;
        System.out.println(CharSetUtil.getStringFromBytesToGBK(name));
//        System.out.println(CharSetUtil.getBytesFromStringToGBK(name));
        System.out.println(new String(new String(name,"GBK").getBytes("unicode"),"GBK"));
//        System.out.println(CharSetUtil.getStringFromBytesByUTF_8(new String(name,"unicode")));
    }
}
