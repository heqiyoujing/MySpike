package spring.boot.com.mySpike;

import java.util.logging.Logger;

/**
 * @author: yqq
 * @date: 2019/4/17
 * @description:
 */
public class MyLock {
    private static Logger logger = Logger.getLogger("com.xyz.common.util.mySpike.MyLock");
    private static final String LOCK_PREFIX = "LOCK";
    private static final Integer DEFAULT_LOCK_TIME = 20;// 默认锁定时间秒

    /**
     * 获取缓存的value,随机值,使不同的锁value不同 (多服务器可以使用redis时间+客户端标识等)
     * @return
     * @Author: yqq
     * @Date: 2019/4/17
     */
    public static String getLockValue() {
        int random = (int) ((Math.random() * 9 + 1) * 100000);
        long now = System.currentTimeMillis();
        return String.valueOf(now) + String.valueOf(random);
    }

    /**
     * 获取锁
     * @param key
     * @param value
     * @return
     * @Author: yqq
     * @Date: 2019/4/17
     */
    public static boolean lock(String key, String value) {
        String fullKey = getFullKey(key);
        // setnx 并设置超时时间
        boolean success = MyJedis.setnx(fullKey, value, (long) DEFAULT_LOCK_TIME * 1000);
        // 获取成功,直接返回
        if (success) {
            return true;
        }
        // sleep后重新获取锁
        sleep(20);
        return lock(key, value);
    }

    private static void sleep(long sleepMillis) {
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放锁,key对应的value于参数value一致,才删除key
     * @param key
     * @param value
     * @Author: yqq
     * @Date: 2019/4/17
     */
    public static boolean unlock(String key, String value) {
        String fullKey = getFullKey(key);
        boolean success = MyJedis.unlock(fullKey, value);
        if (!success) {
            logger.warning("unlock failed ; key:" + key + ",value:" + value);
        }
        return success;
    }

    /**
     * 获取全量KEY
     * @param key
     * @return
     */
    private static String getFullKey(String key) {
        return LOCK_PREFIX + ":" + key;
    }
}
