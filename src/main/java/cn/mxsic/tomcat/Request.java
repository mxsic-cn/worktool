package cn.mxsic.tomcat;

import java.util.Map;

import lombok.Data;

/**
 * Function: Request <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-25 15:50:00
 */
@Data
public class Request {
    private String path;
    private String method;
    private String parameter;
    private Map<String,String> attribute;

    public HttpServlet initServlet(){
        return ServletContainer.getHttpServlet(path);
    }
}
