package cn.mxsic.timer.forFun;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Function: Runner <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-08-25 15:36:00
 */
@Component
public class QueryRunner implements Runnable {

    private PayServiceService payService;
    private QueryService queryService;
    private CacheService cacheService;
    private boolean isRunning;


    @PostConstruct
    public void QueryRunner() {
        cacheService.remove(QueryTask.class);
        // 从数据库查出需要查的数据放入redis
        //reload task from db to cache
        for (QueryTask queryTask : payService.queryTask()) {
            cacheService.put(QueryTask.class, queryTask);
        }

    }

    @PreDestroy
    public void stopSignal() {
        this.isRunning = false;
        //clean the queue in cache
        cacheService.remove(QueryTask.class);
    }


    public void query(QueryTask task) {
        System.out.println("do query api with parameter in query Id");
        //启动时直接进行查询
        Object result = queryService.queryStatus(task.getQueryId());
        if (result.equals(Boolean.TRUE)) {
            //查到结果，可能成功，可能失败，不再查询
            payService.queryResult(task.getQueryId(), result);
        } else {
            //过一会再查
            queryService.queryNextTime(task);
        }
    }

    @Override
    public void run() {
        System.out.println(("Enter thread Query Task, thread id " + Thread.currentThread().getId()));
        this.isRunning = true;
        while (true) {
            if (this.isRunning) {
                try {
                    QueryTask task = cacheService.take(QueryTask.class);
                    if (task != null) {
                        if (task.shouldWait()) {
                            TimeUnit.MILLISECONDS.sleep(task.waitTime());
                        }
                        System.out.println("do query api with parameter in query Id");
                        //启动时直接进行查询
                        Object result = queryService.queryStatus(task.getQueryId());
                        //查询有结果了，
                        if (Boolean.TRUE) {
                            //查到结果，可能成功，可能失败，不再查询
                            payService.queryResult(task.getQueryId(), result);
                        } else {
                            //没结果过一会再查
                            queryService.queryNextTime(task);
                        }
                    }
                } catch (Throwable e) {
                    System.out.println(("error occurred when writing ApiCost log to db, try it later" + e.getLocalizedMessage()));
                }
            }
        }
    }
}


