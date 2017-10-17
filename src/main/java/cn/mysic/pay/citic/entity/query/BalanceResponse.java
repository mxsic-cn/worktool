package cn.mysic.pay.citic.entity.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by siqishangshu on 17/10/10.
 *
 *   响应报文
 <?xml version="1.0" encoding="GBK"?>
 <stream>
 <status></status><!--交易状态 char(7)-->
 <statusText></statusText><!--交易状态信息 varchar(254)-->
 <list name="userDataList">
 <row>
 <status></status><!--账户状态 char(7)-->
 <statusText></statusText><!--账户状态信息 varchar(254)-->
 <accountNo></accountNo><!--账号 char(19)-->
 <accountName></accountName><!--账户名称 varchar(122)-->
 <currencyID></currencyID><!--币种 char(2)-->
 <openBankName></openBankName><!--开户行名称 varchar(122)-->
 <lastTranDate></lastTranDate><!--最近交易日 char(8)-->
 <usableBalance></usableBalance><!--可用账户余额 decimal(15,2)-->
 <balance></balance><!-账号余额--decimal(15,2)--->
 <forzenAmt></forzenAmt><!--冻结（或看管）金额decimal(15,2)-->
 <frozenFlag></frozenFlag><!--账号状态char(1)， A：正常；D:睡眠；F:冻结，仅当查询账号为信银国际账号时返回-->
 <accountType></accountType><!--账户类型 char(2)，ST:活期储蓄；IM:活期支票，仅当查询账号为信银国际账号时返回-->
 <lawptLmt></lawptLmt><!--法透额度decimal(15,2)，仅当查询账号为信银国际账号时返回-->
 </row>
 </list>
 </stream>

 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "stream")
@XmlType(propOrder = {
        "computer",
})
public class BalanceResponse implements Serializable{

    private String action = "DLBALQRY";





}
