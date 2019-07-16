package cn.siqishangshu.redisson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.siqishangshu.performance.memory.MemoryUtil;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Function: MaxDataInRedisTest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-03 15:33:00
 */
public class MaxDataInRedisTest {

    private static int CORE_POOL_SIZE = 0;
    private static int MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;
    private static long KEEP_ALIVE_TIME = 3L;

    public static void main(String[] args) {
//        Config config1 = new Config();
//        config1.useSingleServer().setAddress("redis://192.168.1.168:6379");
//        config1.useSingleServer().setConnectionPoolSize(100);
//        RedissonClient redisSon = Redisson.create(config1);
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(100);
        JedisPool pool = new JedisPool(config, "192.168.1.168");

        try {
            ExecutorService executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>());
            for (int i = 0; i < 10000; i++) {
                System.out.println("job:" + i);
                MemoryUtil.freeMemory();
                MemoryUtil.maxMemory();
                MemoryUtil.totalMemory();
                Job job = new Job(i, pool);
                executor.execute(job);
            }
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }

}
