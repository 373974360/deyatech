package com.deyatech.gateway.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.deyatech.common.Constants;
import com.deyatech.gateway.log.RecorderServerHttpRequestDecorator;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 全局请求日志拦截器，打印请求信息以及结果信息
 * </p>
 *
 * @author: lee.
 * @since: 2019/4/11 18:34
 */
@Slf4j
@Component
public class AccessLogGlobalFilter implements GlobalFilter, Ordered {

    private static final String REQUEST_PREFIX = "------------请求信息开始------------\n";

    private static final String REQUEST_TAIL = "------------请求信息结束------------\n";

    private static final String RESPONSE_PREFIX = "------------响应信息开始------------\n";

    private static final String RESPONSE_TAIL = "------------响应信息结束------------\n";

    private static String HTTP_INFO_STR = "httpInfoStr";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        StringBuilder normalMsg = new StringBuilder("\n");
        ServerHttpRequest request = exchange.getRequest();
        InetSocketAddress address = request.getRemoteAddress();
        HttpMethod method = request.getMethod();
        URI url = request.getURI();
        HttpHeaders headers = request.getHeaders();
        if (StrUtil.isNotBlank(headers.getFirst(Constants.WEBSOCKET_HEADER_SIGN))) {
            return chain.filter(exchange);
        }
        RecorderServerHttpRequestDecorator requestDecorator = new RecorderServerHttpRequestDecorator(request);
        Flux<DataBuffer> body = requestDecorator.getBody();
        //读取requestBody传参
        AtomicReference<String> requestBody = new AtomicReference<>();
        HttpMethod httpMethod = request.getMethod();
        String requestParams;
        if (httpMethod == HttpMethod.GET) {
            requestParams = request.getQueryParams().toString();
        } else {
            body.subscribe(buffer -> {
                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
                requestBody.set(charBuffer.toString());
            });
            requestParams = requestBody.get();
        }
        normalMsg.append(REQUEST_PREFIX);
        normalMsg.append("请求时间：").append(DateUtil.now()).append("\n");
        normalMsg.append("请求头信息：").append(headers).append("\n");
        normalMsg.append("请求参数：").append(requestParams).append("\n");
        normalMsg.append("请求地址：").append(address.getHostName()).append(":").append(address.getPort()).append("\n");
        normalMsg.append("请求方法类型：").append(method.name()).append("\n");
        normalMsg.append("请求地址：").append(url.getPath()).append("\n");
        normalMsg.append(REQUEST_TAIL);
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();
        normalMsg.append(RESPONSE_PREFIX);
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        byte[] resultByte = null;
                        for (DataBuffer dataBuffer : dataBuffers) {
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            DataBufferUtils.release(dataBuffer);
                            if (resultByte == null) {
                                normalMsg.append("响应时间：").append(DateUtil.now()).append("\n");
                                normalMsg.append("响应状态码：").append(this.getStatusCode()).append("\n");
                                normalMsg.append("响应头信息：").append(this.getHeaders()).append("\n");
                                resultByte = content;
                            } else {
                                byte[] temp = byteMergerAll(resultByte, content);
                                resultByte = temp;
                            }
                        }
                        String responseResult = new String(resultByte, Charset.forName("UTF-8"));
                        normalMsg.append("响应结果数据：").append(responseResult).append("\n");
                        normalMsg.append(RESPONSE_TAIL);
                        exchange.getAttributes().put(HTTP_INFO_STR, normalMsg.toString());
                        return bufferFactory.wrap(resultByte);
                    }));
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().request(requestDecorator).response(decoratedResponse).build()).then(Mono.fromRunnable(() -> {
            log.info(exchange.getAttribute(HTTP_INFO_STR));
        }));
    }

    /**
     * 多个byte数组合并
     *
     * @param values
     * @return
     */
    private byte[] byteMergerAll(byte[]... values) {
        int length_byte = 0;
        for (int i = 0; i < values.length; i++) {
            length_byte += values[i].length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }

    @Override
    public int getOrder() {
        return -2;
    }

}