package com.deyatech.admin.vo;

import com.deyatech.admin.entity.Menu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 系统菜单信息扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "系统菜单信息扩展对象", description = "系统菜单信息扩展对象", parent = Menu.class)
public class MenuVo extends Menu {
    @ApiModelProperty(value = "树结构中显示的名称", dataType = "String")
    private String label;

    @ApiModelProperty(value = "树结构中子节点对象集合", dataType = "List<MenuVo>")
    private List<MenuVo> children;

    @ApiModelProperty(value = "树结构中的层级", dataType = "String")
    private Integer level;
}
