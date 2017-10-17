package cn.mysic.pay.citic.entity.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by siqishangshu on 17/10/10.
 *
 *		余额查询

 对应请求代码：DLBALQRY
 特别说明：该交易可查询多个活期账号的余额。一般情况下，账号不建议多于10个。

 *<?xml version="1.0" encoding="GBK"?>
 <stream>
 <action>DLBALQRY</action>
 <userName></userName><!--登录名 varchar(30)-->
 <list name="userDataList">
	 <row>
	 <accountNo></accountNo><!--账号char(19)-->
	 </row>
 </list>
 </stream>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "stream")
@XmlType(propOrder = {
        "action",
        "userName",
        "userDataList",
})
public class BalanceRequest implements Serializable{

    private static final String action = "DLBALQRY";
    private String userName;

//    @Name(name = "accountNo")
    private List userDataList;



}
