package com.deyatech.workflow.service.impl;

import com.deyatech.common.base.BaseServiceImpl;
import com.deyatech.workflow.entity.BusinessStash;
import com.deyatech.workflow.mapper.BusinessStashMapper;
import com.deyatech.workflow.service.BusinessStashService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class BusinessStashServiceImpl extends BaseServiceImpl<BusinessStashMapper, BusinessStash> implements BusinessStashService {

    /**
     * 查绚
     *
     * @return
     */
    @Override
    public Collection<BusinessStash> getBusinessStashList(BusinessStash businessStash) {
        return super.listByBean(businessStash);
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public boolean removeBusinessStashByIds(List<String> ids) {
        return super.removeByIds(ids);
    }
}
