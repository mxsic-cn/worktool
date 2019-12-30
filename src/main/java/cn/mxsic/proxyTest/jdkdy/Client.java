package cn.mxsic.proxyTest.jdkdy;


import java.lang.reflect.InvocationHandler;

import cn.mxsic.proxy.jdk.MProxy;
import cn.mxsic.proxyTest.Hello;
import cn.mxsic.proxyTest.HelloInterface;


/**
 * Function: Client <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-02-14 11:21:00
 */
public class Client {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.getProperties().put("cn.mxsic.ProxyGenerator.saveGeneratedFiles", "true");
        Hello hello = new Hello();
        InvocationHandler handler = new HelloProxy(hello);
        HelloInterface helloInterface = (HelloInterface)
                MProxy.newProxyInstance(handler.getClass().getClassLoader(),
                        hello.getClass().getInterfaces(),
                        handler);
//        HelloInterface helloInterface = new Proxy0(handler);
        helloInterface.sayHello();
    }
}
