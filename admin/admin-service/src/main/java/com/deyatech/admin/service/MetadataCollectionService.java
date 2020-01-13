package com.deyatech.admin.service;

import com.deyatech.admin.entity.MetadataCollection;
import com.deyatech.admin.vo.MetadataCollectionVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  元数据集 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-08-07
 */
public interface MetadataCollectionService extends BaseService<MetadataCollection> {

    /**
     * 单个将对象转换为vo
     *
     * @param metadataCollection
     * @return
     */
    MetadataCollectionVo setVoProperties(MetadataCollection metadataCollection);

    /**
     * 批量将对象转换为vo
     *
     * @param metadataCollections
     * @return
     */
    List<MetadataCollectionVo> setVoProperties(Collection metadataCollections);

    /**
     * 保存
     *
     * @param metadataCollectionVo
     * @return
     */
    MetadataCollection save(MetadataCollectionVo metadataCollectionVo);

    /**
     * 获取所有元数据集（包括关联的元数据）信息
     *
     * @param metadataCollectionVo
     * @return
     */
    List<MetadataCollectionVo> findAllData(MetadataCollectionVo metadataCollectionVo);

    /**
     * 设置主版本
     *
     * @param id
     */
    void setMainVersion(String id);

    /**
     * 设置主版本
     *
     * @param enName
     */
    void setMainVersionByEnName(String enName);

    /**
     * 检查中文名称是否存在
     *
     * @param enName
     * @param name
     * @return
     */
    boolean checkNameExist(String enName, String name);

    /**
     * 检查英文名称是否存在
     *
     * @param id
     * @param name
     * @param enName
     * @return
     */
    boolean checkEnNameExist(String id, String name, String enName);

    /**
     * 检查版本号是否存在
     *
     * @param id
     * @param enName
     * @param version
     * @return
     */
    boolean checkVersionExist(String id, String enName, String version);

    /**
     * 统计件数
     * @param id
     * @return
     */
    int count(String id);
}
