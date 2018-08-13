package com.dongyl.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Redis 列表(List)
 *
 * @author dongyl
 * @desc
 * @date 2018/8/13 11:29
 */
public class RedisListUtil extends RedisUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public List<String> blpop(int dbIndex, String... keys) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            List<String> result = jedis.blpop(keys);
            return result;
        }
    }

    /**
     * BLPOP key1 [key2 ] timeout
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     *
     * @param dbIndex
     * @param expire
     * @param keys
     * @return
     */
    public List<String> blpop(int dbIndex, int expire, String... keys) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            List<String> result = jedis.blpop(expire, keys);
            return result;
        }
    }

    public List<String> brpop(int dbIndex, String... keys) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            List<String> result = jedis.brpop(keys);
            return result;
        }
    }

    /**
     * BRPOP key1 [key2 ] timeout
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     *
     * @param dbIndex
     * @param expire
     * @param keys
     * @return
     */
    public List<String> brpop(int dbIndex, int expire, String... keys) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            List<String> result = jedis.brpop(expire, keys);
            return result;
        }
    }

    /**
     * LPOP key
     * 移出并获取列表的第一个元素
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public String lpop(int dbIndex, String key) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.lpop(key);
            return result;
        }
    }

    /**
     * LPUSH key value1 [value2]
     * 将一个或多个值插入到列表头部
     *
     * @param dbIndex
     * @param key
     * @param strings
     * @return
     */
    public Long lpush(int dbIndex, String key, String... strings) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.lpush(key, strings);
            return result;
        }
    }

    /**
     * RPOP key
     * 移除并获取列表最后一个元素
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public String rpop(int dbIndex, String key) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.rpop(key);
            return result;
        }
    }

    /**
     * LLEN key
     * 获取列表长度
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public Long llen(int dbIndex, String key) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.llen(key);
            return result;
        }
    }

    /**
     * LTRIM key start stop
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     *
     * @param dbIndex
     * @param key
     * @param start
     * @param end
     * @return
     */
    public String ltrim(int dbIndex, String key, long start, long end) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.ltrim(key, start, end);
            return result;
        }
    }

    /**
     * BRPOPLPUSH source destination timeout
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     *
     * @param dbIndex
     * @param source
     * @param destination
     * @param expire
     * @return
     */
    public String brpoplpush(int dbIndex, String source, String destination, int expire) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.brpoplpush(source, destination, expire);
            return result;
        }
    }

    /**
     * LPUSHX key value
     * 将一个值插入到已存在的列表头部
     *
     * @param dbIndex
     * @param key
     * @param string
     * @return
     */
    public Long lpushx(int dbIndex, String key, String... string) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.lpushx(key, string);
            return result;
        }
    }

    /**
     * LINDEX key index
     * 通过索引获取列表中的元素
     *
     * @param dbIndex
     * @param key
     * @param index
     * @return
     */
    public String lpushx(int dbIndex, String key, long index) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.lindex(key, index);
            return result;
        }
    }

    /**
     * RPUSH key value1 [value2]
     * 在列表中添加一个或多个值
     *
     * @param dbIndex
     * @param key
     * @param string
     * @return
     */
    public Long rpush(int dbIndex, String key, String... string) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.rpush(key, string);
            return result;
        }
    }

    /**
     * LINSERT key BEFORE|AFTER pivot value
     * 在列表的元素前或者后插入元素
     *
     * @param dbIndex
     * @param key
     * @param where
     * @param pivot
     * @param value
     * @return
     */
    public Long linsert(int dbIndex, String key, Client.LIST_POSITION where, String pivot, String value) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.linsert(key, where, pivot, value);
            return result;
        }
    }

    /**
     * LRANGE key start stop
     * 获取列表指定范围内的元素
     *
     * @param dbIndex
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(int dbIndex, String key, long start, long end) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            List<String> result = jedis.lrange(key, start, end);
            return result;
        }
    }

    /**
     * LREM key count value
     * 移除列表元素
     *
     * @param dbIndex
     * @param key
     * @param count
     * @param value
     * @return
     */
    public Long lrange(int dbIndex, String key, long count, String value) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.lrem(key, count, value);
            return result;
        }
    }

    /**
     * LSET key index value
     * 通过索引设置列表元素的值
     *
     * @param dbIndex
     * @param key
     * @param index
     * @param value
     * @return
     */
    public String lset(int dbIndex, String key, long index, String value) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.lset(key, index, value);
            return result;
        }
    }

    /**
     * RPOPLPUSH source destination
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param dbIndex
     * @param srckey
     * @param dstkey
     * @return
     */
    public String rpoplpush(int dbIndex, String srckey, String dstkey) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.rpoplpush(srckey, dstkey);
            return result;
        }
    }

    /**
     * RPUSHX key value
     * 为已存在的列表添加值
     */
    public Long rpushx(int dbIndex, String key, String... string) {
        try (Jedis jedis = selectDbIndex(dbIndex)) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.rpushx(key, string);
            return result;
        }
    }


}
