package cn.siqishangshu.rpc.impl;

import cn.siqishangshu.rpc.service.IHelloService;

/**
 * Function: HelloServiceImpl <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-14 14:47:00
 */
public class HelloServiceImpl implements IHelloService{
    @Override
    public String sayHi(String name, String messages) {
        System.out.println("server called");
        return new StringBuffer().append("hi~!").append(name).append(",").append(messages).toString();
    }
}
