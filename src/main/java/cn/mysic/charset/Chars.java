package cn.mysic.charset;

import java.io.UnsupportedEncodingException;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-01-02 10:34:00
 */
public class Chars {
    public static byte[] name;

    static {
        try {
            name = "水".getBytes("GBK");

        System.out.println(new String(name,"GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
