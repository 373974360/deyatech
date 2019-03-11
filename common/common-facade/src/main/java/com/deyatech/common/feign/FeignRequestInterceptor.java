package com.deyatech.common.feign;

import com.deyatech.common.Constants;
import com.deyatech.common.context.UserContextHelper;
import com.deyatech.common.utils.AesUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 设置feign的请求头，跟路由保持一致
 * </p>
 *
 * @author: lee.
 * @since: 2019/3/11 16:05
 */
@Slf4j
@Component
@ConditionalOnClass(FeignClient.class)
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            log.info("设置Feign请求header[" + Constants.GATEWAY_HEADER + "]:" + Constants.GATEWAY_VALUE);
            requestTemplate.header(Constants.GATEWAY_HEADER, AesUtil.aesEncrypt(Constants.GATEWAY_VALUE));
            requestTemplate.header(Constants.CONTEXT_KEY_USER_ID, UserContextHelper.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("设置Feign请求header错误", e);
        }
    }
}
