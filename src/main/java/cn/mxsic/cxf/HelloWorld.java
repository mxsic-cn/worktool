package cn.mxsic.cxf;

import javax.jws.WebService;

/**
 * Function: HelloWorld <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-26 10:44:00
 */
@WebService
public interface HelloWorld {
    String syaHi(String name);
}
