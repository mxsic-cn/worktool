package cn.mxsic.set;

import java.util.HashSet;
import java.util.Set;

/**
 * Function: ListUtil <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-02-19 11:02:00
 */
public class ListUtil {
    public static void main(String[] args) {
        Set<String> ids = new HashSet<>();
        ids.add(null);
        ids.add(null);
        ids.add(null);
        ids.add(null);
        ids.remove(null);
        ids.add("werwer");
        ids.add("werwer12");
        ids.add("werwer12");
        ids.stream().forEach(System.out::println);
    }
}
