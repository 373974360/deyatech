package com.deyatech.workflow.service;

import com.deyatech.common.base.BaseService;
import com.deyatech.workflow.entity.BusinessStash;

import java.util.Collection;
import java.util.List;

public interface BusinessStashService extends BaseService<BusinessStash> {
    /**
     * 查绚
     *
     * @return
     */
    Collection<BusinessStash> getBusinessStashList(BusinessStash businessStash);

    /**
     * 删除
     *
     * @param ids
     */
    boolean removeBusinessStashByIds(List<String> ids);
}
