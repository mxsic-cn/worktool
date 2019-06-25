package cn.siqishangshu.xml.dom4j;

/**
 * Created by siqishangshu on 17/10/17.
 */
public class StringUtil {
    public static boolean isEmpty(String xmlStr) {
        if(null == xmlStr || xmlStr.equals(""))
            return true;
        return false;
    }
}
