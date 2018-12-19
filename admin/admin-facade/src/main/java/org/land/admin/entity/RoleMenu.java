package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;
/**
 * <p>
 * 系统角色菜单关联信息
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_role_menu")
public class RoleMenu extends BaseEntity {

    /**
    * 角色id
    */
    @TableField("role_id")
    private String roleId;

    /**
    * 菜单id
    */
    @TableField("menu_id")
    private String menuId;

  /**
    * 记录状态，0为禁用，1为启用，-1为已删除
    */
    @TableField(exist = false)
    private Integer enable;

    /**
    * 备注
    */
    @TableField(exist = false)
    private String remark;

    /**
    * 数据记录创建者
    */
    @TableField(exist = false)
    private String createBy;

    /**
    * 数据记录创建时间
    */
    @TableField(exist = false)
    private Date createTime;

    /**
    * 数据记录更新者
    */
    @TableField(exist = false)
    private String updateBy;

    /**
    * 数据记录更新时间
    */
    @TableField(exist = false)
    private Date updateTime;

    /**
    * 乐观锁字段
    */
    @TableField(exist = false)
    private Integer version;
}
