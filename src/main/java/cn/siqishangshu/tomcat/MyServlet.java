package cn.siqishangshu.tomcat;

import java.io.IOException;

/**
 * Function: MyServlet <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-25 16:41:00
 */
public class MyServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) throws IOException {
        response.write("<h1> my servlet hello</h1>");
    }
}
