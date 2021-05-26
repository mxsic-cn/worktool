package cn.mxsic.redis.lua;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.mxsic.redis.RedisPoolManager;
import lombok.Data;
import redis.clients.jedis.Jedis;

/**
 * Function: RedisLock <br>
 *
 * redis实现分布式锁
 * 基于hset lua
 *
 * @author: siqishangshu <br>
 * @date: 2020-10-10 16:21:00
 */
//@Component
public class RedisLock {
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    private static final int DEFAULT_TIME_OUT = 10; // 30 s 默认失效时间
    private static final int DEFAULT_RELOCK_TIME = 3; // 10 s  续约周期执行时间
    //    private static RedisSerializer<String> argsSerializer = new StringRedisSerializer();
//    private static RedisSerializer<String> resultSerializer = new StringRedisSerializer();
//        private static RedisTemplate<String, String> redisTemplate;
    private static Jedis jedis;
//    private static Redisson redisson;



    //获取锁
    public static boolean myTryLock(String lockKey, String lockValue) {
        try {
            //获取lua脚本
            InputStream inputStream = new ClassPathResource("script/lock.lua").getInputStream();
            Object[] objects = new Object[]{lockValue, String.valueOf(DEFAULT_TIME_OUT)};
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int readSize;
            while ((readSize = inputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, readSize);
            }

            Object result = jedis.eval(out.toByteArray(), new Integer(1).byteValue(),
                    keysAndArgs(Collections.singletonList(lockKey), objects));
            if ("1".equals(String.valueOf(result))) {
                //获取锁成功，开启续约
                autoWatchDog(lockKey, lockValue);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected static byte[][] keysAndArgs(List<String> keys, Object[] args) {
        final int keySize = keys != null ? keys.size() : 0;
        byte[][] keysAndArgs = new byte[args.length + keySize][];
        int i = 0;
        if (keys != null) {
            for (String key : keys) {
                keysAndArgs[i++] = key.getBytes();
            }
        }
        for (Object arg : args) {
            keysAndArgs[i++] = arg.toString().getBytes();
        }
        return keysAndArgs;
    }

    //释放锁（通过lua脚本判断只能删除自己的锁）
    public static boolean myUnLock(String lockKey, String lockValue) {
        try {
            //获取lua脚本
            InputStream inputStream = new ClassPathResource("script/unLock.lua").getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int readSize;
            while ((readSize = inputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, readSize);
            }
            Object result = jedis.eval(out.toByteArray(), new Integer(1).byteValue(),
                    keysAndArgs(Collections.singletonList(lockKey), new Object[]{lockValue}));
            if ("1".equals(String.valueOf(result))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void autoWatchDog(String lockKey, String lockValue) {
        new Thread(new WatchDog(lockKey, lockValue)).start();
    }


    //实现自动续约，看门狗
    @Data
    static class WatchDog implements Runnable {
        private String lockKey;
        private String lockValue;

        public WatchDog(String lockKey, String lockValue) {
            this.lockKey = lockKey;
            this.lockValue = lockValue;
        }

        @Override
        public void run() {
            System.out.println(lockValue + "-" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" + "-看门狗进程启动-----");
            try {
                Timer timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            public void run() {
                                //获取lua脚本
                                try {

                                    InputStream inputStream = new ClassPathResource("script/keeplock.lua").getInputStream();
                                    Object[] objects = new Object[]{lockValue, String.valueOf(DEFAULT_TIME_OUT)};
                                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                                    byte[] buffer = new byte[2048];
                                    int readSize;
                                    while ((readSize = inputStream.read(buffer)) >= 0) {
                                        out.write(buffer, 0, readSize);
                                    }
                                    Object result = jedis.eval(out.toByteArray(), new Integer(1).byteValue(),
                                            keysAndArgs(Collections.singletonList(lockKey), objects));
                                    if ("1".equals(String.valueOf(result))) {
                                        Long expire = jedis.ttl(lockKey);
                                        System.err.println(lockValue + "-" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" + "-看门狗进程续约成功，剩余时间-----" + expire);
                                    } else {
                                        System.out.println(lockValue + "-" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" + "-看门狗进程终止-----");
                                        timer.cancel();
                                        //获取key过期时间
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 0, RedisLock.DEFAULT_RELOCK_TIME * 1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        RedisLock.jedis = RedisPoolManager.getJedis();
        func("key", Thread.currentThread().getName()+System.nanoTime());
//        jedis.close();
        RedisPoolManager.distory();
    }

    public static boolean func(String lockKey, String lockValue) {
        Boolean flag = true;
        try {
            //获取锁
            flag = RedisLock.myTryLock(lockKey, lockValue);
            if (!flag) {
                System.out.println("lock failed");
                return false;
            }

            System.out.println(lockValue + "-" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" + "-获取锁成功，执行业务");

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(lockValue + "-" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" + "-业务执行完毕");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            boolean unLock = RedisLock.myUnLock(lockKey, lockValue);
            if (unLock) {
                System.out.println(lockValue + "-" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" + "-业务执行完，释放锁");
            }else{
                System.out.println("unlock failed");
            }
        }

        //标识获取锁成功
        return flag;
    }


}

