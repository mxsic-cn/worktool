package cn.mysic.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-05-09 11:02:00
 */
public class ThredDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Demo demo = new ThredDemo().new Demo();
        Thread t1;
      t1 =  new Thread(demo);
       t1.setDaemon(true);
       t1.run();

        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return 12349+12329 ;
            }
        });
        t1 = new Thread(futureTask);
//        System.out.println(futureTask.get());
        t1.start();
//        t1.run();
        System.out.println(futureTask.get());

//        while (true){
////           synchronized (demo){
//               if(demo.isFlag()){
//                   System.out.println("##########");
//                   break;
////               }
//               }
//            System.out.println(demo.flag);
//        }
        System.exit(0);
    }


    class Demo implements Runnable {

        private volatile boolean flag = false;

        public Demo() {

        }

        @Override
        public void run() {
            try {
                Thread.sleep(2200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true;
            System.out.println("flag " + isFlag());
        }


        public boolean isFlag() {
            return this.flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

    }
}