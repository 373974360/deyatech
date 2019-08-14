package com.deyatech.admin.service;

import com.deyatech.admin.entity.MetadataCategory;
import com.deyatech.admin.vo.MetadataCategoryVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  元数据分类 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-08-07
 */
public interface MetadataCategoryService extends BaseService<MetadataCategory> {

    /**
     * 根据MetadataCategory对象属性检索元数据分类的tree对象
     *
     * @param metadataCategory
     * @return
     */
    Collection<MetadataCategoryVo> getMetadataCategoryTree(MetadataCategory metadataCategory);

    /**
     * 单个将对象转换为vo元数据分类
     *
     * @param metadataCategory
     * @return
     */
    MetadataCategoryVo setVoProperties(MetadataCategory metadataCategory);

    /**
     * 批量将对象转换为vo元数据分类
     *
     * @param metadataCategorys
     * @return
     */
    List<MetadataCategoryVo> setVoProperties(Collection metadataCategorys);

    /**
     * 检查分类下是否存在元数据
     *
     * @param id
     * @return
     */
    boolean checkMetadataExist(String id);
}
