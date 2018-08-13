package com.dongyl.utils;

import com.dongyl.utils.redis.RedisStringUtil;
import com.dongyl.utils.redis.RedisUtil;
import org.junit.Test;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Field;

/**
 * @author dongyl
 * @date 17:07 8/12/18
 * @project framework
 */
public class RedisStringUtilTest {

    @Test
    public  void getStringTest() {
        String val = RedisStringUtil.getString("key");
        System.out.println("val = "+val);
    }
    @Test
    public  void setStringTest() {
        String val = RedisStringUtil.getString("key");
        System.out.println("val = "+val);
    }
}
