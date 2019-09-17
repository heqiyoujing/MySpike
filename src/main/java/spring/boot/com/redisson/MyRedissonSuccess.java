package spring.boot.com.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import redis.clients.jedis.Jedis;
import spring.boot.com.mySpike.MyJedis;
import spring.boot.com.mySpike.MyLock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: yqq
 * @date: 2019/4/17
 * @description: 秒杀功能
 * https://blog.csdn.net/qq315737546/article/details/79728676(基于redis setnx的简易分布式锁(修正版))
 * https://zhuanlan.zhihu.com/p/59256821(Redlock：Redis分布式锁最牛逼的实现)
 */
public class MyRedissonSuccess {
    /**
     1.set命令要用set key value px milliseconds nx；
     2.value要具有唯一性；
     3.释放锁时要验证value值，不能误解锁；
     */

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final UUID id = UUID.randomUUID();
    private static RLock  redLock;
    static {
//        Config config = new Config();
//        config.useSentinelServers().addSentinelAddress("127.0.0.1:6379","127.0.0.1:6380")
//                .setMasterName("masterName")
//                .setPassword("123456").setDatabase(0);
//        redissonClient = Redisson.create(config);
    }

    private static Jedis jedis = MyJedis.getJedisObject();
    private static String KEY = "num_all_3";
    private static int threadCount = 1009;
    public static void main(String[] args) {
        jedis.set(KEY, "1000");
        Config config = new Config();
        config.useSingleServer().setAddress("192.168.18.23:6379").setPassword("123456").setDatabase(0);
       RedissonClient redisson = Redisson.create(config);
        redLock = redisson.getLock("REDLOCK_KEY");
        TestM m = new TestM();
        ExecutorService exec = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            exec.submit(new TestM());//submit有返回值，而execute没有
        }
    }

    static class TestM extends Thread{
        @Override
        public void run() {
            boolean isLock;
            try {
                isLock = redLock.tryLock(20, 10, TimeUnit.SECONDS);
                // 500ms拿不到锁, 就认为获取锁失败。10000ms即10s是锁失效时间。
//                isLock = redLock.tryLock(500, 10000, TimeUnit.MILLISECONDS);
                if (isLock) {
                    int num = Integer.valueOf(jedis.get(KEY));
                    if (num > 0) {
                        jedis.decr(KEY);
                    } else {
                        System.out.println(sdf.format(new Date()) + "已售罄");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 无论如何, 最后都要解锁
                redLock.unlock();
            }
        }
    }


}
