package cn.mysic.charset;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-01-02 10:05:00
 */
public class CharSetConvert {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        byte[] name = Chars.name;
//        System.out.println((new String(name,"UTF-8")));
//        String string = new String(name,"UTF-8");
//        System.out.println(CharSetUtil.getStringFromBytesToGBK(
//                        (new String(name,"GBK")).getBytes("UTF-8")));
//        System.out.println(CharSetUtil.getStringFromBytesToGBK(
//                CharSetUtil.getBytesFromStringByUTF_8(
//                        new String(name))));

//        System.out.println(new String(new String(name, "GBK").getBytes("unicode"), "GBK"));
//        System.out.println(CharSetUtil.getStringFromBytesByUTF_8(new String(name,"unicode")));
        String s = "你好&平安平安平安平安12";

// 编码
        byte[] utf = s.getBytes("utf-8");
        byte[] gbk = s.getBytes("gbk");

        System.out.println("utf-8编码：" + Arrays.toString(utf)); // [-28, -67, -96, -27, -91, -67]  6个字节
        System.out.println("gbk编码：" + Arrays.toString(gbk)); // [-60, -29, -70, -61]<span style="white-space:pre;"> </span>4个字节

// 解码
        String s1 = new String(utf, "utf-8"); // 你好
        String s2 = new String(utf, "gbk");// gbk解码：浣犲ソ gbk用2个字节解码，所以会多一个字符
        String s3 = new String(gbk, "utf-8");// gbk用utf-8解码：��� <span style="white-space:pre;"> </span>utf-8解码需要6个字节
        String s4 = new String(gbk, "gbk");// gbk解码：你好  gbk用2个字节解码，所以会多一个字符

        System.out.println("--------------------");
        System.out.println("utf-8用utf-8解码：" + s1);
        System.out.println("utf-8用gbk解码：" + s2);
        System.out.println("gbk用utf-8解码：" + s3);
        System.out.println("gbk用gbk解码：" + s4);
        System.out.println("---------------------");

        System.out.println("gbk用utf-8解码 用utf-8编码回去");
        s3 = new String(s3.getBytes("utf-8"), "gbk");  // 锟斤拷锟?   gbk用utf-8解码后无法编回去
        System.out.println(s3);
        System.out.println("utf-8用gbk解码 用gbk编码回去");
        s3 = new String(s2.getBytes("gbk"), "utf-8");  //  浣犲ソ&骞冲畨骞冲畨骞冲畨骞冲畨1   gbk用utf-8解码后无法编回去
        System.out.println(s2);
    }
}
