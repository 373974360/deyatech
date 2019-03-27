package com.deyatech.common.utils;

import com.deyatech.common.Constants;
import com.deyatech.common.cache.RedisHelper;
import com.deyatech.common.context.SpringContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class CacheUtil {
	private static Logger logger = LoggerFactory.getLogger(CacheUtil.class);
	private static RedisHelper redisHelper;

	public static RedisHelper getCache() {
		if (redisHelper == null) {
			synchronized (CacheUtil.class) {
				if (redisHelper == null) {
					redisHelper = new RedisHelper();
					redisHelper.setRedisTemplate((RedisTemplate<String, Object>) SpringContextHelper.getBean("redisTemplate"));
				}
			}
		}
		return redisHelper;
	}

	/** 获取锁 */
	public static boolean getLock(String key) {
		try {
			if (!getCache().exists(key)) {
				synchronized (CacheUtil.class) {
					if (!getCache().exists(key)) {
						if (getCache().setnx(key, String.valueOf(System.currentTimeMillis()))) {
							return true;
						}
					}
				}
			}
			int expires = 1000 * 60 * 3;
			String currentValue = (String) getCache().get(key);
			if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis() - expires) {
				unlock(key);
				return getLock(key);
			}
			return false;
		} catch (Exception e) {
			logger.error(Constants.Exception_Head, e);
			return true;
		}
	}

	public static void unlock(String key) {
		getCache().unlock(key);
	}


}
