package com.deyatech.workflow.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.workflow.entity.ProcessModel;
import com.deyatech.common.base.BaseMapper;
import com.deyatech.workflow.vo.ProcessModelVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 流程模型 Mapper 接口
 * </p>
 *
 * @Author lee.
 * @since 2019-07-31
 */
public interface ProcessModelMapper extends BaseMapper<ProcessModel> {

    IPage<ProcessModelVo> findLastVersionPage(@Param("page") IPage<ProcessModel> page, @Param("processModel") ProcessModel processModel);

    ProcessModel getLastByActModelId(String actModelId);
}
