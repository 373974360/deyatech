package com.deyatech.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deyatech.admin.entity.Logs;
import com.deyatech.admin.vo.LogsVo;
import com.deyatech.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统数据字典索引信息 Mapper 接口
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface LogsMapper extends BaseMapper<Logs> {

    /**
     * 根据Logs对象属性分页检索系统日志信息
     *
     * @param logs
     * @return
     */
    IPage<LogsVo> selectListByBean(Page<Logs> page,@Param("logs") Logs logs);
}
