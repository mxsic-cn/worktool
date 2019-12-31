package cn.mxsic.redis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cn.mxsic.mysql.MysqlTest;
import redis.clients.jedis.Jedis;

/**
 * Function: redis lock failed <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-04-23 10:50:00
 */
public class RedisLock {

    private static final int WAIT_UNLOCK_TIME = 1;
    private static final int WAIT_LOCK_TIME = 1;
    private static RedisPoolManager redisPoolManager = new RedisPoolManager();
    private static String id = "COUNT";

    public static void main(String[] args) {
        Date start = new Date();
        System.out.println("LOCK start ");
        System.out.println("clear lock ");
//        Jedis jedis = redisPoolManager.getJedis();
//        String key = getAccountKey(id);
//        jedis.del(key);
//        jedis.close();
        Statement st = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");//添加一个驱动类
            conn = DriverManager.getConnection("jdbc:mysql://192.168.1.168:13306/flywayDB?useUnicode=true&characterEncoding=utf-8", "root", "Rootroot1!");
            st = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 10; i++) {
            Statement finalSt = st;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 6; j++) {
                        String n = Double.toString(Math.random());
                        try {

                            if (payLock(id, j + "name" + n)) {
                                MysqlTest.queryCount(finalSt);
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
            for (boolean bool = false; !bool; bool = executorService.awaitTermination(3, TimeUnit.SECONDS))
                ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisPoolManager.getJedis().close();
        System.out.println("START " + start + "  LOCK end " + new Date());
    }

    public static boolean payLock(String id, String name) {
        try {
            String key = getAccountKey(id);
            while (true) {
                Jedis jedis = redisPoolManager.getJedis();
                String threadId = String.valueOf(Thread.currentThread().getId());
                if (jedis.exists(key)) {
                    if (threadId.equals(jedis.get(key))) {
                        return true;
                    }
                }
                //String setex(final String key, final int seconds, final String value)
                if (!jedis.exists(key) && jedis.setnx(key ,threadId) == 1) {
                    System.out.println(name + " 加锁 成功");
                    jedis.close();
                    return true;
                }
                System.out.println(name + " 加锁等待 :" + jedis.exists(key));
                jedis.close();
                TimeUnit.SECONDS.sleep(WAIT_LOCK_TIME);
            }
        } catch (Exception e) {
            System.out.println("LOCK ACCOUNT ERROR" + e.getMessage());
            return false;
        }
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
                    if (!jedis.exists(key) ||
                            jedis.del(key) == 1) {
                        System.out.println(name + " 解锁 成功 ");
                        jedis.close();
                        break;
                    }
                }
                System.out.println(name + " 解锁等待 :" + jedis.exists(key));
                jedis.close();
                TimeUnit.SECONDS.sleep(WAIT_UNLOCK_TIME);
            }
        } catch (Exception e) {
            System.out.println("UNLOCK ACCOUNT ERROR" + e.getMessage());
        }
    }

}
