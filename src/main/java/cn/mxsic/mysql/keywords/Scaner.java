package cn.mxsic.mysql.keywords;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Function: Scaner <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-06-22 10:50:00
 */
public class Scaner {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/siqishangshu/Desktop/mysql8-keywords.txt");
        if (file.exists()) {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            Map<String, List<String>> map = Maps.newHashMap();

            while ((line = fileReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                if (line.length()==1) {
                    continue;
                }
                String key = line.toCharArray()[0] + "";
                if (!map.containsKey(key)) {
                    map.put(key, Lists.newArrayList());
                }
                if (line.contains(";")) {
                    map.get(key).add(line.split(" ")[0]);
                    continue;
                }
                if (line.contains(" ")) {
                    map.get(key).add(line.split(" ")[0]);
                    continue;
                }
                map.get(key).add(line.split(" ")[0]);
            }
            int size = 0;
            for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
                System.out.println("// "+stringListEntry.getKey());
                size += stringListEntry.getValue().size();
                for (String s : stringListEntry.getValue()) {
//                    System.out.println("public String  "+s+" = \""+s+"\";");
                    System.out.println(" keywords.add(\"" + s + "\");");
                }
                System.out.println("// "+stringListEntry.getKey() + " size :" + stringListEntry.getValue().size());
            }
            System.out.println("total:" + size);
        }
    }
}
