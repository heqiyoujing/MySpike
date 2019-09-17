package spring.boot.com.configure;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnPool {
    private static JedisPool redisPool;
    private RedisConnPoolConfig config;
    private static Logger __log = LoggerFactory.getLogger(RedisConnPool.class.getName());

    public RedisConnPool() {
    }

    public static RedisConnPool getInstance() {
        return new RedisConnPool();
    }

    public void init(RedisConnPoolConfig config) {
        this.setConfig(config);
        JedisPoolConfig jedisConfig = new JedisPoolConfig();
        jedisConfig.setMaxTotal(this.getConfig().getMaxTotal());
        jedisConfig.setMaxIdle(this.getConfig().getMaxIdle());
        jedisConfig.setMaxWaitMillis((long)this.getConfig().getMaxWaitMillis());
        jedisConfig.setTestOnBorrow(false);
        jedisConfig.setMinIdle(this.getConfig().getMinIdle());
        jedisConfig.setTestWhileIdle(true);
        jedisConfig.setTimeBetweenEvictionRunsMillis((long)this.getConfig().getTimeBetweenEvictionRunsMillis());
        jedisConfig.setNumTestsPerEvictionRun(this.getConfig().getNumTestsPerEvictionRun());
        redisPool = new JedisPool(jedisConfig, this.getConfig().getHost(), this.getConfig().getPort(), this.getConfig().getTimeout(), this.getConfig().getPassword());
    }

    public JedisPool getRedisPool() {
        return redisPool;
    }

    public void setConfig(RedisConnPoolConfig config) {
        this.config = config;
    }

    public RedisConnPoolConfig getConfig() {
        return this.config;
    }
}
