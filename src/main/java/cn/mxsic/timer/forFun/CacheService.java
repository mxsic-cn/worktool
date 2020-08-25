package cn.mxsic.timer.forFun;

/**
 * Function: CacheService <br>
 *
 * @author: siqishangshu <br>
 * @date: 2020-08-25 16:40:00
 */
public interface CacheService  {

    /**
     * 从指定的队列里获取任务
     * 实现必须 synchronized
     * @param aClass
     * @return
     */
     QueryTask  take(Class<?> aClass);

    /**
     *
     * 将任务加入队列
     * 实现必须 synchronized
     * @param aClass
     * @param task
     * @return
     */
    boolean put(Class<?> aClass,QueryTask task);

    void remove(Class<?> queryTaskClass);

    /**
     *
     * redis 使用时可参照
     public static long queuePush(String k, Object... v) {
     return cacheInRedis.redisTemplate.opsForList().rightPushAll(k, v);
     }

     public static Object queuePop(String k) {
     return cacheInRedis.redisTemplate.opsForList().leftPop(k);
     }

     public static Long queueSize(String k) {
     return cacheInRedis.redisTemplate.opsForList().size(k);
     }

     public static boolean queueRemove(String k, Object v) {
     return 1L == cacheInRedis.redisTemplate.opsForList().remove(k, 1L, v);
     }

     */
}
