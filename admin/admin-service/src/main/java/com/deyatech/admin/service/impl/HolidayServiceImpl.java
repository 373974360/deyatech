package com.deyatech.admin.service.impl;

import com.deyatech.admin.entity.Holiday;
import com.deyatech.admin.vo.HolidayVo;
import com.deyatech.admin.mapper.HolidayMapper;
import com.deyatech.admin.service.HolidayService;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统角色信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
@Service
public class HolidayServiceImpl extends BaseServiceImpl<HolidayMapper, Holiday> implements HolidayService {

    /**
     * 单个将对象转换为vo系统角色信息
     *
     * @param holiday
     * @return
     */
    @Override
    public HolidayVo setVoProperties(Holiday holiday){
        HolidayVo holidayVo = new HolidayVo();
        BeanUtil.copyProperties(holiday, holidayVo);
        return holidayVo;
    }

    /**
     * 批量将对象转换为vo系统角色信息
     *
     * @param holidays
     * @return
     */
    @Override
    public List<HolidayVo> setVoProperties(Collection holidays){
        List<HolidayVo> holidayVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(holidays)) {
            for (Object holiday : holidays) {
                HolidayVo holidayVo = new HolidayVo();
                BeanUtil.copyProperties(holiday, holidayVo);
                holidayVos.add(holidayVo);
            }
        }
        return holidayVos;
    }
}
