package org.land.admin.vo;

import org.land.admin.entity.Dept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * <p>
 * 系统部门信息扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="系统部门信息扩展对象", description="系统部门信息扩展对象", parent = Dept.class)
public class DeptVo extends Dept {
    @ApiModelProperty(value = "树结构中显示的名称",dataType = "String")
    private String label;

    @ApiModelProperty(value = "树结构中子节点对象集合",dataType = "List<DeptVo>")
    private List<DeptVo> children;

    @ApiModelProperty(value = "树结构中的层级",dataType = "String")
    private Integer level;
}
