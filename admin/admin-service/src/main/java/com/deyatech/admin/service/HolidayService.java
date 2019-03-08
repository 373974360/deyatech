package com.deyatech.admin.service;

import com.deyatech.admin.entity.Holiday;
import com.deyatech.admin.vo.HolidayVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统角色信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface HolidayService extends BaseService<Holiday> {

    /**
     * 单个将对象转换为vo系统角色信息
     *
     * @param holiday
     * @return
     */
    HolidayVo setVoProperties(Holiday holiday);

    /**
     * 批量将对象转换为vo系统角色信息
     *
     * @param holidays
     * @return
     */
    List<HolidayVo> setVoProperties(Collection holidays);
}
