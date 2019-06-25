package cn.siqishangshu.java8;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-07 11:11:00
 */
public class ClockTime {
    public static void main(String[] args) {
        System.out.println("java 8 clock");

        System.out.println(ZoneId.getAvailableZoneIds());

        ZoneId zoneId = ZoneId.of("Europe/Berlin");
        Clock clock = Clock.system(zoneId);
//        Clock clock1 = Clock.systemDefaultZone();
//        Instant instant = clock.instant();
//        long millis = clock.millis();
//        System.out.println(millis);
//        System.out.println(clock1 + "-----sd" + instant);
        final ZonedDateTime zonedDateTime = ZonedDateTime.now();
        final ZonedDateTime zonedDateTime1 = ZonedDateTime.now(clock);
        final ZonedDateTime zonedDateTime2 = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));

        System.out.println(zonedDateTime);
        System.out.println(zonedDateTime1);
        System.out.println(zonedDateTime2);

    }
}
