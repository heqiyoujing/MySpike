package spring.boot.com.configure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisConnPoolConfig {
    private static Logger __logger = LoggerFactory.getLogger(RedisConnPoolConfig.class.getName());
    private String host;
    private String password;
    private int port;
    private int maxWaitMillis;
    private int maxIdle;
    private int minIdle;
    private int maxTotal;
    private int timeout;
    private int timeBetweenEvictionRunsMillis;
    private int numTestsPerEvictionRun;

    public RedisConnPoolConfig() {
    }

    public static RedisConnPoolConfig getInstance() {
        return new RedisConnPoolConfig();
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxWaitMillis() {
        return this.maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public int getMaxIdle() {
        return this.maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return this.minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxTotal() {
        return this.maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return this.timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getNumTestsPerEvictionRun() {
        return this.numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }
}
