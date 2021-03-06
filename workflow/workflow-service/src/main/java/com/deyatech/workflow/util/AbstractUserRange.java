package com.deyatech.workflow.util;

import java.util.List;
import java.util.Map;

public abstract class AbstractUserRange {

    /**
     * 获取复合规则的用户
     * 
     * @param data
     * @return
     */
    public abstract List<String> getValidUser(int candidateType, String groupId, Map<String, Object> data);
}
