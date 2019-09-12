package cn.mxsic.proxy.jdkst;

/**
 * Function: Client <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-02-14 11:21:00
 */
public class Client {
    public static void main(String[] args) {
        HelloProxy helloProxy = new HelloProxy();
        helloProxy.sayHello();
    }
}
