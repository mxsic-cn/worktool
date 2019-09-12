package cn.mxsic.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Function: HelloCglib <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-02-14 15:00:00
 */
public class HelloCglib implements MethodInterceptor {
    private Object object;

    public Object getInstance(Object target) {
        this.object = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("Before intercept");
        proxy.invokeSuper(obj,args);
        System.out.println("After intercept");
        return null;
    }
}
