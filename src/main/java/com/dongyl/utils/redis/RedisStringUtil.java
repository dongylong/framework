package com.dongyl.utils.redis;

import com.dongyl.utils.TypeConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.lang.invoke.MethodHandles;
import java.util.*;

/**
 * Redis 字符串(String)
 *
 * @author dongyl
 * @date 10:56 8/12/18
 * @project framework
 */
public class RedisStringUtil extends RedisUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //Redis 字符串(String)-----

    /**
     * Redis 字符串命令
     * 设置指定 key 的值
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public static String setValue(String key, String value, int expire) {
        return setValue(0, key, value, expire);
    }

    public static String setValue(int dbIndex, String key, String value, int expire) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.set(key, value);
            Long exp = jedis.expire(key, expire);
            LOGGER.debug("exp:{}", exp);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * @param dbIndex
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public static String setNx(int dbIndex, String key, String value, int expire){
        try (Jedis jedis = jedisPool.getResource()) {
            String result = set(dbIndex,key, value,"nx","ex",expire);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * @param dbIndex
     * @param key
     * @param value
     * @param nxxx nx:只有当key不存在是才进行set，xx，则只有当key已经存在时才进行set
     * @param expx ex 秒，px 毫秒
     * @param expire
     * @return
     */
    public static String set(int dbIndex, String key, String value,String nxxx ,String expx,int expire){
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.set(key, value,nxxx,expx,expire);
            Long exp = jedis.expire(key, expire);
            LOGGER.debug("exp:{}", exp);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * Redis 字符串命令
     * 获取指定 key 的值
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        return getString(0, key);
    }

    public static String getString(int dbIndex, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String value = jedis.get(key);
            return value;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Redis 字符串命令
     * 返回 key 中字符串值的子字符
     * GETRANGE key start end
     *
     * @param key
     * @return
     */
    public String getRange(String key, long startIndex, long endIndex) {
        return getRange(0, key, startIndex, endIndex);
    }

    private String getRange(int dbIndex, String key, long startIndex, long endIndex) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String range = jedis.getrange(key, startIndex, endIndex);
            return range;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Redis 字符串命令
     * 返回 key 中字符串值的子字符
     *
     * @param key
     * @param value
     * @return
     */
    public String getSetValue(String key, String value) {
        return getSetValue(0, key, value);
    }

    public String getSetValue(int dbIndex, String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String oldValue = jedis.getSet(key, value);
            return oldValue;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Redis 字符串命令
     * Redis Getbit 命令用于对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
     *
     * @return
     */
    public void getBitKeyOffset() {
    }

    /**
     * Redis 字符串命令
     * MGET key1 [key2..]
     * 获取所有(一个或多个)给定 key 的值。
     *
     * @param dbIndex
     * @param keys
     * @return
     */
    public List<String> mGet(int dbIndex, String... keys) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            List<String> valueList = jedis.mget(keys);
            return valueList;
        } catch (Exception e) {
            throw e;
        }
    }

    public Map<String, Object> mget(int dbIndex, String... keys) {
        int len = keys.length;
        byte[][] bytes = new byte[len][];
        for (int i = 0; i < len; i++) {
            bytes[i] = keys[i].getBytes();
        }
        Map<String, Object> result = new HashMap<>(len);
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            List<byte[]> valueList = jedis.mget(bytes);
            int size = valueList.size();
            for (int i = 0; i < size; i++) {
                byte[] byteList = valueList.get(i);
                if (byteList != null) {
                    result.put(keys[i], TypeConvertUtil.bytesToObject(byteList));
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public Map<String, Object> mget(int dbIndex, Collection keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return null;
        }
        int size = keys.size();
        String[] mgetKey = new String[size];
        Iterator<String> iterator = keys.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            mgetKey[i] = iterator.next();
            i++;
        }
        return mget(dbIndex, mgetKey);
    }

    /**
     * Redis 字符串命令
     * SETBIT key offset value
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
     */
    public void setBitKeyOffset() {
    }

    /**
     * Redis 字符串命令
     * SETEX key seconds value
     * 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。
     * Redis Setex 命令为指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值。
     *
     * @param dbIndex
     * @param key
     * @param expire
     * @param value
     * @return
     */
    public String setExKeySecondsValue(int dbIndex, String key, String value, int expire) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.setex(key, expire, value);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Redis 字符串命令
     * SETNX key value
     * 只有在 key 不存在时设置 key 的值。
     * Redis Setnx（SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。
     * 设置成功，返回 1 。 设置失败，返回 0
     *
     * @param dbIndex
     * @param key
     * @param value
     * @return
     */
    public Long setNxKeyValue(int dbIndex, String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.setnx(key, value);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Redis 字符串命令
     * SETRANGE key offset value
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始。
     * Redis Setrange 命令用指定的字符串覆盖给定 key 所储存的字符串值，覆盖的位置从偏移量 offset 开始。
     * SETRANGE KEY_NAME OFFSET VALUE
     *
     * @param dbIndex
     * @param key
     * @param value
     * @param offset
     * @return
     */
    public Long setRange(int dbIndex, String key, String value, long offset) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.setrange(key, offset, value);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Redis 字符串命令
     * STRLEN key
     * 返回 key 所储存的字符串值的长度
     * Redis Strlen 命令用于获取指定 key 所储存的字符串值的长度。当 key 储存的不是字符串值时，返回一个错误
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public Long getStrLen(int dbIndex, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.strlen(key);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Redis 字符串命令
     * MSET key value [key value ...]
     * 同时设置一个或多个 key-value 对
     * Redis Mset 命令用于同时设置一个或多个 key-value 对。
     * ???
     *
     * @param dbIndex
     * @param values
     * @return
     */
    public String mSet(int dbIndex, String... values) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.mset(values);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 13	MSETNX key value [key value ...]
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
     * 14	PSETEX key milliseconds value
     * 这个命令和 SETEX 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 SETEX 命令那样，以秒为单位。
     * 15	INCR key
     * 将 key 中储存的数字值增一。
     * 16	INCRBY key increment
     * 将 key 所储存的值加上给定的增量值（increment） 。
     * 17	INCRBYFLOAT key increment
     * 将 key 所储存的值加上给定的浮点增量值（increment） 。
     * 18	DECR key
     * 将 key 中储存的数字值减一。
     * 19	DECRBY key decrement
     * key 所储存的值减去给定的减量值（decrement） 。
     * 20	APPEND key value
     * 如果 key 已经存在并且是一个字符串， APPEND 命令将指定的 value 追加到该 key 原来值（value）的末尾。
     *
     * @param dbIndex
     * @param key
     * @return
     */
    //Redis 字符串命令---end


//    private void selectDbIndex(Jedis jedis, int dbIndex) {
//        if (dbIndex > 0) {
//            jedis.select(dbIndex);
//        }
//    }
}