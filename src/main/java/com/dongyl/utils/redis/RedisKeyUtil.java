package com.dongyl.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.lang.invoke.MethodHandles;

/**
 * Redis 键(key)
 *
 * @author dongyl
 * @desc
 * @date 2018/8/13 11:04
 */
public class RedisKeyUtil extends RedisUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * DEL key
     * 该命令用于在 key 存在时删除 key。
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public Long delKey(int dbIndex, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.del(key);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * TTL key
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public Long ttl(int dbIndex, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.ttl(key);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * 1
     * 2	DUMP key
     * 序列化给定 key ，并返回被序列化的值。
     * 3	EXISTS key
     * 检查给定 key 是否存在。
     * 4	EXPIRE key seconds
     * 为给定 key 设置过期时间。
     * 5	EXPIREAT key timestamp
     * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置过期时间。 不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp)。
     * 6	PEXPIRE key milliseconds
     * 设置 key 的过期时间以毫秒计。
     * 7	PEXPIREAT key milliseconds-timestamp
     * 设置 key 过期时间的时间戳(unix timestamp) 以毫秒计
     * 8	KEYS pattern
     * 查找所有符合给定模式( pattern)的 key 。
     * 9	MOVE key db
     * 将当前数据库的 key 移动到给定的数据库 db 当中。
     * 10	PERSIST key
     * 移除 key 的过期时间，key 将持久保持。
     * 11	PTTL key
     * 以毫秒为单位返回 key 的剩余的过期时间。
     * 12
     * 13	RANDOMKEY
     * 从当前数据库中随机返回一个 key 。
     * 14	RENAME key newkey
     * 修改 key 的名称
     * 15	RENAMENX key newkey
     * 仅当 newkey 不存在时，将 key 改名为 newkey 。
     * 16	TYPE key
     * 返回 key 所储存的值的类型。
     */
}
