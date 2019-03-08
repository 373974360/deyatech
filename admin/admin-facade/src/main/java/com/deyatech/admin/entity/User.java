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
 * 系统用户信息
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_user")
@ApiModel(value = "系统用户信息对象", description = "系统用户信息", parent = BaseEntity.class)
public class User extends BaseEntity {

    @ApiModelProperty(value = "部门编号", dataType = "String")
    @TableField("department_id")
    private String departmentId;

    @ApiModelProperty(value = "姓名", dataType = "String")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "性别(2:未知;1:男;0:女)", dataType = "Integer", example = "1")
    @TableField("gender_")
    private Integer gender;

    @ApiModelProperty(value = "电话", dataType = "String")
    @TableField("phone_")
    private String phone;

    @ApiModelProperty(value = "头像", dataType = "String")
    @TableField("avatar_")
    private String avatar;

    @ApiModelProperty(value = "登陆帐户", dataType = "String")
    @TableField("account_")
    private String account;

    @ApiModelProperty(value = "密码", dataType = "String")
    @TableField("password_")
    private String password;

}
