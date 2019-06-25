package cn.siqishangshu.time;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-07 10:09:00
 */
public class ClockTime {
    public static void main(String[] args) {

//        System.out.println(ZoneId.getAvailableZoneIds());

        if (ZoneId.getAvailableZoneIds().contains("UTC+java8")) {
            System.out.println("TRUE");
        }
        for (String s : ZoneId.getAvailableZoneIds().toString().split(",")) {
            if (s.contains("Asia")) {
                System.out.println(s);
            }
        }

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        Clock clock=Clock.system(zone1);
//       Clock clock=Clock.systemDefaultZone();//获取默认时区
//        Instant instant=clock.instant();
//        long millis = clock.millis();
//        System.out.println(millis);//可替换System.currentTimeMillis()
//        System.out.println(clock+"-----sd"+instant);
        //如果你需要特定时区的日期/时间，那么ZonedDateTime是你的选择。它持有ISO-8601格式具具有时区信息的日期与时间。下面是一些不同时区的例子：

        final ZonedDateTime zonedDatetime = ZonedDateTime.now();
        final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now( clock );
        final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now( ZoneId.of( "America/Los_Angeles" ) );

        System.out.println( zonedDatetime );
        System.out.println( zonedDatetimeFromClock );
        System.out.println( zonedDatetimeFromZone );
    }

}
