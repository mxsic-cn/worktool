package cn.mxsic.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;

/**
 * Function: Client <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-02-14 11:21:00
 */
public class Client {
    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/siqishangshu/temp");
        HelloCglib helloCglib = new HelloCglib();
        Hello helloInterface = (Hello) helloCglib.getInstance(new Hello());
        helloInterface.sayHello();

    }
}
