package cn.mysic.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by siqishangshu on 17/10/10.
 *
 * @author siqishangshu
 * @date 17/10/10.
 * <p>
 * 如果要进行时间国际化处理，为calender增加时区
 */
public class DateGenerator {

    public static final SimpleDateFormat FM_YYMMDD = new SimpleDateFormat("yyMMdd");
    public static final SimpleDateFormat FM_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
    public static final SimpleDateFormat FM_YYYYMMDD_HHMMSS = new SimpleDateFormat("yyyyMMdd HHmmss");
    public static final SimpleDateFormat FM_HHMMSS = new SimpleDateFormat("HHmmss");
    public static final SimpleDateFormat FM_YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat ST_YYYYMMDD_HHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat FM_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat FM_HH_MM_SS = new SimpleDateFormat("HH:mm:ss");

    public static String getyyyyMMdd() {
        return FM_YYYYMMDD.format(new Date());
    }

    public static String getHHmmss() {
        return FM_HHMMSS.format(new Date());
    }

    public static String getyyyyMMdd_HHmmss() {
        return FM_YYYYMMDD_HHMMSS.format(new Date());
    }

    public static String getyyyyMMddHHmmss() {
        return FM_YYYYMMDDHHMMSS.format(new Date());
    }

    public static String getyyMMdd() {
        return FM_YYMMDD.format(new Date());
    }

    public static String getyyyyMMdd(Date date) {
        return FM_YYYYMMDD.format(date);
    }

    public static String getHHmmss(Date date) {
        return FM_HHMMSS.format(date);
    }

    public static String getyyyyMMdd_HHmmss(Date date) {
        return FM_YYYYMMDD_HHMMSS.format(date);
    }

    public static String getyyyyMMddHHmmss(Date date) {
        return FM_YYYYMMDDHHMMSS.format(date);
    }

    public static String getyyMMdd(Date date) {
        return FM_YYMMDD.format(date);
    }

    public static Date getYestoday() {
        return getDayBeforeOrAfter(-1);
    }

    public static Date getTomorrow() {
        return getDayBeforeOrAfter(1);
    }

    public static Date getToDay() {
        return getDayBeforeOrAfter(0);
    }

    /**
     * 获取几天前的日期  daysBefore 大于0 是后几天 小于0是前几天 等于0是当下
     *
     * @param daysBeforeOrAfter
     * @return
     */
    public static Date getDayBeforeOrAfter(int daysBeforeOrAfter) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, daysBeforeOrAfter);
        return calendar.getTime();
    }

    public static Date getDateByYYYYMMDDString(String dataString) {
        try {
            return FM_YYYYMMDD.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Date getDateByYYMMDDString(String dataString) {
        try {
            return FM_YYMMDD.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateByYYYYMMDD_HHMMSSString(String dataString){
        try {
            return ST_YYYYMMDD_HHMMSS.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateByYYYYMMDDHHMMSSString(String dataString){

        try {
            return FM_YYYYMMDDHHMMSS.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据YYYY-MM-DD获取Date
     * @param dataString
     * @return
     */
    public static Date getDateStringByYYYY_MM_DDString(String dataString){

        try {
            return FM_YYYY_MM_DD.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
