package com.deyatech.workflow.util;

import cn.hutool.http.HttpStatus;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.common.entity.RestResult;
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
    public List<String> getValidUser(String roleId, Map<String, Object> data) {
        RestResult<List<String>> users = adminFeign.getUserIdsByRoleId(roleId);
        if (!users.isOk()) {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, String.format("根据角色id %s 或者用户id失败", roleId));
        }
        return users.getData();
    }

}
