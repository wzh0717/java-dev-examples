package com.wangzh.app.weixin.pa.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 *  https://www.cnblogs.com/antball/p/9239663.html
 *  https://www.cnblogs.com/zeng1994/p/03303c805731afc9aa9c60dbbd32a323.html#4214852
 * @Description:
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
}
