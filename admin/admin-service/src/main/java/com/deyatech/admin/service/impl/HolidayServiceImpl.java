package com.deyatech.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deyatech.admin.entity.Holiday;
import com.deyatech.admin.vo.HolidayVo;
import com.deyatech.admin.mapper.HolidayMapper;
import com.deyatech.admin.service.HolidayService;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 节假日信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
@Service
public class HolidayServiceImpl extends BaseServiceImpl<HolidayMapper, Holiday> implements HolidayService {

    @Autowired
    HolidayMapper holidayMapper;

    /**
     * 批量保存节假日
     *
     * @param year
     * @param value
     * @return
     */
    @Override
    public boolean saveOrUpdateBatch(String year, String[] value) {
        boolean result = false;
        holidayMapper.deleteHolidayByYear(year);
        List<Holiday> list = new ArrayList<>();
        if(!ArrayUtil.isEmpty(value)){
            for(String date:value){
                Holiday holiday = new Holiday();
                holiday.setYear(year);
                holiday.setDate(date);
                list.add(holiday);
            }
            result = saveOrUpdateBatch(list);
        }
        return result;
    }

    /**
     * 单个将对象转换为vo节假日信息
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
     * 批量将对象转换为vo节假日信息
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
