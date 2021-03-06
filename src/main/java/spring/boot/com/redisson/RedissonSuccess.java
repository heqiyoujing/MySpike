package spring.boot.com.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import spring.boot.com.mySpike.MyJedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: yiqq
 * @date: 2019/4/19
 * @description: 通过Redisson实现基于redis的分布式锁，最成功的！！！！
 * https://blog.csdn.net/yuruixin_china/article/details/78531309
 */
public class RedissonSuccess {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Jedis jedis = MyJedis.getJedisObject();
    private static String KEY = "num_all";
    private static int threadCount = 1009;
    private static Logger logger = LoggerFactory.getLogger(RedissonSuccess.class);
    public static void main(String[] args) {
        jedis.set(KEY, "1000");
        //Redisson连接配置文件
        Config config = new Config();
        config. useSingleServer().setAddress("192.168.18.23:6379").setPassword("123456").setDatabase(0);
        RedissonClient redisson = Redisson.create(config);
        RLock yuxinLock = redisson.getLock("yuxinLock"); // 1.获得锁对象实例

        new RedissonSuccess().testM(yuxinLock);
    }

    public void testM(RLock yuxinLock){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadCount);
        for(int i = 0; i < threadCount; i++){
            fixedThreadPool.execute(new Task("woker_" + i,yuxinLock));
        }
    }


    class Task implements Runnable{

        private String name;
        private RLock yuxinLock;

        public Task(String name,RLock yuxinLock){
            this.name = name;
            this.yuxinLock=yuxinLock;
            System.out.println(name);
        }
        public void run() {
            boolean res=false;
            try{
                //1.不支持过期自动解锁，不会超时
                //yuxinLock.lock();

                // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
                //lock.lock(10, TimeUnit.SECONDS);

                // 3. 尝试加锁，最多等待20秒，上锁以后10秒自动解锁（实际项目中推荐这种，以防出现死锁）
                res = yuxinLock.tryLock(20, 10, TimeUnit.SECONDS);
                if(res){
                    int num = Integer.valueOf(jedis.get(KEY));
                    if (num > 0) {
                        jedis.decr(KEY);
                    } else {
                        logger.info(sdf.format(new Date()) + ">>>>>>>>>>已售罄");
                    }
                }else{
                    System.out.println(name+"---------------->>>>>等待超时");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally{
                if(res){
                    yuxinLock.unlock();
                }
            }
        }

    }
}
