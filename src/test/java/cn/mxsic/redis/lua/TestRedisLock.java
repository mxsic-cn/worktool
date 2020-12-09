package cn.mxsic.redis.lua;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;



/**
 * 测试redis分布式锁
 * Function: TestRedisLock <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-10-10 16:24:00
 */
@RunWith(SpringRunner.class)
public class TestRedisLock {
    final static String lockKey = "jiaobaba";

    public TestRedisLock() {
        //在构造函数上写上这个
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }

    @Test
    public void test() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            String lockValue = UUID.randomUUID().toString();
            new Thread(()->{
                while (!func(lockValue)){};
//                func1();

                downLatch.countDown();
            }).start();

        }

        downLatch.await();

        System.out.println("全部任务执行完毕");
    }

    /**
     * 自己实现分布式锁测试
     * @param lockValue
     * @return
     */
    public boolean func(String lockValue){
        Boolean flag = true;
        try{
            //获取锁
            flag = RedisLock.myTryLock(lockKey, lockValue);
            if(!flag){
                return false;
            }

            System.out.println(lockValue + "-"+ new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" + "-获取锁成功，执行业务");

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(lockValue + "-"+ new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" +  "-业务执行完毕");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放锁
            boolean unLock = RedisLock.myUnLock(lockKey, lockValue);
            if(unLock){
                System.out.println(lockValue + "-"+ new SimpleDateFormat("hh:mm:ss").format(new Date()) + "-" +  "-业务执行完，释放锁");
            }
        }

        //标识获取锁成功
        return flag;
    }

    /**
     * redisson 分布式锁测试
     */
    public void func1(){
        RLock rLock = RedisLock.redissonGetLock(lockKey);
        try{
            //加锁
            rLock.lock();
            System.out.println(Thread.currentThread().getName() + "获取锁成功，执行业务");
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放锁
            RedisLock.redissonUnLock(rLock);
            System.out.println(Thread.currentThread().getName() + "业务执行完，释放锁");
        }
    }
}

