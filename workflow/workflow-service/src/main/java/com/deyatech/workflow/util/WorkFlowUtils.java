package com.deyatech.workflow.util;

import java.util.List;
import java.util.Map;

public class WorkFlowUtils {

    /**
     * 根据工作流步骤获取符合条件的用户
     *
     * @param key
     * @param roleId
     * @param data
     * @return
     */
    public static List<String> getValidUser(String key, String roleId, Map<String, Object> data){
        AbstractUserRange rule = UserRangeUtils.getUserRange(key);
        return rule.getValidUser(roleId, data);
    }
}
