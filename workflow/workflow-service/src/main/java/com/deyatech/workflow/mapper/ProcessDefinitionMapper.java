package com.deyatech.workflow.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.workflow.entity.IProcessDefinition;
import com.deyatech.common.base.BaseMapper;
import com.deyatech.workflow.vo.ProcessDefinitionVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 流程定义 Mapper 接口
 * </p>
 *
 * @Author lee.
 * @since 2019-07-31
 */
public interface ProcessDefinitionMapper extends BaseMapper<IProcessDefinition> {

    IPage<ProcessDefinitionVo> findLastVersionPage(@Param("page") IPage<IProcessDefinition> page, @Param("processDefinition") IProcessDefinition processDefinition);

    IProcessDefinition getLastVersionByActDefinitionKey(@Param("actDefinitionKey") String actDefinitionKey, @Param("version") int version);

    void enableByActDefinitionKey(@Param("actDefinitionKey") String actDefinitionKey);

    void disableByActDefinitionKey(@Param("actDefinitionKey") String actDefinitionKey);
}
