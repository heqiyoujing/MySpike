package spring.boot.com.mySpike;

import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: yqq
 * @date: 2019/4/17
 * @description: 秒杀功能
 * https://blog.csdn.net/qq315737546/article/details/79728676
 * https://zhuanlan.zhihu.com/p/59256821
 */
public class MyTest {
    /**
     1.set命令要用set key value px milliseconds nx；
     2.value要具有唯一性；
     3.释放锁时要验证value值，不能误解锁；
     */

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Jedis jedis = MyJedis.getJedisObject();
    private static String KEY = "num_all_0";
    private static int threadCount = 1009;
    public static void main(String[] args) {
        jedis.set(KEY, "1000");
        /*jedis.decrBy(KEY, 10);
        jedis.incrBy(KEY, 10);*/
        String string = jedis.get("AAAAAAAAAAAA");
        System.out.println(string);
        TestM m = new TestM();
        ExecutorService exec = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            exec.submit(m);
        }
    }

    static class TestM extends Thread{
        @Override
        public void run() {
            String key = "testLock";
            String value = MyLock.getLockValue();
            MyLock.lock(key, value);
            try {
                int num = Integer.valueOf(jedis.get(KEY));
                if(num >0){
                    jedis.decr(KEY);
                }else {
                    System.out.println(sdf.format(new Date()) + "已售完");
                }
            } finally {
                MyLock.unlock(key, value);
            }
        }
    }
}
