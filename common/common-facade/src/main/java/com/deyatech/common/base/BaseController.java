package com.deyatech.common.base;

import cn.hutool.http.HttpStatus;
import com.deyatech.common.Constants;
import com.deyatech.common.exception.BusinessException;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Closeable;
import java.io.IOException;

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

    protected void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
