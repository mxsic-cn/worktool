package cn.siqishangshu.netty;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Function: Server <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-18 11:09:00
 */
public class PlainOioServer {

    public void server(int port) throws IOException{
        final ServerSocket socket = new ServerSocket(port);
        try{
            while (true){
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try{
                            out = clientSocket.getOutputStream();
                            out.write("hi!\r\n".getBytes(Charset.defaultCharset()));
                            out.flush();
                            clientSocket.close();
                        }catch (IOException e){
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
