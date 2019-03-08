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
 * 系统角色信息
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_role")
@ApiModel(value = "系统角色信息对象", description = "系统角色信息", parent = BaseEntity.class)
public class Role extends BaseEntity {

    @ApiModelProperty(value = "角色名称", dataType = "String")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)", dataType = "Integer", example = "1")
    @TableField("type_")
    private Integer type;

}
