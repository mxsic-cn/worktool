package cn.mxsic.rpc.service;

/**
 * Function: IHelloService <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-14 10:23:00
 */
public interface IHelloService extends IRpcService {
    String sayHi(String name,String messages);
}
