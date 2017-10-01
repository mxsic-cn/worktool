package cn.mysic.thread;

/**
 * Created by liuchuan on 2/9/17.
 */
public class RunIs implements Runnable {
    @Override
    public void run() {
        for (int i = 0 ;i < 100 ; i++){
            System.out.println("RunIS");
        }
    }
}
