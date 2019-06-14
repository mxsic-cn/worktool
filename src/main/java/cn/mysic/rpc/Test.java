package cn.mysic.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import cn.mysic.rpc.impl.Client;
import cn.mysic.rpc.service.IHelloService;

/**
 * Function: Test <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-14 15:33:00
 */
public class Test {

    public static void main(String[] args) throws IOException {
        IHelloService service = Client.getRemoteProxyObj(IHelloService.class, new InetSocketAddress(8080));
        service.sayHi("sb", "新年快乐!");
    }
}
