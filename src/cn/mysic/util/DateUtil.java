package cn.mysic.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by siqishangshu on 17/10/10.
 */
public class DateUtil {

    public static String formatDateTimes(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }
    public static String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
    public static String formatTime(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(date);
    }
    public static String formatDateTime(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
        return formatter.format(date);
    }

}
