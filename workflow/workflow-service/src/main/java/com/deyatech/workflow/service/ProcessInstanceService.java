package com.deyatech.workflow.service;

import com.deyatech.common.enums.ProcessInstanceStatusEnum;
import com.deyatech.workflow.vo.ProcessInstanceVo;

/**
 * @author doukang
 * @description TODO
 * @date 2019/8/6 10:51
 */
public interface ProcessInstanceService {

    /**
     * 启动流程实例
     *
     * @param processInstanceVo
     * @return
     */
    ProcessInstanceStatusEnum startInstance(ProcessInstanceVo processInstanceVo);
}
