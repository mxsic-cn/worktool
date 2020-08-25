package cn.mxsic.timer;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Function: MyScheduleExecutorService <br>
 *在单机生产环境下建议使用 ScheduledExecutorService 来执行定时任务，它是 JDK 1.5 之后自带的 API，因此使用起来也比较方便，并且使用 ScheduledExecutorService 来执行任务，不会造成任务间的相互影响。

 作者：Java中文社群
 链接：https://juejin.im/post/6864468840836038664
 来源：掘金
 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * @author: siqishangshu <br>
 * @date: 2020-08-25 16:06:00
 */
public class MyScheduleExecutorService {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("进入 Schedule：" + new Date());
            // 模拟异常
            int num = 8 / 0;
            System.out.println("Run Schedule：" + new Date());
        }, 1, 3, TimeUnit.SECONDS); // 1s 后开始执行，每 3s 执行一次
        // 执行任务 2
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("Run Schedule2：" + new Date());
        }, 1, 3, TimeUnit.SECONDS); // 1s 后开始执行，每 3s 执行一次


    }
}
