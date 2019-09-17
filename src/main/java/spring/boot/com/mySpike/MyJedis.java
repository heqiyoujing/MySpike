package spring.boot.com.mySpike;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Collections;
import java.util.Objects;

/**
 * @author: yqq
 * @date: 2019/4/17
 * @description: 从2.6.12版本后, 就可以使用set(key,value,NX,PX,expireTime)来获取锁, Lua 脚本来释放锁。
 * https://blog.csdn.net/qq315737546/article/details/79728676（github地址）
 */
public class MyJedis {
    private static final Long UNLOCK_SUCCESS = 1L;
    private static JedisPool jedisPool = null;
    static {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        jedisPool = new JedisPool(config, "192.168.18.23", 6379, 6000, "123456");
    }

    public static boolean setnx(String key, String value, Long expireMillis) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = getJedisObject();
            // nx = not exist, px= 单位是毫秒
            String result = jedis.set(key, value, "NX", "PX", expireMillis);
            if (result != null && result.equalsIgnoreCase("OK")) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            release(jedis);
        }
        return flag;
    }

    public static boolean unlock(String fullKey, String value) {
        Jedis jedis = null;
        boolean flag = false;
        try {
            jedis = getJedisObject();
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(fullKey), Collections.singletonList(value));
            if (Objects.equals(UNLOCK_SUCCESS, result)) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.unwatch();
            release(jedis);
        }
        return flag;
    }

    public static Jedis getJedisObject() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisException e) {
            e.printStackTrace();
            throw e;
        }
        return jedis;
    }

    public static void release(Jedis jedis) {
        jedis.close();
    }
}
