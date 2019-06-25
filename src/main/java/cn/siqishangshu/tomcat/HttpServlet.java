package cn.siqishangshu.tomcat;

import java.io.IOException;

/**
 * Function: HttpServlet <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-25 15:53:00
 */
public abstract class HttpServlet {
    public void doGet(Request request,Response response) throws IOException{
        this.service(request,response);
    }

    public void doPost(Request request,Response response) throws IOException{
        this.service(request,response);
    }

    public void service(Request request, Response response) throws IOException{
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request,response);
        }else {
            doPost(request,response);
        }

    }
}
