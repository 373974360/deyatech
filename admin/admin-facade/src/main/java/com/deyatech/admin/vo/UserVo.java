package com.deyatech.admin.vo;

import com.deyatech.admin.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户信息扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "系统用户信息扩展对象", description = "系统用户信息扩展对象", parent = User.class)
public class UserVo extends User {

    /**
     * 用户token信息
     */
    private String token;

    /**
     * 用户拥有的权限列表
     */
    private String[] permissions;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门树结构索引位置
     */
    private String departmentTreePosition;

    /**
     * 用户关联站群数量
     */
    private int stationGroupNumber;

    /**
     * 用户关联站群栏目数量
     */
    private int catalogNumber;
}
