package cn.mxsic.charset;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-01-02 10:34:00
 */
public class Chars {
    public static void main(String[] args) {
        char a = '.';
        System.out.println(a);
        int len = 10;
        while (len --> 0) {
            System.out.println(len);
        }
        System.out.println(10 & '\uffff');
        System.out.println('\uffff');

        System.out.println(10 & 0xffff);
//        char a = 'A';
//        String as ="A";
//        try {
//            while (true){
//                System.out.println(as);
//                TimeUnit.SECONDS.sleep(10);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
