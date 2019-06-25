package cn.siqishangshu.tomcat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Function: Server <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-25 15:31:00
 */
public class Server {


    private static ServerSocket serverSocket;
    private static int port = 8080;
    private final static int POOL_SIZE = 8;
    private static ExecutorService executorService;

    public static void start(){
        try {
            serverSocket = new ServerSocket(port);
            Socket socket = null;
            System.out.println("start server,port : " + port);
            executorService =  new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE,
                    0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
            while (true){
                socket = serverSocket.accept();
                executorService.execute(new Handler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        start();
    }
}
