package com.deyatech.admin.mapper;

import com.deyatech.admin.entity.Holiday;
import com.deyatech.common.base.BaseMapper;

/**
 * <p>
 * 节假日信息 Mapper 接口
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface HolidayMapper extends BaseMapper<Holiday> {

    /**
     * 根据年份物理删除节假日
     *
     * @param year
     * @return
     */
    void deleteHolidayByYear(String year);
}
