package com.deyatech.workflow.vo;

import com.deyatech.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "任务扩展对象", description = "任务扩展对象")
public class ProcessTaskVo extends BaseEntity {

    private String actTaskId;

    private String name;

    private Date startTime;

    private String handleUserId;

    private Date handleTime;

    private String actDefinitionKey;

    private String businessId;

    private String source;

    private String candidateUser;

    private Map<String, Object> variables;

    private List<Map<String, Object>> queryOr;
}
