package cn.siqishangshu.xml.mysic;

import lombok.Data;

/**
 * Created by siqishangshu on 17/10/13.
 *
 *  <stream>
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
public class Body {

    private Person row ;

    private String key1;
    private String key2;

}