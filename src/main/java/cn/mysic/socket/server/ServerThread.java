package cn.mysic.socket.server;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-01 11:29:00
 */


public class ServerThread implements Runnable {

    private static final Logger logger = Logger.getLogger(ServerThread.class);

    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
//       String line;
        BufferedReader is = null;
        Writer writer = null;
        if (socket == null) {
            logger.error("Server down!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        try {
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String temp;
            int index;
            temp = is.readLine();
            System.out.println("line is: "+ temp);
            while (temp != null) {
                System.out.println(temp);
                if ((index = temp.indexOf("eof")) != -1) {// 遇到eof时就结束接收
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
                temp = is.readLine();
            }
            // logger.info(sb.toString());
            System.out.println(sb.toString());
            writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write("Hello Client.");
            writer.write("eof\n");
            writer.flush();
        } catch (IOException e) {
            logger.error("Read information error: ", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                logger.error("Close the IO session error: ", e);
            }

        }
    }

}