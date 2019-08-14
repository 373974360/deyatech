package com.deyatech.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.deyatech.admin.entity.MetadataCollection;
import com.deyatech.admin.entity.MetadataCollectionMetadata;
import com.deyatech.admin.mapper.MetadataCollectionMapper;
import com.deyatech.admin.service.MetadataCollectionMetadataService;
import com.deyatech.admin.service.MetadataCollectionService;
import com.deyatech.admin.vo.MetadataCollectionMetadataVo;
import com.deyatech.admin.vo.MetadataCollectionVo;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 元数据集 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-08-07
 */
@Service
public class MetadataCollectionServiceImpl extends BaseServiceImpl<MetadataCollectionMapper, MetadataCollection> implements MetadataCollectionService {

    @Autowired
    private MetadataCollectionMetadataService metadataCollectionMetadataService;

    /**
     * 单个将对象转换为vo
     *
     * @param metadataCollection
     * @return
     */
    @Override
    public MetadataCollectionVo setVoProperties(MetadataCollection metadataCollection){
        MetadataCollectionVo metadataCollectionVo = new MetadataCollectionVo();
        BeanUtil.copyProperties(metadataCollection, metadataCollectionVo);
        return metadataCollectionVo;
    }

    /**
     * 批量将对象转换为vo
     *
     * @param metadataCollections
     * @return
     */
    @Override
    public List<MetadataCollectionVo> setVoProperties(Collection metadataCollections){
        List<MetadataCollectionVo> metadataCollectionVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(metadataCollections)) {
            for (Object metadataCollection : metadataCollections) {
                MetadataCollectionVo metadataCollectionVo = new MetadataCollectionVo();
                BeanUtil.copyProperties(metadataCollection, metadataCollectionVo);
                metadataCollectionVos.add(metadataCollectionVo);
            }
        }
        return metadataCollectionVos;
    }

    @Override
    public MetadataCollection save(MetadataCollectionVo metadataCollectionVo) {
        MetadataCollection metadataCollection = new MetadataCollection();
        BeanUtil.copyProperties(metadataCollectionVo, metadataCollection);
        saveOrUpdate(metadataCollection);

        updateName(metadataCollection.getName(), metadataCollection.getEnName());

        // 根据元数据集和版本号删除关联的元数据关联信息
        MetadataCollectionMetadata wrapperBean = new MetadataCollectionMetadata();
        wrapperBean.setMetadataCollectionId(metadataCollection.getId());
        wrapperBean.setMdcVersion(metadataCollectionVo.getMdcVersion());
        metadataCollectionMetadataService.removeByBean(wrapperBean);

        List<MetadataCollectionMetadataVo> metadataVoList = metadataCollectionVo.getMetadataList();
        if (CollectionUtil.isNotEmpty(metadataVoList)) {
            List<MetadataCollectionMetadata> metadataList = new ArrayList<>();
            for (MetadataCollectionMetadataVo metadataVo : metadataVoList) {
                MetadataCollectionMetadata metadata = new MetadataCollectionMetadata();
                BeanUtil.copyProperties(metadataVo, metadata);
                metadata.setMetadataCollectionId(metadataCollection.getId());
                metadata.setMdcVersion(metadataCollectionVo.getMdcVersion());
                metadataList.add(metadata);
            }
            metadataCollectionMetadataService.saveBatch(metadataList);
        }
        return metadataCollection;
    }

    @Override
    public List<MetadataCollectionVo> findAllData(MetadataCollectionVo metadataCollectionVo) {
        List<MetadataCollectionVo> data = getBaseMapper().findAllData(metadataCollectionVo);
        if (CollectionUtil.isNotEmpty(data)) {
            for (MetadataCollectionVo collection : data) {
                List<MetadataCollectionMetadataVo> metadataVoList = collection.getMetadataList();
                if (CollectionUtil.isNotEmpty(metadataVoList)) {
                    collection.setMetadataList(buildRelationTree(metadataVoList));
                }
            }
        }
        return data;
    }

    @Override
    public void setMainVersion(String id) {
        MetadataCollection collection = getById(id);
        getBaseMapper().removeMainVersion(collection.getEnName());
        collection.setMainVersion(true);
        saveOrUpdate(collection);
    }

    @Override
    public boolean checkNameExist(String enName, String name) {
        MetadataCollection bean = new MetadataCollection();
        bean.setName(name);
        Collection<MetadataCollection> list = listByBean(bean);
        if (CollectionUtil.isNotEmpty(list)) {
            for (MetadataCollection mc : list) {
                if (ObjectUtil.notEqual(enName, mc.getEnName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkEnNameExist(String id, String name, String enName) {
        MetadataCollection bean = new MetadataCollection();
        bean.setEnName(enName);
        Collection<MetadataCollection> list = listByBean(bean);
        if (CollectionUtil.isNotEmpty(list)) {
            if (StrUtil.isNotBlank(id)) {
                MetadataCollection collection = getById(id);
                name = collection.getName();
            }
            for (MetadataCollection mc : list) {
                if (ObjectUtil.notEqual(name, mc.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkVersionExist(String id, String enName, String version) {
        if (StrUtil.isNotBlank(enName)) {
            MetadataCollection bean = new MetadataCollection();
            bean.setEnName(enName);
            Collection<MetadataCollection> list = listByBean(bean);
            if (CollectionUtil.isNotEmpty(list)) {
                for (MetadataCollection mc : list) {
                    if (ObjectUtil.equal(version, mc.getMdcVersion()) && ObjectUtil.notEqual(id, mc.getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void updateName(String name, String enName) {
        getBaseMapper().updateName(name, enName);
    }

    private List<MetadataCollectionMetadataVo> buildRelationTree(List<MetadataCollectionMetadataVo> metadataVoList) {
        List<MetadataCollectionMetadataVo> tree = new ArrayList<>();
        for (MetadataCollectionMetadataVo metadataVo : metadataVoList) {
            if (StrUtil.isBlank(metadataVo.getRelationId())) {
                tree.add(metadataVo);
            }
            for (MetadataCollectionMetadataVo child : metadataVoList) {
                if (ObjectUtil.equal(child.getRelationId(), metadataVo.getMetadataId())) {
                    if (ObjectUtil.isNull(metadataVo.getRelationList())) {
                        metadataVo.setRelationList(new ArrayList<>());
                    }
                    metadataVo.getRelationList().add(child);
                }
            }
        }
        return tree;
    }
}
