package com.deyatech.admin.vo;

import com.deyatech.admin.entity.MetadataCollection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 元数据集扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "元数据集扩展对象", description = "元数据集扩展对象", parent = MetadataCollection.class)
public class MetadataCollectionVo extends MetadataCollection {

    /**
     * 元数据集关联的元数据
     */
    List<MetadataCollectionMetadataVo> metadataList;
    List<MetadataCollectionMetadataVo> oldMetadataList;
    private boolean beUsed;
}
