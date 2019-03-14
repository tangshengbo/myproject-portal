package com.tangshengbo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

/**
 * redis集群版客户端
 *
 * @version 1.0
 */
public class JedisClientCluster implements JedisClient {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public String get(String key) {
        String string = jedisCluster.get(key);
        return string;
    }

    @Override
    public String set(String key, String value) {
        String string = jedisCluster.set(key, value);
        return string;
    }

    @Override
    public String set(String key, String value, int cacheSeconds) {
        String string = jedisCluster.set(key, value);
        if (cacheSeconds != 0) {
            jedisCluster.expire(key, cacheSeconds);
        }
        return string;
    }

    @Override
    public long incr(String key) {
        Long result = jedisCluster.incr(key);
        return result;
    }

    @Override
    public Long hset(String hkey, String key, String value) {
        Long result = jedisCluster.hset(hkey, key, value);
        return result;
    }

    @Override
    public String hget(String hkey, String key) {
        String string = jedisCluster.hget(hkey, key);
        return string;
    }

    @Override
    public Long del(String key) {
        Long result = jedisCluster.del(key);
        return result;
    }

    @Override
    public Long hdel(String hkey, String key) {
        Long result = jedisCluster.hdel(hkey, key);
        return result;
    }

    @Override
    public Long expire(String key, int second) {
        Long result = jedisCluster.expire(key, second);
        return result;
    }

}
