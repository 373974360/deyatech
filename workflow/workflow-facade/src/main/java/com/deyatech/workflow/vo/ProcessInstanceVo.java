package com.deyatech.workflow.vo;

import com.deyatech.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author doukang
 * @description TODO
 * @date 2019/8/6 10:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "流程实例扩展对象", description = "流程实例扩展对象")
public class ProcessInstanceVo extends BaseEntity {

    private String actDefinitionKey;

    private String businessId;

    private Map<String, Object> variables;

    private String userId;

    private String source;
}
