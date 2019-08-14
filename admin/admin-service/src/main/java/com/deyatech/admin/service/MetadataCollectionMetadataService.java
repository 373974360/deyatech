package com.deyatech.admin.service;

import com.deyatech.admin.entity.MetadataCollectionMetadata;
import com.deyatech.admin.vo.MetadataCollectionMetadataVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  元数据集元数据关联 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-08-07
 */
public interface MetadataCollectionMetadataService extends BaseService<MetadataCollectionMetadata> {

    /**
     * 单个将对象转换为vo
     *
     * @param metadataCollectionMetadata
     * @return
     */
    MetadataCollectionMetadataVo setVoProperties(MetadataCollectionMetadata metadataCollectionMetadata);

    /**
     * 批量将对象转换为vo
     *
     * @param metadataCollectionMetadatas
     * @return
     */
    List<MetadataCollectionMetadataVo> setVoProperties(Collection metadataCollectionMetadatas);
}
