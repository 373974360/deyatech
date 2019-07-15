package com.deyatech.common.submail;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 获取短信模板列表结果
 * </p>
 *
 * @author ycx.
 * @since 2019-05-21
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class SubMailTemplateResult {
    private String status;
    private List<SubMailTemplate> templates;
}
