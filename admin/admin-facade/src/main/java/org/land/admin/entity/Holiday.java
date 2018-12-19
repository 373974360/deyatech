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
@TableName("admin_holiday")
public class Holiday extends BaseEntity {

    /**
    * 年份
    */
    @TableField("year_")
    private String year;

    /**
    * 节假日日期，格式为 yyyy-mm-dd
    */
    @TableField("date_")
    private String date;

}
