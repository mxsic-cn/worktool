package cn.mxsic.rpc.service;

import java.io.IOException;

/**
 * Function: Server <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-14 14:52:00
 */
public interface Server {
    int PORT = 8080;

    void start() throws IOException;

    void stop();

    void regist(Class<? extends IRpcService> serviceInterface, Class<? extends IRpcService> impl);

}
