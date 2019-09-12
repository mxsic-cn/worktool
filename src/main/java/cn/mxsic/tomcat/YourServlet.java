package cn.mxsic.tomcat;

import java.io.IOException;

/**
 * Function: MyServlet <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-25 16:41:00
 */
public class YourServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.write("<h1> your servlet hello</h1>");
    }
}
