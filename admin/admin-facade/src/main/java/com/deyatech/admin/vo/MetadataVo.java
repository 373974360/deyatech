package com.deyatech.admin.vo;

import com.deyatech.admin.entity.Metadata;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 元数据扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "元数据扩展对象", description = "元数据扩展对象", parent = Metadata.class)
public class MetadataVo extends Metadata {

    /**
     * 复合类型元数据关联的基本类型元数据
     */
    private List<MetadataVo> relationList;
}
