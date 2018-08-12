package com.dongyl.utils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Field;

/**
 * @author dongyl
 * @date 17:07 8/12/18
 * @project framework
 */
public class redisUtilTest {
    static RedisUtil redisUtil;
    static {
        try {
            String ip = "127.0.0.1";
            int port = 6380;
            JedisPoolConfig config = new JedisPoolConfig();
            JedisPool jedisPool = new JedisPool(config,ip,port);
            redisUtil = new RedisUtil();
            Field field = RedisUtil.class.getDeclaredField("jedisPool");
            field.setAccessible(true);
            field.set(redisUtil,jedisPool);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        String result = redisUtil.setValue("key", "value");
        System.out.println("result = "+result);
        String val = redisUtil.getString("key");
        System.out.println("val = "+val);
    }
}
