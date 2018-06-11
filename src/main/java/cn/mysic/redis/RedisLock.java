package cn.mysic.redis;

import cn.mysic.mysql.MysqlTest;
import org.redisson.api.RLock;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Function: redis lock failed <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-04-23 10:50:00
 */
public class RedisLock {

    private static final int LOCK_TIME = 30;
    private static final int WAIT_TIME_UNIT = 1;
    private static RedisPoolManager redisPoolManager = new RedisPoolManager();
    private static String id = "COUNT";
    private static RLock lock;

    public static void main(String[] args) {
        System.out.println("LOCK start ");
        System.out.println("clear lock ");
        Jedis jedis = redisPoolManager.getJedis();
        String key = getAccountKey(id);
        jedis.del(key);
        jedis.close();
        Statement st = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");//添加一个驱动类
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/flywayDB?useUnicode=true&characterEncoding=utf-8", "root", "Rootroot1!dev");
            st = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 8; i++) {
            Statement finalSt = st;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        String n = Double.toString(Math.random());
                        try {

                            if (payLock(id, j + "name" + n)) {
                                MysqlTest.queryCount(finalSt);
//                                payUnLock(id);
                            } else {
                                System.out.println("LOCK ERROR");
                            }
                        } finally {
                            System.out.println("开锁开始");
                            payUnLock(id, j + "name" + n);
                            System.out.println("开锁完成");
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
        System.out.println("LOCK end ");
    }

    private static void checkKey(String id) {
        Jedis jedis = redisPoolManager.getJedis();
        String key = getAccountKey(id);
        while (jedis.exists(key)) {
            System.out.println(new Date());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                jedis.close();
            }
        }
        System.out.println("END " + new Date());

    }

    public static boolean payLock(String id, String name) {
        try {
            String key = getAccountKey(id);
            while (true) {
                Jedis jedis = redisPoolManager.getJedis();
                if (!jedis.exists(key)) {
                    if (jedis.setnx(key, key) == 1) {
                        System.out.println(name + " 加锁 成功");
                        break;
                    }
                }
                /**
                 * 提前还回链接池
                 */
                System.out.println(name + " 加锁等待 :" + jedis.exists(key));
                jedis.close();
                TimeUnit.SECONDS.sleep(WAIT_TIME_UNIT);
            }
        } catch (Exception e) {
            System.out.println("LOCK ACCOUNT ERROR" + e.getMessage());
            return false;
        }
        return true;
    }

    private static String getAccountKey(String id) {
        return "LOCKED_" + id;
    }

    public static void payUnLock(String id, String name) {
        String key = getAccountKey(id);
        try {
            while (true) {
                Jedis jedis = redisPoolManager.getJedis();
                if (jedis.isConnected()) {
                    if (jedis.del(key) == 1) {
                        System.out.println(name + " 解锁 成功 ");
                        break;
                    }
                }
                System.out.println(name + " 解锁等待 :" + jedis.exists(key));
                jedis.close();
                TimeUnit.SECONDS.sleep(WAIT_TIME_UNIT);
            }
        } catch (Exception e) {
            System.out.println("UNLOCK ACCOUNT ERROR" + e.getMessage());
        }
    }

}
