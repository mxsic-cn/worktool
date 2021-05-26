package cn.mxsic.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-04-23 10:55:00
 */
public class RedisPoolManager {
    private static final String TEST_KEY = "test_key";
    private static String host = "redis";
    private static int port = 6379;

    private static JedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(2);
        pool = new JedisPool(config, host, port);
    }


    public static Jedis getJedis() {
        System.out.println("waiters " + pool.getNumWaiters() + " active " + pool.getNumActive() + " idle " + pool.getNumIdle());
        return pool.getResource();
    }

    public static void distory() {
        RedisPoolManager.pool.close();
    }

    public static void main(String[] args) {
        Jedis jedis =  RedisPoolManager.getJedis();
        for (int i = 0; i < 10; i++) {
            jedis.hset(TEST_KEY, i + "", "value"+i);
        }
        Set<String> keys = jedis.hkeys(TEST_KEY);
        for (String key : keys) {
            System.out.println(jedis.hget(TEST_KEY, key));
        }
//        jedis.close();
        RedisPoolManager.getJedis();
        RedisPoolManager.pool.close();
    }
}
