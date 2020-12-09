package cn.mxsic.ognl;

import org.apache.ibatis.scripting.xmltags.OgnlCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Function: OgnlTest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-12-09 10:01:00
 */
public class OgnlTest {
    public static void main(String[] args) {

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("state", "0");
        Object value = OgnlCache.getValue("state != null and state != ''", objectMap);
        System.out.println(value);

    }

}
