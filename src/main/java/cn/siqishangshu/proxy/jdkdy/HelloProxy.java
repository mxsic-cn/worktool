package cn.siqishangshu.proxy.jdkdy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Function: Hello <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-02-14 11:22:00
 */
public class HelloProxy implements InvocationHandler{

    private static int  cout = 0;
    private Object object;

    public HelloProxy(Object object){
        this.object = object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(cout++);
        System.out.println("Before invoke" + method.getName());
        method.invoke(object,args);
        System.out.println("After invoke" + method.getName());
        return null;
    }
}
