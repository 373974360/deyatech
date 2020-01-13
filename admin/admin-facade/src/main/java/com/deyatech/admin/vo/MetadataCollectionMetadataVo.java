package com.deyatech.admin.vo;

import com.deyatech.admin.entity.MetadataCollectionMetadata;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 元数据集元数据关联扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "元数据集元数据关联扩展对象", description = "元数据集元数据关联扩展对象", parent = MetadataCollectionMetadata.class)
public class MetadataCollectionMetadataVo extends MetadataCollectionMetadata {
    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 元数据信息
     */
    private MetadataVo metadata;

    /**
     * 复合类型元数据关联的基本类型元数据
     */
    private List<MetadataCollectionMetadataVo> relationList;
}
