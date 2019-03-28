package com.wangzh.app.weixin.pa.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.security.KeyPair;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * https://www.cnblogs.com/antball/p/9239663.html
 * https://www.cnblogs.com/zeng1994/p/03303c805731afc9aa9c60dbbd32a323.html#4214852
 *
 * @Description: redisTemplate 操作类
 * @CreatedDate:2019-03-27 16:59
 * @Author:wangzh
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定key失效
     *
     * @param key
     * @param timeout
     * @param timeUnit
     * @return
     */
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }


    public boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 过期时间，返回0代表为永久有效
     *
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除
     *
     * @param keys
     */
    public void delete(String... keys) {
        if (null == keys || keys.length <= 0) return;
        if (keys.length == 1) {
            redisTemplate.delete(keys[0]);
        } else {
            redisTemplate.delete(CollectionUtils.arrayToList(keys));
        }
    }

    /*********************************String**************************************/

    public Object getValue(String key) {
        return StringUtils.isBlank(key) ? null : redisTemplate.opsForValue().get(key);
    }

    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setValue(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public void setValue(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public void setValue(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * 递增/递减
     *
     * @param key
     * @param value
     * @return long
     */
    public long increment(String key, long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    /**
     * 递增/递减
     *
     * @param key
     * @param value
     * @return double
     */
    public double increment(String key, double value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    /************************************Hash**************************************/
    public Object getHash(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key
     * @param item
     * @return
     */
    public boolean hasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> getHashAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public long getHashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    public void setHashAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public void setHashAll(String key, Map<String, Object> map, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, map);
        expire(key, timeout, timeUnit);
    }

    public void setHash(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    public void setHash(String key, String item, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, item, value);
        expire(key, timeout, timeUnit);
    }

    /**
     * 删除
     *
     * @param key
     * @param items
     */
    public void deleteHash(String key, Object... items) {
        redisTemplate.opsForHash().delete(key, items);
    }

    /**
     * hash 递增/递减
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public long increment(String key, String item, long value) {
        return redisTemplate.opsForHash().increment(key, item, value);
    }

    /**
     * hash 递增/递减
     *
     * @param key
     * @param item
     * @param value
     * @return
     */
    public double increment(String key, String item, double value) {
        return redisTemplate.opsForHash().increment(key, item, value);
    }

    /************************************Set**************************************/

    /**
     * 根据key获取Set中的所有值
     *
     * @param key
     * @return
     */
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key
     * @param value
     * @return
     */
    public boolean hasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key
     * @param values
     * @return 成功个数
     */
    public long addSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    public long addSet(String key, long timeout, TimeUnit timeUnit, Object... values) {
        long count = redisTemplate.opsForSet().add(key, values);
        expire(key, timeout, timeUnit);
        return count;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key
     * @return
     */
    public long getSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }


    /**
     * 移除值为value
     *
     * @param key
     * @param values
     * @return 移除的个数
     */
    public Long deleteSet(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /************************************ZSet**************************************/
    public boolean addZSet(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public Long addZSet(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    public Long deleteZSet(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    public Long deleteZSet(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    public double incrementScore(String key, Object value, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    /**
     * 有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
     *
     * @param key
     * @param value
     * @return
     */
    public long rank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 有序集中指定成员的排名，其中有序集成员按分数值递减(从大到小)顺序排列
     *
     * @param key
     * @param value
     * @return
     */
    public long reverseRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> range(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Object> rangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }


    /**
     * 通过分数返回有序集合指定区间内的成员，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<Object> rangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    /**
     * 通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, start, end);
    }

    /**
     * 通过分数返回有序集合指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * 通过分数返回有序集合指定区间内的成员对象，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * 通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递减(从大到小)顺序排列
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> reverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递减(从大到小)顺序排列
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 与rangeByScore调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Object> reverseRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 与rangeByScoreWithScores调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * 与rangeByScore调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<Object> reverseRangeByScore(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    /**
     * 与rangeByScoreWithScores调用方法一样，其中有序集成员按分数值递减(从大到小)顺序排列
     *
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * 通过分数返回有序集合指定区间内的成员个数
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long count(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取有序集合的成员数，内部调用的就是zCard方法
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取有序集合的成员数
     *
     * @param key
     * @return
     */
    Long zCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取指定成员的score值
     *
     * @param key
     * @param value
     * @return
     */
    public Double score(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员，其中有序集成员按分数值递增(从小到大)顺序排列
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long removeRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值得范围来移除成员
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long removeRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 计算给定的一个有序集的并集，并存储在新的 destKey中，key相同的话会把score值相加
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long unionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 计算给定的多个有序集的并集，并存储在新的 destKey中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long unionAndStore(String key, List<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long intersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    Long intersectAndStore(String key, List<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 遍历zset
     *
     * @param key
     * @param options
     * @return
     */
    public Cursor<ZSetOperations.TypedTuple<Object>> scan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }

    /************************************List**************************************/

    /**
     * 获取list缓存的内容
     * 0 到 -1代表所有值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> getList(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key
     * @return
     */
    public long getListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object getValue(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将数据放入list尾部
     *
     * @param key
     * @param value
     * @return
     */
    public long setList(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将数据放入list尾部
     *
     * @param key
     * @param values
     * @return
     */
    public long setListAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    public long setListAll(String key, long timeout, TimeUnit timeUnit, Object... values) {
        long count = redisTemplate.opsForList().rightPushAll(key, values);
        expire(key, timeout, timeUnit);
        return count;
    }

    /**
     * 将数据放入list尾部
     *
     * @param key
     * @param list
     * @return
     */
    public long setListAll(String key, List<Object> list) {
        return redisTemplate.opsForList().rightPushAll(key, list);
    }

    public long setListAll(String key, long timeout, TimeUnit timeUnit, List<Object> list) {
        long count = redisTemplate.opsForList().rightPushAll(key, list);
        expire(key, timeout, timeUnit);
        return count;
    }

    /**
     * 将数据放入list尾部
     *
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     * @return
     */
    public long setList(String key, Object value, long timeout, TimeUnit timeUnit) {
        long count = redisTemplate.opsForList().rightPush(key, value);
        expire(key, timeout, timeUnit);
        return count;
    }

    public void setValue(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * @param key
     * @param count 移除多少个
     * @param value
     * @return 移除多少个
     */
    public long deleteList(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

}
