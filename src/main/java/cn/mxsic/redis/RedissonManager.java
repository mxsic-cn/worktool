package cn.mxsic.redis;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RMap;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Protocol;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-07-31 11:28:00
 */
public class RedissonManager {

    private static final String RAtomicName = "genId_";
    private static final String TESTMAP = "userMap";

    private static Redisson redisson = null;

    public static void init() {
        try {
            Config config = new Config();
            config.useSingleServer()
                    .setAddress("redis://"
                            + Protocol.DEFAULT_HOST
                            + (char) Protocol.COLON_BYTE
                            + Protocol.DEFAULT_PORT)
                    .setDatabase(15)
                    .setConnectionPoolSize(100)
                    .setConnectTimeout(Protocol.DEFAULT_TIMEOUT);
            redisson = (Redisson) Redisson.create(config);
            System.out.println("----------Redisson配置完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Redisson getRedisson() {
        if (redisson == null) {
            init();
        }
        return redisson;
    }

    /**
     * 获取redis中的原子ID
     */
    public static Long nextID() {
        RAtomicLong atomicLong = getRedisson().getAtomicLong(RAtomicName);
        atomicLong.incrementAndGet();
        return atomicLong.get();
    }

    public static void main(String[] args) {
        RMap rMap = getRedisson().getMap(TESTMAP);
        if (rMap == null) {
            System.out.println("map is null");
        }
        for (int i = 2691882; i < 10000000; i++) {
            getRedisson().getLock("lock:test_" + i).lock(100, TimeUnit.HOURS);
//            rMap.put("lock:test_" + i,i);
        }

//        RMap<String,String> rMap1 = getRedisson().getMap(TESTMAP);
//        for (Map.Entry<String, String> entry : rMap1.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }

    }
}
