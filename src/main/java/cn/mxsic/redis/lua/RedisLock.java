package cn.mxsic.redis.lua;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lombok.Data;
/**
 * Function: RedisLock <br>
 *
 * redis实现分布式锁
 * 基于hset lua
 * @author: siqishangshu <br>
 * @date: 2020-10-10 16:21:00
 */
@Component
public class RedisLock {
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    private static final int DEFAULT_TIME_OUT = 10; // 30 s 默认失效时间
    private static final int DEFAULT_RELOCK_TIME = 3; // 10 s  续约周期执行时间
    private static RedisSerializer<String> argsSerializer  = new StringRedisSerializer();
    private static RedisSerializer<String> resultSerializer = new StringRedisSerializer();
    private static RedisTemplate<String, String> redisTemplate;
    private static Redisson redisson;

    /**
     * 解决静态属性使用@Autowired注入
     * @param redisTemplate
     */
    @Autowired
    public RedisLock(RedisTemplate<String, String> redisTemplate, Redisson redisson){
        this.redisTemplate = redisTemplate;
        this.redisson = redisson;
    }

    //获取锁
    public static boolean myTryLock(String lockKey, String lockValue){
        try {
            //获取lua脚本
            DefaultRedisScript lockRedisScript =new DefaultRedisScript<>();
            lockRedisScript.setLocation(new ClassPathResource("script/lock.lua"));
            // 这个值类型要跟lua返回值类型一致才行，否则就会报 java.lang.IllegalStateException
            lockRedisScript.setResultType(Long.class);
            List<String> keys = Collections.singletonList(lockKey);
            Object result = redisTemplate.execute(lockRedisScript, argsSerializer, resultSerializer, keys, lockValue, String.valueOf(DEFAULT_TIME_OUT));
            if("1".equals(String.valueOf(result))){
                //获取锁成功，开启续约
                autoWatchDog(lockKey, lockValue);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //释放锁（通过lua脚本判断只能删除自己的锁）
    public static boolean myUnLock(String lockKey, String lockValue){
        try {
            //获取lua脚本
            DefaultRedisScript unLockRedisScript = new DefaultRedisScript<>();
            unLockRedisScript.setLocation(new ClassPathResource("script/unLock.lua"));
            // 这个值类型要跟lua返回值类型一致才行，否则就会报 java.lang.IllegalStateException
            unLockRedisScript.setResultType(Long.class);
            List<String> keys = Collections.singletonList(lockKey);
            Object result = redisTemplate.execute(unLockRedisScript, argsSerializer, resultSerializer, keys, lockValue);
            if("1".equals(String.valueOf(result))){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void autoWatchDog(String lockKey, String lockValue){
        new Thread(new WatchDog(lockKey, lockValue)).start();
    }


    //实现自动续约，看门狗
    @Data
    static class WatchDog implements Runnable{
        private String lockKey;
        private String lockValue;

        public WatchDog(String lockKey, String lockValue){
            this.lockKey = lockKey;
            this.lockValue = lockValue;
        }

        @Override
        public void run() {
            System.out.println(lockValue + "-"+ new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" +  "-看门狗进程启动-----");
            try {
                Timer timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            public void run() {
                                //获取lua脚本
                                DefaultRedisScript lockRedisScript = new DefaultRedisScript<>();
                                lockRedisScript.setLocation(new ClassPathResource("script/lock.lua"));
                                // 这个值类型要跟lua返回值类型一致才行，否则就会报 java.lang.IllegalStateException
                                lockRedisScript.setResultType(Long.class);
                                List<String> keys = Collections.singletonList(lockKey);
                                Object result = redisTemplate.execute(lockRedisScript, argsSerializer, resultSerializer, keys, lockValue, String.valueOf(DEFAULT_TIME_OUT));
                                if(!"1".equals(String.valueOf(result))){
                                    System.out.println(lockValue + "-"+ new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-"+  "-看门狗进程终止-----");
                                    timer.cancel();
                                }else{
                                    //获取key过期时间
                                    Long expire = redisTemplate.opsForValue().getOperations().getExpire(lockKey);
                                    System.err.println(lockValue + "-"+ new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" +  "-看门狗进程续约成功，剩余时间-----" + expire);
                                }
                            }
                        }, 0, RedisLock.DEFAULT_RELOCK_TIME * 1000);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    //根据key获取value
    public static String myGetValueByKey(String lockKey){
        return redisTemplate.opsForValue().get(lockKey);
    }


    //redisson获取锁
    public static RLock redissonGetLock(String lockKey){
        return redisson.getLock(lockKey);
    }

    //redisson释放锁
    public static void redissonUnLock(RLock rLock){
        rLock.unlock();
    }


    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    public void run() {
                        System.out.println(new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" +11111);
                    }
                }, 0, 3000);
    }

}

