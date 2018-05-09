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
    private static final int WAIT_TIME_UNIT = 3;
    private static RedisPoolManager redisPoolManager = new RedisPoolManager();
    private static String id = "1234";
    private static RLock lock;
    public static void main(String[] args) {
        System.out.println("LOCK start ");
        Statement st = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");//添加一个驱动类
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/flywayDB?useUnicode=true&characterEncoding=utf-8", "root", "Rootroot1!");
            st = conn.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 10; i++) {
            Statement finalSt = st;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        try {
                            if (payLock(id)) {
                                MysqlTest.queryCount(finalSt);
                                payUnLock(id);
                            } else {
                                System.out.println("LOCK ERROR");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            payUnLock(id);
                        }
                    }


                }
            });
        }
        if(!executorService.isShutdown()){
            executorService.shutdown();
        }
        try {
            for (boolean bool = false; !bool; bool = executorService.awaitTermination(3, TimeUnit.SECONDS));
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
            }
        }
        System.out.println("END " + new Date());

    }

    public synchronized static boolean payLock(String id) {
        try {
            Jedis jedis = redisPoolManager.getJedis();
            String key = getAccountKey(id);
            while (true) {
                if (!jedis.exists(key)) {
                    jedis.setnx(key, key);
                    jedis.expire(key, LOCK_TIME);
                    System.out.println("加锁 成功");
                    break;
                }
                TimeUnit.SECONDS.sleep(WAIT_TIME_UNIT);
                System.out.println("等待 :" +new Date());
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

    public synchronized static void payUnLock(String id) {
        Jedis jedis = redisPoolManager.getJedis();
        String key = getAccountKey(id);
        jedis.del(key);
        System.out.println("解锁 成功");
    }

}
