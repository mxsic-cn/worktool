package cn.mxsic.mysql.keywords;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Function: DBScaner <br>
 *
 * @author: siqishangshu <br>
 * @date: 2021-06-22 11:28:00
 */
public class DBScaner {
    public static void main(String[] args) {

        String database = "logistics";
//        String database = "footstone";
        Statement st = null;
        Connection conn = null;
        Map<String,List<String>> map = Maps.newHashMap();
        try {
            //添加一个驱动类
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://rm-bp1w99r3e2bua83y08o.mysql.rds.aliyuncs.com:3306/" + database + "?useUnicode=true&characterEncoding=utf-8",
                    "root", "root");
            st = conn.createStatement();
            String sql= "show tables;";
            ResultSet resultSet = st.executeQuery(sql);

            while (resultSet.next()) {
                String table = resultSet.getString("Tables_in_" + database);
//                System.out.println();
//                System.out.println(table);
                String sql1 = "desc "+table+";";
                st = conn.createStatement();
                ResultSet tSet = st.executeQuery(sql1);
                while (tSet.next()) {
                    String column = tSet.getString("Field");
                    if (Mysql_8.keywords.contains(column.toUpperCase())) {
                        if (!map.containsKey(column)) {
                            map.put(column, Lists.newArrayList());
                        }
                        map.get(column).add(table);
                    }
                }
            }
            for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
                System.out.println("Keywords: "+stringListEntry.getKey());
                for (String s : stringListEntry.getValue()) {
                    System.out.println(" "+s);
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
