package com.dongyl.utils;

import com.dongyl.utils.redis.RedisStringUtil;
import org.junit.Test;

/**
 * @author dongyl
 * @date 17:07 8/12/18
 * @project framework
 */
public class RedisHashUtilTest extends RedisBaseTest{
    @Test
    public  void redisSetTest() {
        String result = RedisStringUtil.setValue("key", "value",1000);
        System.out.println("result = "+result);
        String val = RedisStringUtil.getString("key");
        System.out.println("val = "+val);
    }
}
