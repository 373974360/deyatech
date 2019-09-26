package com.deyatech.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deyatech.admin.entity.Metadata;
import com.deyatech.admin.entity.MetadataCategory;
import com.deyatech.admin.mapper.MetadataCategoryMapper;
import com.deyatech.admin.service.MetadataCategoryService;
import com.deyatech.admin.service.MetadataService;
import com.deyatech.admin.vo.MetadataCategoryVo;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.common.Constants;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 元数据分类 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-08-07
 */
@Service
public class MetadataCategoryServiceImpl extends BaseServiceImpl<MetadataCategoryMapper, MetadataCategory> implements MetadataCategoryService {

    @Autowired
    private MetadataService metadataService;

    /**
     * 根据MetadataCategory对象属性检索元数据分类的tree对象
     *
     * @param metadataCategory
     * @return
     */
    @Override
    public Collection<MetadataCategoryVo> getMetadataCategoryTree(MetadataCategory metadataCategory) {
        metadataCategory.setSortSql("sortNo asc");
        List<MetadataCategoryVo> metadataCategoryVos = setVoProperties(super.listByBean(metadataCategory));
        List<MetadataCategoryVo> rootMetadataCategorys = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(metadataCategoryVos)) {
            for (MetadataCategoryVo metadataCategoryVo : metadataCategoryVos) {
                QueryWrapper<Metadata> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("category_id", metadataCategoryVo.getId());
                metadataCategoryVo.setDataCount(metadataService.count(queryWrapper));
                metadataCategoryVo.setLabel(metadataCategoryVo.getName());
                if(StrUtil.isNotBlank(metadataCategoryVo.getTreePosition())){
                    String[] split = metadataCategoryVo.getTreePosition().split(Constants.DEFAULT_TREE_POSITION_SPLIT);
                    metadataCategoryVo.setLevel(split.length);
                }else{
                    metadataCategoryVo.setLevel(Constants.DEFAULT_ROOT_LEVEL);
                }
                if (ObjectUtil.equal(metadataCategoryVo.getParentId(), Constants.ZERO)) {
                    rootMetadataCategorys.add(metadataCategoryVo);
                }
                for (MetadataCategoryVo childVo : metadataCategoryVos) {
                    if (ObjectUtil.equal(childVo.getParentId(), metadataCategoryVo.getId())) {
                        if (ObjectUtil.isNull(metadataCategoryVo.getChildren())) {
                            List<MetadataCategoryVo> children = CollectionUtil.newArrayList();
                            children.add(childVo);
                            metadataCategoryVo.setChildren(children);
                        } else {
                            metadataCategoryVo.getChildren().add(childVo);
                        }
                    }
                }
            }
        }
        return rootMetadataCategorys;
    }

    /**
     * 单个将对象转换为vo元数据分类
     *
     * @param metadataCategory
     * @return
     */
    @Override
    public MetadataCategoryVo setVoProperties(MetadataCategory metadataCategory){
        MetadataCategoryVo metadataCategoryVo = new MetadataCategoryVo();
        BeanUtil.copyProperties(metadataCategory, metadataCategoryVo);
        return metadataCategoryVo;
    }

    /**
     * 批量将对象转换为vo元数据分类
     *
     * @param metadataCategorys
     * @return
     */
    @Override
    public List<MetadataCategoryVo> setVoProperties(Collection metadataCategorys){
        List<MetadataCategoryVo> metadataCategoryVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(metadataCategorys)) {
            for (Object metadataCategory : metadataCategorys) {
                MetadataCategoryVo metadataCategoryVo = new MetadataCategoryVo();
                BeanUtil.copyProperties(metadataCategory, metadataCategoryVo);
                metadataCategoryVos.add(metadataCategoryVo);
            }
        }
        return metadataCategoryVos;
    }

    @Override
    public boolean checkMetadataExist(String id) {
        Metadata metadata = new Metadata();
        metadata.setCategoryId(id);
        return CollectionUtil.isNotEmpty(metadataService.listByBean(metadata));
    }
}
