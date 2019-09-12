package cn.mxsic.flyway;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Function: flyway db  <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-01-24 10:00:00
 */

public class App {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        TimeUnit.SECONDS.sleep(0);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new FlyWayRunner("N"+i));
        }
    }
}