package cn.siqishangshu.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Function: synchronized <br>
 *
 * //实例方法，锁住的是该类的实例对象
 * public synchronized void method(){}
 * //静态方法，锁住的是类对象
 * public static synchronized void method(){}
 * //同步代码块，锁住的是该类的实例对象
 * synchronized(this){}
 * //同步代码块，锁住的是该类的类对象
 * synchronized (XXX.class){}
 * //同步代码块，锁住的是配置实例对象
 * //String 对象作为锁
 * String lock ="";
 * synchronized(lock){}
 * * @author: siqishangshu <br>
 * @date: 2018-05-08 11:25:00
 */
public class SynchronizedDemo implements Runnable {

    private volatile static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new SynchronizedDemo());
            thread.start();
        }
        List list = new ArrayList();
        TimeUnit.SECONDS.sleep(4);
        System.out.println(count);
        while (true){
            list.add(new SynchronizedDemo());
        }
    }


    @Override
    public void run() {
        synchronized (this.getClass()) {
            for (int i = 0; i < 100000; i++) {
//            count();
                count++;
            }

        }
    }

    private  synchronized void count() {
//        count++;
        count = count + 1;
    }

}
