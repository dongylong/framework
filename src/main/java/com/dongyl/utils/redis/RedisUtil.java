package com.dongyl.utils.redis;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import javax.annotation.Resource;
import java.io.*;

/**
 * @author dongyl
 * @date 10:56 8/12/18
 * @project framework
 */
public class RedisUtil {
    @Resource(name = "jedisPool")
    protected static Pool<Jedis> jedisPool;

    protected static void selectDbIndex(Jedis jedis, int dbIndex) {
        if (dbIndex > 0) {
            jedis.select(dbIndex);
        }
    }

    protected Jedis selectDbIndex(int dbIndex) {
        Jedis jedis = jedisPool.getResource();
        selectDbIndex(jedis, dbIndex);
        return jedis;
    }

}