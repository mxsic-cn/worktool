package cn.mxsic.cxf;


import java.util.Date;

import javax.jws.WebService;

/**
 * Function: HelloWorldWs <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-26 10:44:00
 */
@WebService(endpointInterface = "cn.siqishangshu.cxf.HelloWorld",serviceName = "HelloWorld")
public class HelloWorldWs implements HelloWorld{
    @Override
    public String syaHi(String name) {
        return name+", OK,The Time is :"+new Date();
    }
}
