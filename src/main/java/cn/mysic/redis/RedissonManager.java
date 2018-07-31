package cn.mysic.redis;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RMap;
import org.redisson.config.Config;
import redis.clients.jedis.Protocol;

/**
 * Function: TODO: ADD FUNCTION <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-07-31 11:28:00
 */
public class RedissonManager {

        private static final String RAtomicName = "genId_";
    private static final String TESTMAP = "TESTMAP";

        private static Redisson redisson = null;

        public static void init(){
            try {
                Config config = new Config();
                config.useSingleServer()
                        .setAddress("redis://"
                                +  Protocol.DEFAULT_HOST
                                + (char) Protocol.COLON_BYTE
                                + Protocol.DEFAULT_PORT)
                        .setDatabase(Protocol.DEFAULT_DATABASE)
                        .setConnectionPoolSize(100)
                        .setConnectTimeout(Protocol.DEFAULT_TIMEOUT);
                redisson = (Redisson) Redisson.create(config);
                System.out.println("----------Redisson配置完成");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public static Redisson getRedisson(){
            if (redisson == null) {
                init();
            }
            return redisson;
        }

        /** 获取redis中的原子ID */
        public static Long nextID(){
            RAtomicLong atomicLong = getRedisson().getAtomicLong(RAtomicName);
            atomicLong.incrementAndGet();
            return atomicLong.get();
        }

    public static void main(String[] args) {
        RMap rMap = getRedisson().getMap(TESTMAP);
        if (rMap == null) {
            System.out.println("map is null");
        }
        rMap.put("1","1");
        rMap.put("2","1");
        rMap.put("3","1");
        rMap.put("4","1");

//        RMap<String,String> rMap1 = getRedisson().getMap(TESTMAP);
//        for (Map.Entry<String, String> entry : rMap1.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }

    }
}
