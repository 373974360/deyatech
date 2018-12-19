package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 系统数据字典索引信息
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_dict_index")
public class DictIndex extends BaseEntity {

    /**
    * 索引关键字
    */
    @TableField("key_")
    private String key;

    /**
    * 索引名称
    */
    @TableField("name_")
    private String name;

}
