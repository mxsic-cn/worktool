package cn.siqishangshu.cxf;

import javax.xml.ws.Endpoint;

/**
 * Function: WsMain <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-26 10:46:00
 */
public class WsMain {
    public static void main(String[] args) {
        HelloWorldWs helloWorldWs = new HelloWorldWs();
        Endpoint.publish("http://127.0.0.1:8081/webservice", helloWorldWs);
        System.out.println("web service ok");
    }
}
