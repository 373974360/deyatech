package com.deyatech.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.deyatech.common.Constants;
import com.deyatech.common.entity.AppResponse;
import com.deyatech.common.utils.AesUtil;
import com.deyatech.gateway.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 第三方请求验证过滤器
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 11:49
 */
@Slf4j
@Component
public class ExternalGlobaFilter implements GlobalFilter, Ordered {

    @Autowired
    SecurityConfig securityConfig;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = exchange.getRequest().getPath().toString();
        if (isStartWith(url)) {
            MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
            String thirdAppKey = queryParams.getFirst("appKey");
            if (StrUtil.isBlank(thirdAppKey)) {
                return exceptError(response, "appKey is not empty");
            }

            if (!securityConfig.getAppKey().equals(thirdAppKey)) {
                return exceptError(response, "appKey不存在，请联系合作方");
            }

            String timestamp = queryParams.getFirst("timestamp");
            if (StrUtil.isBlank(timestamp)) {
                return exceptError(response, "timestamp is not empty");
            }

            String thirdToken = queryParams.getFirst("token");
            if (StrUtil.isBlank(thirdToken)) {
                return exceptError(response, "token is not empty");
            }
            String token = generaterToken(queryParams);
            if (!token.equals(thirdToken)) {
                return exceptError(response, "token验证不通过");
            }
        }
        return chain.filter(exchange.mutate().request(request.mutate().headers(httpHeaders -> httpHeaders.add(Constants.GATEWAY_HEADER, AesUtil.aesEncrypt(Constants.GATEWAY_VALUE))).build()).build());
    }

    /**
     * 返回数据处理
     * @param response
     * @param message
     * @return
     */
    private Mono<Void> exceptError(ServerHttpResponse response, String message) {
        AppResponse restResult = AppResponse.error(message);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(restResult).getBytes());
        return response.writeWith(Mono.just(bodyDataBuffer));
    }
    /**
     * URI是否以什么打头
     *
     * @param requestUri
     * @return
     */
    private boolean isStartWith(String requestUri) {
        boolean flag = false;
        for (String s : securityConfig.getUrl().split(",")) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return flag;
    }

    /**
     * 生成token
     *
     * @param params
     * @return
     */
    public static String generaterToken(MultiValueMap<String, String> params) {
        SecurityConfig securityConfig = new SecurityConfig();
        MultiValueMap<String, String> newParams = new LinkedMultiValueMap<>();
        for (String key : params.keySet()) {
            List<String> value = params.get(key);
            if (key.equals("token")) {
                continue;
            }
            if (value.get(0) == null) {
                continue;
            }
            newParams.add(key, value.get(0));
        }
        String strParams = sortParams(newParams);
        strParams = strParams + "," + securityConfig.getAppSecret();
        String token = encode(strParams);
        return token;
    }

    /**
     * md5 32位加密算法
     *
     * @param text
     * @return
     */
    public static String encode(String text) {

        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 参数排序和组建
     *
     * @param params2
     */
    public static String sortParams(MultiValueMap<String, String> params2) {
        String[] sortedKeys = params2.keySet().toArray(new String[]{});
        Arrays.sort(sortedKeys);
        StringBuilder s2 = new StringBuilder();
        for (String key : sortedKeys) {
            s2.append(key).append(params2.get(key));
        }
        s2.deleteCharAt(s2.length() - 1);
        return s2.toString();
    }

    /**
     * 测试获取token
     * @param args
     */
    public static void main(String[] args) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> params11 = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> params22 = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> params3 = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> params33 = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> params4 = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> params44 = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> params5 = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> params55 = new LinkedMultiValueMap<>();

        params.add("timestamp", "2019-08-08 16:44:00");
        params.add("appKey", "03lnhZ3fG3");
        /**
         * 根据事项信息查询某一个日期下，每个时间段可预约人数
         */
        params1.add("date", "2019-08-08");
        params1.add("itemId", "1e2a6b8c74924aeb91a8cf479fea9efa");
        params11.addAll(params);
        params11.addAll(params1);
        System.out.println("根据事项信息查询某一个日期下，每个时间段可预约人数=====>>"+generaterToken(params11));

        /**
         * 根据事项信息、预约时间、手机号码申请预约
         */
        params2.add("date", "2019-08-08");
        params2.add("itemId", "1e2a6b8c74924aeb91a8cf479fea9efa");
        params2.add("time", "13:00-13:59");
        params2.add("phone", "15109260612");
        params2.add("userId", "1126744462988050433");
        params2.add("processNumber", "17876524352367263726");
        params2.add("IsSendSms", "1");
        params2.add("address", "西安国际港务区");
        params2.add("itemName", "公共卫生");
        params2.add("preorderChannel", "1");
        params22.addAll(params);
        params22.addAll(params2);
        System.out.println("根据事项信息、预约时间、手机号码申请预约=====>>"+generaterToken(params22));
        /**
         *  取消预约
         */
        params3.add("id", "1158998421542653954");
        params33.addAll(params);
        params33.addAll(params3);
        System.out.println("取消预约=====>>"+generaterToken(params33));
        /**
         * 根据用户ID查找用户的预约记录
         */
        params4.add("userId", "1126744462988050433");
        params4.add("page", "2");
        params4.add("rows", "2");
        params44.addAll(params);
        params44.addAll(params4);
        System.out.println("根据用户ID查找用户的预约记录=====>>"+generaterToken(params44));
        /**
         * 获取当前事项可预约时间
         */
        params5.add("itemId", "1e2a6b8c74924aeb91a8cf479fea9efa");
        params55.addAll(params);
        params55.addAll(params5);
        System.out.println("获取当前事项可预约时间=====>>"+generaterToken(params55));
    }


    @Override
    public int getOrder() {
        return -1000;
    }
}
