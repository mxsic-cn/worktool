package cn.mysic.longs;

import java.util.Date;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-06-01 10:33:00
 */
public class Longs {
    public static void main(String[] args) {
        System.out.println(new Date());
        for (int i = 0; i < 100000; i++) {
            Long.valueOf("29875192887387493");
        }
        System.out.println(new Date());
        for (int i = 0; i < 100000; i++) {
            Long.parseLong("29875192887387493");
        }
        System.out.println(new Date());
    }
}
