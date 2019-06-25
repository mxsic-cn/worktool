package cn.siqishangshu.mysql;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-04-19 11:15:00
 * <p>
 * <p>
 * use flywayDB;
 * CREATE TABLE IF NOT EXISTS `account` (
 * `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
 * `count` INT(25) NOT NULL COMMENT '计数',
 * `lock` TINYINT  DEFAULT 0 COMMENT '锁',
 * PRIMARY KEY (`id`))
 * ENGINE = InnoDB
 * DEFAULT CHARACTER SET = utf8
 * COMMENT = '表';
 */
public class MysqlTest {


    public static void main(String[] args) throws Exception {


        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Statement st = null;
                    Connection conn = null;
                    try {
                        Class.forName("com.mysql.jdbc.Driver");//添加一个驱动类
                        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/flywayDB?useUnicode=true&characterEncoding=utf-8", "root", "Rootroot1!");
                        st = conn.createStatement();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < 50; j++) {

                        try {
                            if (payLock(st)) {
                                queryCount(st);
                            } else {
                                System.out.println("LOCK ERROR");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (!payUnLock(st)) {
                                System.out.println("UNLOCK ERROR");
                            }

                        }
                    }


                }
            });
        }
        if (!executorService.isShutdown()) {
            executorService.shutdown();
        }
        try {
            for (boolean bool = false; !bool; bool = executorService.awaitTermination(3, TimeUnit.SECONDS)) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void queryCount(Statement st) {
        try {
            String sql = "select count from account where id ='123' ";
            ResultSet rs = st.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                if (null != rs) {
                    count = rs.getInt("count");
                    System.out.println(count);
                }
            }
            updateCount(st, count);
        } catch (Exception e) {
            System.out.println("SQL ERROR");
        }
    }

    private static void updateCount(Statement st, int count) throws SQLException {
        String sql2 = "UPDATE account SET count = " + (count + 1) + " WHERE id = '123' ";
        int re = st.executeUpdate(sql2);
//        System.out.println(re>0?"count 更新成功"+count + 1:re);
    }

    public static boolean payLock(Statement st) {
        String sql1 = "UPDATE account SET lock = 1 WHERE id = '123' and lock =0  ";
        try {
            while (true) {
                if (isUnlock(st)) {
                    if (st.executeUpdate(sql1) > 0) {
                        System.out.println("LOCK 成功");
                        return true;
                    }
                }
                int time = 1;
                System.out.println("LOCK 等待" + time + "秒");
                TimeUnit.SECONDS.sleep(time);
            }
        } catch (Exception e) {
            System.out.println(("LOCK ACCOUNT ERROR" + e.getMessage()));
            return false;
        }
    }

    public static boolean payUnLock(Statement st) {
        String sql3 = "UPDATE account SET lock = 0";//  WHERE id = '123' and (reserve ='LOCKED'  OR reserve is null)";
        try {
            while (true) {
                if (isLocked(st)) {
                    if (st.executeUpdate(sql3) > 0) {
                        System.out.println("UNLOCK 成功");
                        return true;
                    }
                } else {
                    return true;
                }
                int time = 1;
                System.out.println("UNLOCK 等待" + time + "秒");
                TimeUnit.SECONDS.sleep(time);
            }
        } catch (Exception e) {
            System.out.println(("UNLOCK ACCOUNT ERROR" + e.getMessage()));
            return false;
        }
    }

    private static boolean isUnlock(Statement st) throws SQLException {
        String sql2 = "select count(*) as count from account WHERE id = '123'  and lock =0  ";
        ResultSet rs = st.executeQuery(sql2);
        while (rs.next()) {
            return rs.getInt("count") > 0;
        }
        return false;
    }

    private static boolean isLocked(Statement st) throws SQLException {
        String sql2 = "select count(*) as count from account  WHERE id = '123' and lock =1 ";
        ResultSet rs = st.executeQuery(sql2);
        while (rs.next()) {
            return rs.getInt("count") > 0;
        }
        return false;
    }

}
