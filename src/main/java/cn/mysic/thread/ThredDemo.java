package cn.mysic.thread;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-05-09 11:02:00
 */
public class ThredDemo {

    public static void main(String[] args) {
        Demo demo = new ThredDemo().new Demo();
        new Thread(demo).start();
        while (true){
//           synchronized (demo){
               if(demo.isFlag()){
                   System.out.println("##########");
                   break;
//               }
               }
//            System.out.println(demo.flag);
        }
    }


    class Demo implements Runnable {

        private volatile boolean flag = false;

        public Demo() {

        }

        @Override
        public void run() {
            try {
                Thread.sleep(200);
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