package cn.mysic.proxy.jdkst;

/**
 * Function: Hello <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-02-14 11:19:00
 */
public class Hello implements HelloInterface {
    @Override
    public void sayHello() {
        System.out.println("Hello Cohen");
    }
}
