package cn.mxsic.socket.client;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Function: TODO: ADD FUNCTION <br>
 * Author: siqishangshu <br>
 * Date: 2017-11-01 11:03:00
 */

public class SocketClient {

    private static final Logger logger = Logger.getLogger(SocketClient.class);

    public void client() {
        Socket client = null;
        BufferedReader is = null;
        Writer writer = null;
        try {
            client = new Socket("127.0.0.1", 9999);

            writer = new OutputStreamWriter(client.getOutputStream());
            writer.write("Hello Server.");
            writer.write("eof\n");
            writer.flush();

            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String temp;
            int index;
            while ((temp = is.readLine()) != null) {
                if ((index = temp.indexOf("eof")) != -1) {
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
            }
            // logger.info(sb.toString());
            System.out.println(sb.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                logger.error("Close the IO session error: ", e);
            }
        }

    }

    public static void main(String[] args) {
        SocketClient sc = new SocketClient();
        sc.client();
    }

}