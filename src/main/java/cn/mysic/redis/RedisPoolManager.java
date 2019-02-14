package cn.mysic.redis;

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
    private String host = "127.0.0.1";
    private int port = 6379;

    private JedisPool pool;

    {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
//        config.setMaxTotal(1);
        pool = new JedisPool(config, host);
    }


    public Jedis getJedis() {
        System.out.println("waiters "+pool.getNumWaiters());
        System.out.println("active "+pool.getNumActive());
        System.out.println("idle "+pool.getNumIdle());
        return pool.getResource();
    }

    public static void main(String[] args) {
        RedisPoolManager redisPoolManager = new RedisPoolManager();
        for (int i = 0; i < 10; i++) {
            redisPoolManager.getJedis().lset("list",i,i+"");
        }
    }
}
