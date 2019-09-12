package cn.mxsic.xml.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

/**
 * Created by siqishangshu on 17/10/13.
 *
 *  <stream>
 <action>DLBALQRY</action>
 <userName></userName><!--登录名 varchar(30)-->
 <list name="userDataList">
 <row>
 <accountNo></accountNo><!--账号char(19)-->
 </row>
 </list>
 </stream>


 <?xml version="1.0" encoding="GBK"?>
 <stream>
 <key1>value1</key1>
 <key2>value2</key2>
 <list name="userDataList">
 <row>
 <key3>value3</key3>
 …
 </row>
 </list>
 </stream>


 */
@Data
@XStreamAlias("stream")
public class Table {
    @XStreamAlias("action")
    private String action = "DLBALQRY";

    @XStreamAlias("userName")
    private String userName;

    @XStreamImplicit(itemFieldName = "list")
    private List products;
}