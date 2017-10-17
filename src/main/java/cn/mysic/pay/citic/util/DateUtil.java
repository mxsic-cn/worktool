package cn.mysic.pay.citic.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by siqishangshu on 17/10/10.
 */
public class DateUtil {


    public static String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(new Date());
    }
    public static String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        return formatter.format(new Date());
    }
    public static String getDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        return formatter.format(new Date());
    }

}
