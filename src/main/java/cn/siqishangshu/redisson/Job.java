package cn.siqishangshu.redisson;

import org.redisson.api.RedissonClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Function: Job <br>
 * 提高效率，进行并发处理。
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-03 15:36:00
 */
public class Job implements Runnable {
    private int id;
    private RedissonClient redisson;
    private JedisPool pool;

    public Job(int id, RedissonClient redisson) {
        this.id = id;
        this.redisson = redisson;
    }

    public Job(int id, JedisPool pool) {
        this.id = id;
        this.pool = pool;
    }
//    public Job(int id) {
//        this.id = id;
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://192.168.1.168:6379");
//        this.redisson  = Redisson.create(config);
//    }

    @Override
    public void run() {
//        RAtomicLo ng atomicLong = redisson.getAtomicLong("AtomicLong");
        Jedis jedis = pool.getResource();
        Long atomicLong = jedis.incr("AtomicLong");
//        SerPrinter.println(new Date() + "***********" + atomicLong);
        jedis.close();
//        System.out.println(atomicLong.incrementAndGet());
    }
}