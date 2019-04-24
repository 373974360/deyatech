package com.deyatech.common.submail;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.deyatech.common.submail.SubMailConfig.PREFIX;

/**
 * <p>
 * submail短信配置类
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ConfigurationProperties(prefix = PREFIX)
public class SubMailConfig {
    public static final String PREFIX = "subMail";

    /**
     * 短信验证码
     */
    private String sendUrl = "http://api.submail.cn/message/multixsend.json";
    private String templateUrl = "http://api.submail.cn/message/template.json";
    private String appId;
    private String appKey;

    private boolean mock = false;
}
