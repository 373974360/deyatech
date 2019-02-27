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
 * 系统数据字典索引信息
 * </p>
 *
 * @author lee.
 * @since 2019-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_dict_index")
@ApiModel(value="系统数据字典索引信息对象", description="系统数据字典索引信息" ,parent = BaseEntity.class)
public class DictIndex extends BaseEntity {

    @ApiModelProperty(value = "索引关键字",dataType = "String")
    @TableField("key_")
    private String key;

    @ApiModelProperty(value = "索引名称",dataType = "String")
    @TableField("name_")
    private String name;

}
