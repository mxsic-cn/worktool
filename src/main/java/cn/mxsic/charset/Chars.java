package cn.mxsic.charset;

import java.util.concurrent.TimeUnit;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-01-02 10:34:00
 */
public class Chars {
    public static void main(String[] args) {
        char a = 'A';
        String as ="A";
        try {
            while (true){
                System.out.println(as);
                TimeUnit.SECONDS.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
