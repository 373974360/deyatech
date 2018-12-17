package org.litisn.common.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 基础类
 * @author: litisn
 * @since: 2018-12-13 18:12
 */
@Data
@Accessors(chain = true)
public class BaseEntity {
    @TableId(value = "id_", type = IdType.ID_WORKER_STR)
    private String id;
    @TableLogic
    @TableField("enable_")
    private Integer enable;
    @Version
    @TableField("version_")
    private Integer version;
    @TableField("remark_")
    private String remark;
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;
}
