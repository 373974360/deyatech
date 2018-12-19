package org.land.admin.service.impl;

import org.land.admin.entity.Holiday;
import org.land.admin.vo.HolidayVo;
import org.land.admin.mapper.HolidayMapper;
import org.land.admin.service.HolidayService;
import org.land.common.base.BaseServiceImpl;
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
 * @since 2018-12-19
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
