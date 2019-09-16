package com.deyatech.workflow.util;

import java.util.List;
import java.util.Map;

public class WorkFlowUtils {

    /**
     * 根据工作流步骤获取符合条件的用户
     *
     * @param key
     * @param candidateType
     * @param groupId
     * @param data
     * @return
     */
    public static List<String> getValidUser(String key, int candidateType, String groupId, Map<String, Object> data){
        AbstractUserRange rule = UserRangeUtils.getUserRange(key);
        return rule.getValidUser(candidateType, groupId, data);
    }
}
