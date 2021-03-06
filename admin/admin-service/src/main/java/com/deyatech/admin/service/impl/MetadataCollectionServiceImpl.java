package com.deyatech.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.Metadata;
import com.deyatech.admin.entity.MetadataCollection;
import com.deyatech.admin.entity.MetadataCollectionMetadata;
import com.deyatech.admin.mapper.MetadataCollectionMapper;
import com.deyatech.admin.service.MetadataCollectionMetadataService;
import com.deyatech.admin.service.MetadataCollectionService;
import com.deyatech.admin.service.MetadataService;
import com.deyatech.admin.util.MetaUtils;
import com.deyatech.admin.vo.MetadataCollectionMetadataVo;
import com.deyatech.admin.vo.MetadataCollectionVo;
import com.deyatech.admin.vo.MetadataVo;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Handler;
import java.util.stream.Collectors;

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

    @Autowired
    private MetadataService metadataService;

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
                long count = MetaUtils.countTotal(MetaUtils.getTableName(metadataCollectionVo));
                metadataCollectionVo.setBeUsed(count > 0 ? true : false);
                metadataCollectionVos.add(metadataCollectionVo);
            }
        }
        return metadataCollectionVos;
    }

    @Override
    public IPage<MetadataCollection> pageByBean(MetadataCollection entity) {
        QueryWrapper<MetadataCollection> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(entity.getName())) {
            wrapper.like("name_", entity.getName());
        }
        if (entity.getMainVersion() != null) {
            wrapper.eq("main_version", entity.getMainVersion());
        }
        return super.page(getPageByBean(entity), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MetadataCollection save(MetadataCollectionVo metadataCollectionVo) {

        boolean isUpdate = false;
        if (StrUtil.isNotEmpty(metadataCollectionVo.getId())) {
            isUpdate = true;
        }
        MetadataCollection metadataCollection = new MetadataCollection();
        BeanUtil.copyProperties(metadataCollectionVo, metadataCollection);
        saveOrUpdate(metadataCollection);

        updateName(metadataCollection.getName(), metadataCollection.getEnName());

        // 根据元数据集和版本号删除关联的元数据关联信息
        MetadataCollectionMetadata wrapperBean = new MetadataCollectionMetadata();
        wrapperBean.setMetadataCollectionId(metadataCollection.getId());
        wrapperBean.setMdcVersion(metadataCollectionVo.getMdcVersion());
        // 检索旧的数据
        Collection<MetadataCollectionMetadata> oldMetadataList = metadataCollectionMetadataService.listByBean(wrapperBean);
        if (CollectionUtil.isNotEmpty(oldMetadataList)) {
            List<MetadataCollectionMetadataVo> oldMetadataVoList = new ArrayList<>();
            for (MetadataCollectionMetadata cmd : oldMetadataList) {
                MetadataCollectionMetadataVo cmdVo = metadataCollectionMetadataService.setVoProperties(cmd);
                cmdVo.setFieldName(metadataCollectionVo.getMdPrefix() + cmd.getBriefName());
                oldMetadataVoList.add(cmdVo);
            }
            metadataCollectionVo.setOldMetadataList(oldMetadataVoList);
        }
        // 删除旧的数据
        metadataCollectionMetadataService.removeByBean(wrapperBean);

        List<MetadataCollectionMetadataVo> metadataVoList = metadataCollectionVo.getMetadataList();
        if (CollectionUtil.isNotEmpty(metadataVoList)) {
            List<MetadataCollectionMetadata> collectionMetadataList = new ArrayList<>();
            for (MetadataCollectionMetadataVo collectionMetadataVo : metadataVoList) {
                MetadataVo md = metadataService.setVoProperties(metadataService.getById(collectionMetadataVo.getMetadataId()));
                // 画面变更的部分
                md.setName(collectionMetadataVo.getLabel());
                md.setCheckModel(collectionMetadataVo.getCheckModel());
                collectionMetadataVo.setMetadata(md);

                MetadataCollectionMetadata collectionMetadata = new MetadataCollectionMetadata();
                // 保存画面上的数据
                BeanUtil.copyProperties(collectionMetadataVo, collectionMetadata);
                // 不能变更的部分用元数据覆盖
                collectionMetadata.setBriefName(md.getBriefName());
                collectionMetadata.setDataType(md.getDataType());
                collectionMetadata.setDataLength(md.getDataLength());
                collectionMetadata.setControlType(md.getControlType());
                collectionMetadata.setControlLength(md.getControlLength());
                collectionMetadata.setDataSource(md.getDataSource());
                collectionMetadata.setDictionaryId(md.getDictionaryId());
                collectionMetadata.setRequired(md.getRequired());
                collectionMetadata.setMandatory(md.getMandatory());
                collectionMetadata.setMetadataCollectionId(metadataCollection.getId());
                collectionMetadata.setMdcVersion(metadataCollectionVo.getMdcVersion());
                collectionMetadataList.add(collectionMetadata);
            }
            metadataCollectionMetadataService.saveBatch(collectionMetadataList);
        }

        MetaUtils.metadataTableChange(metadataCollectionVo, isUpdate);

        return metadataCollection;
    }

    @Override
    public List<MetadataCollectionVo> findAllData(MetadataCollectionVo metadataCollectionVo) {
        List<MetadataCollectionVo> data = getBaseMapper().findAllData(metadataCollectionVo);
        if (CollectionUtil.isNotEmpty(data)) {
            for (MetadataCollectionVo collection : data) {
                long count = MetaUtils.countTotal(MetaUtils.getTableName(collection));
                collection.setBeUsed(count > 0 ? true : false);
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

    /**
     * 设置主版本
     *
     * @param enName
     */
    @Override
    public void setMainVersionByEnName(String enName) {
        MetadataCollection bean = new MetadataCollection();
        bean.setEnName(enName);
        Collection<MetadataCollection> list = listByBean(bean);
        if (CollectionUtil.isNotEmpty(list)) {
            boolean hasMainVersion = false;
            for (MetadataCollection mc : list) {
                if (mc.getMainVersion()) {
                    hasMainVersion = true;
                }
            }
            if (!hasMainVersion) {
                Optional<MetadataCollection> optional = list.stream().sorted(Comparator.comparing(MetadataCollection::getId).reversed()).findFirst();
                MetadataCollection collection = optional.get();
                collection.setMainVersion(true);
                super.saveOrUpdate(collection);
            }
        }
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

    /**
     * 统计件数
     *
     * @param id
     * @return
     */
    @Override
    public int count(String id) {
        QueryWrapper<MetadataCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_", id);
        return super.count(queryWrapper);
    }
}
