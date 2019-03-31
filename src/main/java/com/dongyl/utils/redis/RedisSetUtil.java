package com.dongyl.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.lang.invoke.MethodHandles;

/**
 * Redis 集合(Set)
 *
 * @author dongyl
 * @desc
 * @date 2018/8/13 11:04
 */
public class RedisSetUtil extends RedisUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * SADD key member1 [member2]
     * 向集合添加一个或多个成员
     *
     * @param dbIndex
     * @param key
     * @param members
     * @return
     */
    public Long sadd(int dbIndex, String key, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.sadd(key, members);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * SCARD key
     * 获取集合的成员数
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public Long scard(int dbIndex, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.scard(key);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * SPOP key
     * 移除并返回集合中的一个随机元素
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public String spop(int dbIndex, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            String result = jedis.spop(key);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * SREM key member1 [member2]
     * 移除集合中一个或多个成员
     *
     * @param dbIndex
     * @param key
     * @return
     */
    public Long srem(int dbIndex, String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            selectDbIndex(jedis, dbIndex);
            Long result = jedis.srem(key);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     4	SDIFFSTORE destination key1 [key2]
     返回给定所有集合的差集并存储在 destination 中
     5	SINTER key1 [key2]
     返回给定所有集合的交集
     6	SINTERSTORE destination key1 [key2]
     返回给定所有集合的交集并存储在 destination 中
     7	SISMEMBER key member
     判断 member 元素是否是集合 key 的成员
     8	SMEMBERS key
     返回集合中的所有成员
     9	SMOVE source destination member
     将 member 元素从 source 集合移动到 destination 集合

     11	SRANDMEMBER key [count]
     返回集合中一个或多个随机数
     12
     13	SUNION key1 [key2]
     返回所有给定集合的并集
     14	SUNIONSTORE destination key1 [key2]
     所有给定集合的并集存储在 destination 集合中
     15	SSCAN key cursor [MATCH pattern] [COUNT count]
     迭代集合中的元素
     */

}
