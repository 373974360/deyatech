package com.deyatech.admin.vo;

import com.deyatech.admin.entity.RoleUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统数据字典索引信息扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "系统数据字典索引信息扩展对象", description = "系统数据字典索引信息扩展对象", parent = RoleUser.class)
public class RoleUserVo extends RoleUser {

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 用户工号
     */
    private String empNo;

    /**
     * 用户登录账号
     */
    private String account;
}
