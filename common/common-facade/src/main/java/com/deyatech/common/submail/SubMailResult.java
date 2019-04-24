package com.deyatech.common.submail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * submail短信发送、短信模板返回结果类
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class SubMailResult implements Serializable {

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String status;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String to;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String send_id;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Float fee;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String sms_credits;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String code;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String msg;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private String template_id;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private List<SubMailTemplate> templates;
}
