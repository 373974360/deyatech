package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
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
 * @since 2019-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_holiday")
@ApiModel(value="系统角色信息对象", description="系统角色信息" ,parent = BaseEntity.class)
public class Holiday extends BaseEntity {

    @ApiModelProperty(value = "年份",dataType = "String")
    @TableField("year_")
    private String year;

    @ApiModelProperty(value = "节假日日期，格式为 yyyy-mm-dd",dataType = "String")
    @TableField("date_")
    private String date;

}
