package com.deyatech.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deyatech.admin.entity.MetadataCollectionMetadata;
import com.deyatech.admin.mapper.MetadataCollectionMetadataMapper;
import com.deyatech.admin.service.MetadataCollectionMetadataService;
import com.deyatech.admin.vo.MetadataCollectionMetadataVo;
import com.deyatech.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 元数据集元数据关联 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-08-07
 */
@Service
public class MetadataCollectionMetadataServiceImpl extends BaseServiceImpl<MetadataCollectionMetadataMapper, MetadataCollectionMetadata> implements MetadataCollectionMetadataService {

    /**
     * 单个将对象转换为vo
     *
     * @param metadataCollectionMetadata
     * @return
     */
    @Override
    public MetadataCollectionMetadataVo setVoProperties(MetadataCollectionMetadata metadataCollectionMetadata){
        MetadataCollectionMetadataVo metadataCollectionMetadataVo = new MetadataCollectionMetadataVo();
        BeanUtil.copyProperties(metadataCollectionMetadata, metadataCollectionMetadataVo);
        return metadataCollectionMetadataVo;
    }

    /**
     * 批量将对象转换为vo
     *
     * @param metadataCollectionMetadatas
     * @return
     */
    @Override
    public List<MetadataCollectionMetadataVo> setVoProperties(Collection metadataCollectionMetadatas){
        List<MetadataCollectionMetadataVo> metadataCollectionMetadataVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(metadataCollectionMetadatas)) {
            for (Object metadataCollectionMetadata : metadataCollectionMetadatas) {
                MetadataCollectionMetadataVo metadataCollectionMetadataVo = new MetadataCollectionMetadataVo();
                BeanUtil.copyProperties(metadataCollectionMetadata, metadataCollectionMetadataVo);
                metadataCollectionMetadataVos.add(metadataCollectionMetadataVo);
            }
        }
        return metadataCollectionMetadataVos;
    }

    /**
     * 统计元数据使用件数
     *
     * @param metadataId
     * @return
     */
    @Override
    public int count(String metadataId) {
        QueryWrapper<MetadataCollectionMetadata> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(metadataId)) {
            queryWrapper.eq("metadata_id", metadataId);
        }
        return super.count(queryWrapper);
    }
}
