package cn.mxsic.week;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Function: WeekUtil <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-05-31 09:29:00
 */
public class WeekUtil {
    public static void main(String[] args) throws ParseException {
        for (Integer integer : getThisWeekDate()) {
            System.out.println(integer);
        }
//        for (Integer integer : getDatesByType(Constant.DAY_TYPE_LASTWEEK)) {
//            System.out.print(integer+" ");
//        }
//        System.out.println(" other ");
//        for (Integer integer : getDatesByType(Constant.DAY_TYPE_BEFORLASTWEEK)) {
//            System.out.print(integer+" ");
//        }

    }
    /**
     * 获取上周、上上周的日期list
     * @param type
     * @return
     */
    static public List<Integer> getDatesByType(Integer type){
        int days;
        List<Integer> daysList = new ArrayList<>();
        if(Constant.DAY_TYPE_LASTWEEK == type){
            days = 7;
        }else if (Constant.DAY_TYPE_BEFORLASTWEEK == type){
            days = 14;
        }else{
            return daysList;
        }

        Calendar c = Calendar.getInstance();
        int dayWeek = c.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        //设置一个星期的第一天 周一
        c.setFirstDayOfWeek(Calendar.MONDAY);
        int diff = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -diff - days);
        Long startTime = c.getTimeInMillis();
        c.add(Calendar.DATE, 6);
        Long endTime = c.getTimeInMillis();
        Long oneDay = 86400000L;
        Long time = startTime;
        while (time <= endTime) {
            daysList.add(Integer.parseInt(getDateFormat("yyyyMMdd").format(new Date(time))));
            time += oneDay;
        }
        return daysList;
    }

    private static SimpleDateFormat getDateFormat(String temp) {
        return new SimpleDateFormat(temp);
    }

    /**
     * 返回本周已过的日期list
     * @return
     */
    static public List<Integer> getThisWeekDate() throws ParseException {
        List<Integer> daysList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(getDateFormat("yyyyMMdd").parse("20210601"));
        Long endTime = c.getTimeInMillis();
        int dayWeek = c.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        c.setFirstDayOfWeek(Calendar.MONDAY);
        int diff = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -diff);
        Long startTime = c.getTimeInMillis();
        Long oneDay = 86400000L;
        Long time = startTime;
        while (time <= endTime) {
            daysList.add(Integer.parseInt(getDateFormat("yyyyMMdd").format(time)));
            time += oneDay;
        }
        return daysList;
    }
    static public Integer getYesterday(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
        String yesterdayDate = getDateFormat("yyyyMMdd").format(calendar.getTime());
        return Integer.parseInt(yesterdayDate);
    }

    static public Integer getToday(){
        String today = getDateFormat("yyyyMMdd").format(new Date());
        return Integer.parseInt(today);
    }
}
