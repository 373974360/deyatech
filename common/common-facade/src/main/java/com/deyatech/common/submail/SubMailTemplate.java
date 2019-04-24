package com.deyatech.common.submail;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * <p>
 * submail短信模板对象
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class SubMailTemplate implements Serializable {
    private String id;
    private String template_id;
    private String sms_title;
    private String sms_signature;
    private String sms_content;
    private String add_date;
    private String edit_date;
    private String template_status;
    private String template_status_description;
}
