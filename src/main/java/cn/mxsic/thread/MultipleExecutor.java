package cn.mxsic.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Function: MultipleExecutor <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-05-21 16:59:00
 */
public class MultipleExecutor {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger integer = new AtomicInteger(0);
//        ExecutorService executor = Executors.newCachedThreadPool();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                try {
                    System.out.println("sleep:" + Thread.currentThread().getName());
                    System.out.println(integer.getAndIncrement());
                    TimeUnit.SECONDS.sleep(Math.round(Math.random() * 10));
                    System.out.println("wake up:" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("task:" + i + " queue:" + executor.getQueue().size() + " worker: " + executor.getPoolSize());
//            CPUUtil.printCpu();
        }
        System.out.println("  queue:" + executor.getQueue().size() + " worker: " + executor.getPoolSize());
        executor.shutdown();
        while (!executor.isTerminated()) {
            System.out.println("waiting");
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(integer.get());
        System.out.println("  queue:" + executor.getQueue().size() + " worker: " + executor.getPoolSize());
    }

}
