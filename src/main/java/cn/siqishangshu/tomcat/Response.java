package cn.siqishangshu.tomcat;

import java.io.PrintWriter;

/**
 * Function: Response <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-25 15:55:00
 */
public class Response {
    private PrintWriter writer;

    public Response(PrintWriter writer) {
        this.writer = writer;
    }

    public void write(String msg) {
        writer.write(msg);
        writer.flush();
    }
}
