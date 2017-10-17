package cn.mysic.util;

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

}
