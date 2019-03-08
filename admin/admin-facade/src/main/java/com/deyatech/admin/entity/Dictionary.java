package com.deyatech.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.deyatech.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统数据字典明细信息
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_dictionary")
@ApiModel(value = "系统数据字典明细信息对象", description = "系统数据字典明细信息", parent = BaseEntity.class)
public class Dictionary extends BaseEntity {

    @ApiModelProperty(value = "数据字典索引编号", dataType = "String")
    @TableField("index_id")
    private String indexId;

    @ApiModelProperty(value = "英文代码", dataType = "String")
    @TableField("code_")
    private String code;

    @ApiModelProperty(value = "文字说明", dataType = "String")
    @TableField("code_text")
    private String codeText;

    @ApiModelProperty(value = "排序号", dataType = "Integer", example = "1")
    @TableField("sort_no")
    private Integer sortNo;

    @ApiModelProperty(value = "是否可编辑", dataType = "Integer", example = "1")
    @TableField("editable_")
    private Integer editable;

}
