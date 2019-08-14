package com.deyatech.admin.mapper;

import com.deyatech.admin.entity.MetadataCollection;
import com.deyatech.admin.vo.MetadataCollectionVo;
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
public interface MetadataCollectionMapper extends BaseMapper<MetadataCollection> {

    List<MetadataCollectionVo> findAllData(MetadataCollectionVo metadataCollectionVo);

    void updateName(@Param("name") String name, @Param("enName") String enName);

    void removeMainVersion(String enName);
}
