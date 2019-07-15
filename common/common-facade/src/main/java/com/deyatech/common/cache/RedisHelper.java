package com.deyatech.common.cache;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.deyatech.common.Constants;
import com.deyatech.common.model.RedisResult;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存辅助类
 *
 * @author ShenHuaJie
 * @version 2016年4月2日 下午4:17:22
 */
public final class RedisHelper {

    private RedisTemplate<String, Object> redisTemplate = null;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public final Object get(final String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    public final Set<Object> getAll(final String pattern) {
        Set<Object> values = CollectionUtil.newHashSet();
        Set<String> keys = redisTemplate.keys(pattern);
        for (String key : keys) {
            values.add(redisTemplate.opsForValue().get(key));
        }
        return values;
    }

    public final void set(final String key, final String value, long seconds) {
        redisTemplate.boundValueOps(key).set(value);
        expire(key, seconds);
    }

    public final void set(final String key, final Object value) {
        if (key == null) {
            return;
        }
        redisTemplate.boundValueOps(key).set(value);
    }

    public final Boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public final void del(final String key) {
        redisTemplate.delete(key);
    }

    public final void delAll(final String pattern) {
        if (pattern == null) {
            return;
        }
        redisTemplate.delete(redisTemplate.keys("*".concat(pattern.concat("*"))));
    }

    public void dels(List<String> keys) {
        if (CollectionUtil.isNotEmpty(keys)) {
            for (String key : keys) {
                redisTemplate.delete(key);
            }
        }
    }

    public final String type(final String key) {
        return redisTemplate.type(key).getClass().getName();
    }

    /**
     * 在某段时间后失效
     *
     * @return
     */
    public final Boolean expire(final String key, final long seconds) {
        return redisTemplate.expire(key, seconds, TimeUnit.MILLISECONDS);
    }

    /**
     * 在某个时间点失效
     *
     * @param key
     * @param unixTime
     * @return
     */
    public final Boolean expireAt(final String key, final long unixTime) {
        return redisTemplate.expireAt(key, new Date(unixTime));
    }

    public final Long ttl(final String key) {
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    public final void setrange(final String key, final long offset, final String value) {
        redisTemplate.boundValueOps(key).set(value, offset);
    }

    public final String getrange(final String key, final long startOffset, final long endOffset) {
        return redisTemplate.boundValueOps(key).get(startOffset, endOffset);
    }

    public final Object getSet(final String key, final String value) {
        return redisTemplate.boundValueOps(key).getAndSet(value);
    }

    public boolean setnx(String key, String value) {
        return redisTemplate.boundValueOps(key).setIfAbsent(value);
    }

    public void unlock(String key) {
        del(key);
    }

    public void hset(String key, String field, String value) {
        redisTemplate.boundHashOps(key).put(field, value);
    }

    public Object hget(String key, String field) {
        return redisTemplate.boundHashOps(key).get(field);
    }

    public void hdel(String key, String field) {
        redisTemplate.boundHashOps(key).delete(field);
    }

    public void hputall(String key, Map<?, ?> map) {
        redisTemplate.boundHashOps(key).putAll(map);
    }

    public Map<?, ?> hgetall(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }


    public List<RedisResult> getAllResult(final String key) {
        List<RedisResult> list = CollectionUtil.newArrayList();
        Set<String> keys;
        if (ObjectUtil.isNull(key)) {
            keys = redisTemplate.keys("*");
        } else {
            keys = redisTemplate.keys("*".concat(key).concat("*"));
        }
        for (String temp : keys) {
            //排除redis默认生成的setkey
            if (temp.toString().indexOf(Constants.REDIS_SET_KEY) < 0) {
                String type = redisTemplate.type(temp).name();
                Object value = redisTemplate.boundValueOps(temp).get(0, Integer.MAX_VALUE);
                Long expire = redisTemplate.getExpire(temp, TimeUnit.MILLISECONDS);
                DateTime date = null;
                if(expire != -1){
                    date = DateUtil.date(DateUtil.current(false) + expire);
                }
                RedisResult redisResult = new RedisResult();
                redisResult.setKey(temp.toString());
                redisResult.setValue(value);
                redisResult.setTtl(date);
                redisResult.setType(type);
                list.add(redisResult);
            }
        }
        return list;
    }
}
