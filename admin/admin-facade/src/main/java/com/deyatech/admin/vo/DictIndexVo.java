package com.deyatech.admin.vo;

import com.deyatech.admin.entity.DictIndex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统数据字典索引信息扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="系统数据字典索引信息扩展对象", description="系统数据字典索引信息扩展对象", parent = DictIndex.class)
public class DictIndexVo extends DictIndex {
}
