package cn.mxsic.sync;

/**
 * Function: SynchronizedDemo <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-10-24 14:23:00
 */
public class SynchronizedDemo implements Runnable {
    private static int count = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new SynchronizedDemo());
            thread.start();
        }
        try {
            Thread.sleep(500);
            System.out.println(count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("1024");
    }


    @Override
    public void run() {
        synchronized (SynchronizedDemo.class) {
            for (int i = 0; i < 100000; i++) {
                count++;
            }
        }

    }
}