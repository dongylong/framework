package com.dongyl.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

/**
 * Redis 哈希(Hash)
 *
 * @author dongyl
 * @desc
 * @date 2018/8/13 11:04
 */
public class RedisHashUtil extends RedisUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * HDEL key field1 [field2]
     * 删除一个或多个哈希表字段
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public Long hDel(int dbIndex, String key, String... fields) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.hdel(key, fields);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * HEXISTS key field
     * 查看哈希表 key 中，指定的字段是否存在。
     *
     * @param dbIndex
     * @param key
     * @param field
     * @return
     */
    public Boolean hExists(int dbIndex, String key, String field) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Boolean result = jedis.hexists(key, field);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * HGET key field
     * * 获取存储在哈希表中指定字段的值。
     *
     * @param dbIndex
     * @param key
     * @param field
     * @return
     */
    public String hGet(int dbIndex, String key, String field) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.hget(key, field);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * HGETALL key
     * 获取在哈希表中指定 key 的所有字段和值
     * Redis Hgetall 命令用于返回哈希表中，所有的字段和值。
     * 在返回值里，紧跟每个字段名(field name)之后是字段的值(value)，所以返回值的长度是哈希表大小的两倍。
     * HGETALL KEY_NAME
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public Map<String, String> hGetAll(int dbIndex, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Map<String, String> result = jedis.hgetAll(key);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * HLEN key
     * 获取哈希表中字段的数量
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public Long hLen(int dbIndex, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.hlen(key);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * HMGET key field1 [field2]
     * 获取所有给定字段的值
     *
     * @param dbIndex
     * @param key
     * @param fields
     * @return
     */
    public List<String> hMget(int dbIndex, String key, String... fields) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            List<String> result = jedis.hmget(key, fields);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * HMSET key field1 value1 [field2 value2 ]
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     *
     * @param dbIndex
     * @param key
     * @param fields
     * @return
     */
    public String hmset(int dbIndex, String key, Map<String, String> fields) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.hmset(key, fields);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public String hmset(int dbIndex, byte[] key, Map<byte[], byte[]> fields) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.hmset(key, fields);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * HSET key field value
     * 将哈希表 key 中的字段 field 的值设为 value 。
     *
     * @param dbIndex
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hset(int dbIndex, String key, String field, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.hset(key, field, value);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public Long hset(int dbIndex, byte[] key, byte[] field, byte[] value) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.hset(key, field, value);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * 5	HINCRBY key field increment
     * 为哈希表 key 中的指定字段的整数值加上增量 increment 。
     * 6	HINCRBYFLOAT key field increment
     * 为哈希表 key 中的指定字段的浮点数值加上增量 increment 。
     * 7	HKEYS key
     * 获取所有哈希表中的字段
     * 12	HSETNX key field value
     * 只有在字段 field 不存在时，设置哈希表字段的值。
     * 13	HVALS key
     * 获取哈希表中所有值
     * 14	HSCAN key cursor [MATCH pattern] [COUNT count]
     * 迭代哈希表中的键值对。
     */
}
