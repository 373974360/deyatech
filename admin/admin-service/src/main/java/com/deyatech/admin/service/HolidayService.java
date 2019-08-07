package com.deyatech.admin.service;

import com.deyatech.admin.entity.Holiday;
import com.deyatech.admin.vo.HolidayVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  节假日信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface HolidayService extends BaseService<Holiday> {


    /**
     * 批量保存节假日
     *
     * @param year
     * @param value
     * @return
     */
    boolean saveOrUpdateBatch(String year,String[] value);
    /**
     * 单个将对象转换为vo节假日信息
     *
     * @param holiday
     * @return
     */
    HolidayVo setVoProperties(Holiday holiday);

    /**
     * 批量将对象转换为vo节假日信息
     *
     * @param holidays
     * @return
     */
    List<HolidayVo> setVoProperties(Collection holidays);

    /**
     * 计算某个日期几个工作日之后的时间
     *
     * @param startTime
     * @param defaultLimit
     * @return
     */
    Date workDayAfter(Date startTime, Integer defaultLimit);

    /**
     * 计算某个日期几个工作日之后的时间
     *
     * @param startTime
     * @param limitHour
     * @return
     */
    Date workHourAfter(Date startTime, Integer limitHour);

    /**
     * 是否是节假日
     * @param date
     * @return
     */
    Boolean isHoliday(Date date);
}
