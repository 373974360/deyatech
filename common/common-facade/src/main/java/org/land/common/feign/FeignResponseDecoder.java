package org.land.common.feign;

import cn.hutool.http.HttpStatus;
import feign.FeignException;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.land.common.entity.RestResult;
import org.land.common.exception.BusinessException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * <p>
 * feign返回结果解析类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:17
 */
@Slf4j
@Component
@ConditionalOnClass(FeignClient.class)
public class FeignResponseDecoder extends SpringDecoder {

    public FeignResponseDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        RestResult result = (RestResult) super.decode(response, type);
        if (result.getHttpCode().equals(HttpStatus.HTTP_OK)) {
            return result;
        } else {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, result.getMsg());
        }
    }
}
