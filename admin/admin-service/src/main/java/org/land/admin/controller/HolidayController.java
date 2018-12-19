package org.land.admin.controller;

import org.land.admin.entity.Holiday;
import org.land.admin.vo.HolidayVo;
import org.land.admin.service.HolidayService;
import org.land.common.entity.RestResult;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.web.bind.annotation.RestController;
import org.land.common.base.BaseController;

/**
 * <p>
 * 系统角色信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2018-12-19
 */
@Slf4j
@RestController
@RequestMapping("/admin/holiday")
public class HolidayController extends BaseController {
    @Autowired
    HolidayService holidayService;

    /**
     * 单个保存或者更新系统角色信息
     *
     * @param holiday
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public RestResult saveOrUpdate(Holiday holiday) {
        log.info(String.format("保存或者更新系统角色信息: %s ", JSONUtil.toJsonStr(holiday)));
        boolean result = holidayService.saveOrUpdate(holiday);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统角色信息
     *
     * @param holidayList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    public RestResult saveOrUpdateBatch(Collection<Holiday> holidayList) {
        log.info(String.format("批量保存或者更新系统角色信息: %s ", JSONUtil.toJsonStr(holidayList)));
        boolean result = holidayService.saveOrUpdateBatch(holidayList);
        return RestResult.ok(result);
    }

    /**
     * 根据Holiday对象属性逻辑删除系统角色信息
     *
     * @param holiday
     * @return
     */
    @PostMapping("/removeByHoliday")
    public RestResult removeByHoliday(Holiday holiday) {
        log.info(String.format("根据Holiday对象属性逻辑删除系统角色信息: %s ", holiday));
        boolean result = holidayService.removeByBean(holiday);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统角色信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    public RestResult removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统角色信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = holidayService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据ID获取系统角色信息
     *
     * @param holiday
     * @return
     */
    @GetMapping("/getByHoliday")
    public RestResult getByHoliday(Holiday holiday) {
        holiday = holidayService.getByBean(holiday);
        HolidayVo holidayVo = holidayService.setVoProperties(holiday);
        log.info(String.format("根据id获取系统角色信息：s%", JSONUtil.toJsonStr(holidayVo)));
        return RestResult.ok(holidayVo);
    }

    /**
     * 根据Holiday对象属性检索所有系统角色信息
     *
     * @param holiday
     * @return
     */
    @GetMapping("/listByBean")
    public RestResult listByBean(Holiday holiday) {
        Collection<Holiday> holidays = holidayService.listByBean(holiday);
        Collection<HolidayVo> holidayVos = holidayService.setVoProperties(holidays);
        log.info(String.format("根据Holiday对象属性检索所有系统角色信息: %s ",JSONUtil.toJsonStr(holidayVos)));
        return RestResult.ok(holidayVos);
    }

    /**
     * 根据Holiday对象属性分页检索系统角色信息
     *
     * @param holiday
     * @return
     */
    @GetMapping("/pageByBean")
    public RestResult pageByBean(Holiday holiday) {
        IPage holidays = holidayService.pageByBean(holiday);
        holidays.setRecords(holidayService.setVoProperties(holidays.getRecords()));
        log.info(String.format("根据Holiday对象属性分页检索系统角色信息: %s ",JSONUtil.toJsonStr(holidays)));
        return RestResult.ok(holidays);
    }

}
