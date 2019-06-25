package cn.siqishangshu.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-10-31 17:38:00
 */
public class SystemTime {

    private static final DateTimeFormatter FM_HHMMSS = DateTimeFormatter.ofPattern("HHmmss");
    public static void main(String args[]){
        //解析日期
//        String dateStr= "2016年10月25日";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
//        LocalDate date= LocalDate.parse(dateStr, formatter);
//        DateTimeFormatter FM_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//
//        ZoneId zoneId = ZoneId.systemDefault();
//        LocalDate localDate = LocalDate.parse("20181223121812", FM_YYYYMMDDHHMMSS);
//        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
//        System.out.println("fsafds:"+Date.from(zdt.toInstant()));
//
//
//        //日期转换为字符串
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm a");
//        String nowStr = now .format(format);
//        System.out.println(nowStr);
//
//
//
//        //ThreadLocal来限制SimpleDateFormat
//        System.out.println(format(new Date()));
    }


    public static String getHHmmss(int sec,ChronoUnit chronoUnit) {
        LocalDateTime now = LocalDateTime.now().plus(sec, ChronoUnit.SECONDS);
        return now.format(FM_HHMMSS);
    }


    //要在高并发环境下能有比较好的体验，可以使用ThreadLocal来限制SimpleDateFormat只能在线程内共享，这样就避免了多线程导致的线程安全问题。
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static String format(Date date) {
        return threadLocal.get().format(date);
    }
}
