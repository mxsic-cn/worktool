package cn.siqishangshu.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Function: MaxDataInRedisTest <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-03 15:33:00
 */
public class MaxDataInRedisTest {

    private static int CORE_POOL_SIZE = 10;
    private static int MAXIMUM_POOL_SIZE = Integer.MAX_VALUE;
    private static long KEEP_ALIVE_TIME = 30L;

    public static void main(String[] args) {
        Config config = new Config ();
        config.useSingleServer().setAddress("redis://192.168.1.168:6379");
        RedissonClient redisSon = Redisson.create(config);
        ExecutorService executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 10000; i++) {
//            for (int j = 0; j < 10; j++) {
            Job job = new Job(i,redisSon);executor.execute(job);
//            Job job = new Job(i);
            executor.execute(job);
//            }
        }
    }

}
