package org.land.admin.service;

import org.land.admin.entity.Holiday;
import org.land.admin.vo.HolidayVo;
import org.land.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统角色信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2018-12-19
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
