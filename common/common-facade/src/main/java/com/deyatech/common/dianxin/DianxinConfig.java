package com.deyatech.common.dianxin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.deyatech.common.dianxin.DianxinConfig.PREFIX;


/**
 * <p>
 * 电信webservice配置类
 * </p>
 *
 * @author yxz
 * @since 2019-04-28
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ConfigurationProperties(prefix = PREFIX)
public class DianxinConfig {


    public static final String PREFIX = "dianxin";

    /**
     * 企业识别号码， 02985992688
     */
    private String accountNumber;

    /**
     * webservice地址，http://logistics.118399.cn/LogisticsService.asmx?WSDL
     */
    private String wsdlUrl;

    /**
     * webservice方法，playWavTmpFile
     */
    private String methodName;

}
