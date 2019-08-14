package com.deyatech.admin.mapper;

import com.deyatech.admin.entity.Metadata;
import com.deyatech.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @Author lee.
 * @since 2019-08-07
 */
public interface MetadataMapper extends BaseMapper<Metadata> {

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
    List<Metadata> findCandidateRelation(@Param("id") String id, @Param("categoryId") String categoryId);
}
