package cn.mxsic.array;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-05-29 10:18:00
 */
public class Arrays {

    private static DateTimeFormatter FM_HHMMSS = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static DateTimeFormatter FM_YYYYMMDD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static void main(String[] args) {
        String[] values = new String[]{"213","123","1242"};
        System.out.println("");
        System.out.println(java.util.Arrays.asList(values).toString().substring(1));


        StringBuilder valueStr = new StringBuilder("");
        for (int i = 0; i < values.length; i++) {
            valueStr.append(","+values[i]);
        }
        /**
         * 乱码解决，这段代码在出现乱码时使用
         * valueStr = new String(valueStr.getBytes(ALIPAY_ISO_8859_1),  ALIPAY_CHARSET);
         */

        System.out.println(valueStr.toString().substring(1));


        System.out.println(getyyyyMMdd() +" "+ getHHmmss());


    }
    public static String getyyyyMMdd() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FM_YYYYMMDD);
    }

    public static String getHHmmss() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(FM_HHMMSS);
    }
}
