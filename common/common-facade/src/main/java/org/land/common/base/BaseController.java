package org.land.common.base;

import org.land.common.Constants;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 前端控制器基类
 *
 * @author: lee.
 * @since: 2018-12-14 11:14
 */
public class BaseController {

    /**
     * 验证验证码
     *
     * @param verifyCode
     * @return
     */
    public boolean validateVerifyCode(RedisTemplate redisTemplate, String verifyCode, String random) {
        Object o = redisTemplate.opsForValue().get(Constants.PREFIX_KEY_VERIFY_CODE.concat(random));
        if (o == null) {
            return false;
        }
        String s = o.toString();
        if (verifyCode != null && verifyCode.equalsIgnoreCase(s)) {
            redisTemplate.delete(Constants.PREFIX_KEY_VERIFY_CODE.concat(random));
            return true;
        }
        return false;
    }

}
