package cn.mysic.print;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuchuan on 3/13/17.
 */
public class Print {

    public static void main(String[] args) throws Exception{
        byte[] bytes = new byte[]{2,5,2,1,5,4,6,2,4};
        for (byte b : bytes) {
            System.out.print(b+" ");
        }
        System.out.println(" ");
        Date encrypt = new Date();
        TimeUnit.MILLISECONDS.sleep(1);
        Date decrypt = new Date();

        System.out.println("time :"+(decrypt.getTime() - encrypt.getTime()));
    }
}
