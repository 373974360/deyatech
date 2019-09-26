package com.deyatech.admin.vo;

import com.deyatech.admin.entity.MetadataCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * <p>
 * 元数据分类扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "元数据分类扩展对象", description = "元数据分类扩展对象", parent = MetadataCategory.class)
public class MetadataCategoryVo extends MetadataCategory {

    @ApiModelProperty(value = "树结构中显示的名称", dataType = "String")
    private String label;

    @ApiModelProperty(value = "树结构中子节点对象集合", dataType = "List<MetadataCategoryVo>")
    private List<MetadataCategoryVo> children;

    @ApiModelProperty(value = "树结构中的层级", dataType = "String")
    private Integer level;

    @ApiModelProperty(value = "元数据件数", dataType = "Integer")
    private Integer dataCount;
}
