package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 系统用户信息
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_user")
public class User extends BaseEntity {

    /**
    * 部门编号
    */
    @TableField("dept_id")
    private String deptId;

    /**
    * 姓名
    */
    @TableField("name_")
    private String name;

    /**
    * 性别(2:未知;1:男;0:女)
    */
    @TableField("gender_")
    private Integer gender;

    /**
    * 电话
    */
    @TableField("phone_")
    private String phone;

    /**
    * 头像
    */
    @TableField("avatar_")
    private String avatar;

    /**
    * 登陆帐户
    */
    @TableField("account_")
    private String account;

    /**
    * 密码
    */
    @TableField("password_")
    private String password;

}
