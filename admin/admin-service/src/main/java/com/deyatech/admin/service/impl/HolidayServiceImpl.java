package com.deyatech.admin.service.impl;

import cn.hutool.core.date.DateUtil;
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
import java.util.Date;
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

    /**
     * 计算某个日期几个工作日之后的时间
     *
     * @param date
     * @param day
     * @return
     */
    @Override
    public Date workDayAfter(Date date, Integer day) {
        Integer lastHoliday = 0;
        Date after = DateUtil.offsetDay(date, day);
        int hasHolidays = hasHolidays(date, after);
        for (int i = hasHolidays - lastHoliday; i > 0; ) {
            lastHoliday = hasHolidays;
            after = DateUtil.offsetDay(date, day + hasHolidays);
            hasHolidays = hasHolidays(date, after);
            i = hasHolidays - lastHoliday;
        }
        return after;
    }

    /**
     * 计算某个日期几个工作日之后的时间
     *
     * @param date
     * @param hour
     * @return
     */
    @Override
    public Date workHourAfter(Date date, Integer hour) {
        Integer lastHoliday = 0;
        Date after = DateUtil.offsetHour(date, hour);
        int hasHolidays = hasHolidays(date, after);
        for (int i = hasHolidays - lastHoliday; i > 0; ) {
            lastHoliday = hasHolidays;
            after = DateUtil.offsetHour(date, hour + (hasHolidays * 24));
            hasHolidays = hasHolidays(date, after);
            i = hasHolidays - lastHoliday;
        }
        return after;
    }

    /**
     * 是否是节假日
     * @param date
     * @return
     */
    @Override
    public Boolean isHoliday(Date date) {
        String today = DateUtil.formatDate(date);
        QueryWrapper<Holiday> queryWrapper = new QueryWrapper();
        queryWrapper.eq("date_", today)
                .eq("enable_", 1);
        Integer integer = holidayMapper.selectCount(queryWrapper);
        return integer > 0;
    }

    private int hasHolidays(Date startDate, Date endDate) {
        int total = 0;
        String startYear = DateUtil.format(startDate, "yyyy");
        String endYear = DateUtil.format(endDate, "yyyy");
        Collection<Holiday> all = new ArrayList<>();
        if (startYear.equals(endYear)) {
            all = findHolidayByYear(Integer.valueOf(startYear));
        } else {
            all.addAll(findHolidayByYear(Integer.valueOf(startYear)));
            all.addAll(findHolidayByYear(Integer.valueOf(endYear)));
        }
        //最后传入 false ，表示时分秒也进行计算，如果不够24小时不算一天,将会返回0
        long twoDate = DateUtil.betweenDay(startDate, endDate, false);
        for (Holiday holiday : all) {
            String result = holiday.getDate();
            for (int i = 0; i <= twoDate; i++) {
                String start = DateUtil.formatDate(DateUtil.offsetDay(startDate, i));
                if (result.equals(start)) {
                    total++;
                }
            }
        }
        return total;
    }

    private Collection<Holiday> findHolidayByYear(Integer year) {
        HolidayVo vo = new HolidayVo();
        vo.setYear(year.toString());
        return listByBean(vo);
    }
}
