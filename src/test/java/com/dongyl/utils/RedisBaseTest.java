package com.dongyl.utils;

import com.dongyl.utils.redis.RedisUtil;
import org.junit.Before;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Field;

/**
 * @author dongyl
 * @date 17:07 8/12/18
 * @project framework
 */
public class RedisBaseTest {
    static RedisUtil redisUtil;
    @Before
    public void connRedis(){
        try {
            String ip = "127.0.0.1";
            int port = 6380;
            JedisPoolConfig config = new JedisPoolConfig();
            JedisPool jedisPool = new JedisPool(config,ip,port);
            Field field = RedisUtil.class.getDeclaredField("jedisPool");
            field.setAccessible(true);
            field.set(redisUtil,jedisPool);
            System.out.println("redis started");

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
