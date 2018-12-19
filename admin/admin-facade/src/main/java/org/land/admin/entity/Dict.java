package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 系统数据字典明细信息
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_dict")
public class Dict extends BaseEntity {

    /**
    * 数据字典索引编号
    */
    @TableField("index_id")
    private String indexId;

    /**
    * 英文代码
    */
    @TableField("code_")
    private String code;

    /**
    * 文字说明
    */
    @TableField("code_text")
    private String codeText;

    /**
    * 排序号
    */
    @TableField("sort_no")
    private Integer sortNo;

    /**
    * 是否可编辑
    */
    @TableField("editable_")
    private Integer editable;

}
