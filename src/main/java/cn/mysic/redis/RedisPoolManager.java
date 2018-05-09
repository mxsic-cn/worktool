package cn.mysic.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
        pool = new JedisPool(host, port);
    }


    public Jedis getJedis() {
        return pool.getResource();
    }
}
