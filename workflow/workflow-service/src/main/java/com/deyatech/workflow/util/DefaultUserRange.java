package com.deyatech.workflow.util;

import cn.hutool.http.HttpStatus;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.enums.CandidateTypeEnum;
import com.deyatech.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DefaultUserRange extends AbstractUserRange {

    @Autowired
    private AdminFeign adminFeign;

    /**
     * 获取角色全部用户
     */
    @Override
    public List<String> getValidUser(int candidateType, String groupId, Map<String, Object> data) {
        if (CandidateTypeEnum.GROUP.getCode().equals(candidateType)) {
            RestResult<List<String>> users = adminFeign.getUserIdsByRoleId(groupId);
            if (!users.isOk()) {
                throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, String.format("根据角色id %s 获取用户id失败", groupId));
            }
            return users.getData();
        } else if (CandidateTypeEnum.DEPARTMENT.getCode().equals(candidateType)) {
            RestResult<List<String>> users = adminFeign.findUserIdsByDepartmentId(groupId);
            if (!users.isOk()) {
                throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, String.format("根据部门id %s 获取用户id失败", groupId));
            }
            return users.getData();
        }
        return null;
    }

}
