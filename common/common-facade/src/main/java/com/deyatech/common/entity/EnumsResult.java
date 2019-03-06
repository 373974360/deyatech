package com.deyatech.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 枚举类型对象封装返回结果类
 *
 * @author : lee.
 * @since : 2018-12-14 13:27
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class EnumsResult implements Serializable {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "值")
    private List<Map<String,Object>> value;
}
