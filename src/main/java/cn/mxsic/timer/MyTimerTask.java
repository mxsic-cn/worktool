package cn.mxsic.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Function: MyTimerTask <br>
 * TODO :简单单机场景，
 * 多个task 一个异常全部失败。
 * 一直重复场景，却没有隔离保障，，差评。
 * @author: siqishangshu <br>
 * @date: 2020-08-25 15:39:00
 */

public class MyTimerTask {

    public static void main(String[] args) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Run timerTask:" + new Date());
            }
        };
        // 定义任务 2
        TimerTask timerTask2 = new TimerTask() {
             int times =5;
            @Override
            public void run() {
                System.out.println("Run timerTask 2：" + new Date());
                times--;
                if(times<=0){
                    //never end
                    System.out.println("Run timerTask 2 OVER");
                    return;
                }
            }
        };


        Timer timer = new Timer();
        timer.schedule(timerTask,1000,3000);
        timer.schedule(timerTask2,1000,3000);
    }
}
