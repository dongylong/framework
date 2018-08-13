package com.dongyl.utils;

import com.dongyl.utils.redis.RedisStringUtil;
import org.junit.Test;

/**
 * @author dongyl
 * @date 17:07 8/12/18
 * @project framework
 */
public class RedisStringUtilTest extends RedisBaseTest{

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
