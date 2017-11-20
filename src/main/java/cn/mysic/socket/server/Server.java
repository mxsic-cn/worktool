package cn.mysic.socket.server;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-01 11:28:00
 */

public class Server{

    private static final Logger logger = Logger.getLogger(Server.class);

    public static ServerSocket server = null;

    static{
        try {
            server = new ServerSocket(5678);
        } catch (IOException e) {
            logger.error("Server set up error: ", e);
        }
    }

    public static void stop(){
        if(server != null){
            try {
                server.close();
            } catch (IOException e) {
                logger.error("Server close error: ", e);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Start server");
        //Server.stop();
    }

}