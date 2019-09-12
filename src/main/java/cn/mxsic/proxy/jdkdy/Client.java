package cn.mxsic.proxy.jdkdy;

import java.lang.reflect.InvocationHandler;

import cn.mxsic.proxy.jdkst.Hello;
import cn.mxsic.proxy.jdkst.HelloInterface;

/**
 * Function: Client <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-02-14 11:21:00
 */
public class Client {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Hello hello = new Hello();
        InvocationHandler handler = new HelloProxy(hello);
//        HelloInterface helloInterface = (HelloInterface)
//                Proxy.newProxyInstance(handler.getClass().getClassLoader(),
//                hello.getClass().getInterfaces(),
//                handler);
        HelloInterface helloInterface = new Proxy0(handler);
        helloInterface.sayHello();


    }
}
