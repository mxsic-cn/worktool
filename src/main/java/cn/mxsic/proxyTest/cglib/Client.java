package cn.mxsic.proxyTest.cglib;

/**
 * Function: Client <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-02-14 11:21:00
 */
public class Client {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        HelloCglib helloCglib = new HelloCglib();
        Hello helloInterface = (Hello) helloCglib.getInstance(new Hello());
        helloInterface.sayHello();

    }
}
