package cn.mxsic.tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Function: Handler <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-25 15:43:00
 */
public class Handler implements Runnable {

    private Socket socket;
    private PrintWriter printWriter;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type: text/html;charset=utf-8");
            printWriter.println();
            Request request = new Request();
            Response response = new Response(printWriter);

            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                String msg = reader.readLine();
                if (msg == null || "".equals(msg.trim())) {
                    break;
                }
                String[] msgs = msg.split(" ");
                if (msgs.length == 3 && msgs[2].equalsIgnoreCase("HTTP/1.1")) {
                    request.setMethod(msgs[0]);
                    request.setPath(msgs[1]);
                    break;
                }
            }
            if (request.getPath().endsWith("ico")) {
                return;
            }
            HttpServlet httpServlet = request.initServlet();
            dispatcher(httpServlet, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                printWriter.close();
                socket.close();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }

    }

    private void dispatcher(HttpServlet httpServlet, Request request, Response response) {
        try {
            if (httpServlet == null) {
                response.write("<h1> 404 not found</h1>");
                return;
            }
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                httpServlet.doGet(request, response);
            } else if ("POST".equalsIgnoreCase(request.getMethod())) {
                httpServlet.doPost(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.write("<h1> 500 server error</h1>");
        }

    }
}
