package cn.mysic.charset;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-01-02 10:34:00
 */
public class Chars {
    public static byte[] name;

    static {
            name = ("平安测试六零零零七六六七八零零九").getBytes();
        System.out.println(new String(name));

    }

}
