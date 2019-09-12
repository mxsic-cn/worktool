package cn.mxsic.redisson;

import org.redisson.Redisson;
import org.redisson.api.RExecutorService;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Function: Redisson <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-02 16:40:00
 */
public class RedissonUtil {


    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.168:6379");
        // or read config from file
//        config = Config.fromYAML(new File("config-file.yaml"));
        // 2. Create Redisson instance

        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);

        // Reactive API
//        RedissonReactiveClient redissonReactive = Redisson.createReactive(config);

        // RxJava2 API
//        RedissonRxClient redissonRx = Redisson.createRx(config);
        // 3. Get Redis based Map

        RMap<String, Object> map = redisson.getMap("myMap");
        map.put("1", "2");
        map.put("1", "2");
        map.put("1", "2");
        map.put("1", "2");
        map.put("1", "2");
        map.put("1", "2");


//        RMapReactive<MyKey, MyValue> mapReactive = redissonReactive.getMap("myMap");

//        RMapRx<MyKey, MyValue> mapRx = redissonRx.getMap("myMap");
        // 4. Get Redis based Lock
        RLock lock = redisson.getLock("myLock213");
        lock.lock(123, TimeUnit.SECONDS);
        /**
         * "if (redis.call('exists', KEYS[1]) == 0) then " +
         "redis.call('hset', KEYS[1], ARGV[2], 1); " +
         "redis.call('pexpire', KEYS[1], ARGV[1]); " +
         "return nil; " +
         "end; " +
         "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
         "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
         "redis.call('pexpire', KEYS[1], ARGV[1]); " +
         "return nil; " +
         "end; " +
         "return redis.call('pttl', KEYS[1]);",
         */
        lock.lock(123, TimeUnit.SECONDS);
        /**
         * "if (redis.call('exists', KEYS[1]) == 0) then " +
         "redis.call('publish', KEYS[2], ARGV[1]); " +
         "return 1; " +
         "end;" +
         "if (redis.call('hexists', KEYS[1], ARGV[3]) == 0) then " +
         "return nil;" +
         "end; " +
         "local counter = redis.call('hincrby', KEYS[1], ARGV[3], -1); " +
         "if (counter > 0) then " +
         "redis.call('pexpire', KEYS[1], ARGV[2]); " +
         "return 0; " +
         "else " +
         "redis.call('del', KEYS[1]); " +
         "redis.call('publish', KEYS[2], ARGV[1]); " +
         "return 1; "+
         "end; " +
         "return nil;"
         */
        lock.unlock();
        System.out.println(lock.isLocked());
//        RLockReactive lockReactive = redissonReactive.getLock("myLock");

//        RLockRx lockRx = redissonRx.getLock("myLock");
        // 4. Get Redis based ExecutorService
        RExecutorService executor = redisson.getExecutorService("myExecutorService");

        // over 30 different Redis based objects and services ...

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


}
