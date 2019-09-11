package com.deyatech.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.Metadata;
import com.deyatech.admin.mapper.MetadataMapper;
import com.deyatech.admin.service.MetadataService;
import com.deyatech.admin.vo.MetadataVo;
import com.deyatech.common.Constants;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.common.enums.MetadataTypeEnum;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 元数据 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-08-07
 */
@Service
public class MetadataServiceImpl extends BaseServiceImpl<MetadataMapper, Metadata> implements MetadataService {

    /**
     * 单个将对象转换为vo
     *
     * @param metadata
     * @return
     */
    @Override
    public MetadataVo setVoProperties(Metadata metadata){
        MetadataVo metadataVo = new MetadataVo();
        BeanUtil.copyProperties(metadata, metadataVo);
        return metadataVo;
    }

    /**
     * 批量将对象转换为vo
     *
     * @param metadatas
     * @return
     */
    @Override
    public List<MetadataVo> setVoProperties(Collection metadatas){
        List<MetadataVo> metadataVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(metadatas)) {
            for (Object metadata : metadatas) {
                MetadataVo metadataVo = new MetadataVo();
                BeanUtil.copyProperties(metadata, metadataVo);
                metadataVos.add(metadataVo);
            }
        }
        return metadataVos;
    }

    @Override
    public IPage<Metadata> pageByBean(Metadata entity) {
        QueryWrapper<Metadata> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(entity.getCategoryId())) {
            wrapper.eq("category_id", entity.getCategoryId());
        }
        if (StrUtil.isNotBlank(entity.getName())) {
            wrapper.like("name_", entity.getName());
        }
        return super.page(getPageByBean(entity), wrapper);
    }

    @Override
    public Metadata save(MetadataVo metadataVo) {
        Metadata metadata = new Metadata();
        BeanUtil.copyProperties(metadataVo, metadata);
        saveOrUpdate(metadata);
        if (MetadataTypeEnum.COMPOSITE.getCode().equals(metadataVo.getType())) {
            // 解除旧关联关系
            unRelate(metadata.getId());

            if (CollectionUtil.isNotEmpty(metadataVo.getRelationList())) {
                for (MetadataVo basic : metadataVo.getRelationList()) {
                    basic.setRelationId(metadata.getId());
                    saveOrUpdate(basic);
                }
            }
        }
        return metadata;
    }

    @Override
    public List<Metadata> findByRelationId(String id) {
        Metadata metadata = new Metadata();
        metadata.setRelationId(id);
        return new ArrayList<>(listByBean(metadata));
    }

    @Override
    public void unRelate(String id) {
        getBaseMapper().unRelate(id);
    }

    @Override
    public List<Metadata> findCandidateRelation(String id, String categoryId) {
        return getBaseMapper().findCandidateRelation(id, categoryId);
    }

    @Override
    public boolean checkNameExist(String id, String name) {
        Metadata bean = new Metadata();
        bean.setName(name);
        Collection<Metadata> list = listByBean(bean);
        if (CollectionUtil.isNotEmpty(list)) {
            for (Metadata m : list) {
                if (ObjectUtil.notEqual(id, m.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkBriefNameExist(String id, String briefName) {
        Metadata bean = new Metadata();
        bean.setBriefName(briefName);
        Collection<Metadata> list = listByBean(bean);
        if (CollectionUtil.isNotEmpty(list)) {
            for (Metadata m : list) {
                if (ObjectUtil.notEqual(id, m.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkEnNameExist(String id, String enName) {
        Metadata bean = new Metadata();
        bean.setEnName(enName);
        Collection<Metadata> list = listByBean(bean);
        if (CollectionUtil.isNotEmpty(list)) {
            for (Metadata m : list) {
                if (ObjectUtil.notEqual(id, m.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
