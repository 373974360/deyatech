package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 系统角色信息
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_role")
public class Role extends BaseEntity {

    /**
    * 角色名称
    */
    @TableField("name_")
    private String name;

    /**
    * 角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
    */
    @TableField("type_")
    private Integer type;

}
