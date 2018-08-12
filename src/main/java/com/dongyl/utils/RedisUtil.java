package com.dongyl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;

/**
 * @author dongyl
 * @date 10:56 8/12/18
 * @project framework
 */
public class RedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Resource(name = "jedisPool")
    protected Pool<Jedis> jedisPool;

    public String getString(String key) {
        return getString(0, key);
    }

    public String getString(int dbIndex, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (dbIndex > 0) {
                jedis.select(dbIndex);
            }
            String value = jedis.get(key);
            return value;
        } catch (Exception e) {
            throw e;
        }
    }
    public String setValue(String key,String value) {
        return setValue(0,key,value);
    }
    public String setValue(int dbIndex, String key,String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            if (dbIndex > 0) {
                jedis.select(dbIndex);
            }
            String result = jedis.set(key, value);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}
