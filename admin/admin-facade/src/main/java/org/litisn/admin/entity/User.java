package org.litisn.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.litisn.common.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.jackson.JsonComponent;

/**
 * <p>
 *  系统用户信息
 * </p>
 *
 * @Author litisn
 * @since 2018-12-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆帐户
     */
    @TableField("account_")
    private String account;

    /**
     * 密码
     */
    @TableField("password_")
    @JsonIgnore
    private String password;

    /**
     * 工号
     */
    @TableField("emp_no")
    private String empNo;

    /**
     * 电话
     */
    @TableField("phone_")
    private String phone;

    /**
     * 性别(0:男;1:女)
     */
    @TableField("gender_")
    private Integer gender;

    /**
     * 姓名
     */
    @TableField("name_")
    private String name;

    /**
     * 头像
     */
    @TableField("avatar_")
    private String avatar;

    /**
     * 部门编号
     */
    @TableField("dept_id")
    private String deptId;


}
