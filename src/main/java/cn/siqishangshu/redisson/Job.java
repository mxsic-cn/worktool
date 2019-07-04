package cn.siqishangshu.redisson;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.UUID;

/**
 * Function: Job <br>
 * 提高效率，进行并发处理。
 * @author: siqishangshu <br>
 * @date: 2019-07-03 15:36:00
 */
public class Job implements Runnable {
    private int id;
    private RedissonClient redisson;

    public Job(int id,RedissonClient redisson) {
        this.id = id;
        this.redisson = redisson;
    }
    public Job(int id) {
        this.id = id;
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.1.168:6379");
        this.redisson  = Redisson.create(config);
    }

    @Override
    public void run() {
        RMap<String, Object> newMap = redisson.getMap("001_" + id);
        System.out.println("********************* 001_" + id);
        newMap.put(UUID.randomUUID().toString(), UUID.randomUUID());
    }
}