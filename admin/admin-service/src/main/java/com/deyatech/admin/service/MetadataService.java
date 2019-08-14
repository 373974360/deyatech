package com.deyatech.admin.service;

import com.deyatech.admin.entity.Metadata;
import com.deyatech.admin.vo.MetadataVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  元数据 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-08-07
 */
public interface MetadataService extends BaseService<Metadata> {

    /**
     * 单个将对象转换为vo
     *
     * @param metadata
     * @return
     */
    MetadataVo setVoProperties(Metadata metadata);

    /**
     * 批量将对象转换为vo
     *
     * @param metadatas
     * @return
     */
    List<MetadataVo> setVoProperties(Collection metadatas);

    /**
     * 保存
     *
     * @param metadataVo
     * @return
     */
    Metadata save(MetadataVo metadataVo);

    /**
     * 根据复合类型元数据id查询已关联的基本类型元数据
     *
     * @param id
     * @return
     */
    List<Metadata> findByRelationId(String id);

    /**
     * 根据复合类型元数据id取消关联已关联的基本类型元数据
     *
     * @param id
     */
    void unRelate(String id);

    /**
     * 根据复合类型元数据id和分类id查询可关联的基本类型元数据
     *
     * @param id
     * @param categoryId
     * @return
     */
    List<Metadata> findCandidateRelation(String id, String categoryId);

    /**
     * 检查中文名称是否存在
     *
     * @param id
     * @param name
     * @return
     */
    boolean checkNameExist(String id, String name);

    /**
     * 检查字段名是否存在
     *
     * @param id
     * @param briefName
     * @return
     */
    boolean checkBriefNameExist(String id, String briefName);

    /**
     * 检查英文名称是否存在
     *
     * @param id
     * @param enName
     * @return
     */
    boolean checkEnNameExist(String id, String enName);
}
