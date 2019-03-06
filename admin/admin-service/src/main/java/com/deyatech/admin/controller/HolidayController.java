package com.deyatech.admin.controller;

import com.deyatech.admin.entity.Holiday;
import com.deyatech.admin.vo.HolidayVo;
import com.deyatech.admin.service.HolidayService;
import com.deyatech.common.entity.RestResult;
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
import com.deyatech.common.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统角色信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-03-06
 */
@Slf4j
@RestController
@RequestMapping("/admin/holiday")
@Api(tags = {"系统角色信息接口"})
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
    @ApiOperation(value="单个保存或者更新系统角色信息", notes="根据系统角色信息对象保存或者更新系统角色信息信息")
    @ApiImplicitParam(name = "holiday", value = "系统角色信息对象", required = true, dataType = "Holiday", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(Holiday holiday) {
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
    @ApiOperation(value="批量保存或者更新系统角色信息", notes="根据系统角色信息对象集合批量保存或者更新系统角色信息信息")
    @ApiImplicitParam(name = "holidayList", value = "系统角色信息对象集合", required = true, allowMultiple = true, dataType = "Holiday", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<Holiday> holidayList) {
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
    @ApiOperation(value="根据Holiday对象属性逻辑删除系统角色信息", notes="根据系统角色信息对象逻辑删除系统角色信息信息")
    @ApiImplicitParam(name = "holiday", value = "系统角色信息对象", required = true, dataType = "Holiday", paramType = "query")
    public RestResult<Boolean> removeByHoliday(Holiday holiday) {
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
    @ApiOperation(value="根据ID批量逻辑删除系统角色信息", notes="根据系统角色信息对象ID批量逻辑删除系统角色信息信息")
    @ApiImplicitParam(name = "ids", value = "系统角色信息对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统角色信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = holidayService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Holiday对象属性获取系统角色信息
     *
     * @param holiday
     * @return
     */
    @GetMapping("/getByHoliday")
    @ApiOperation(value="根据Holiday对象属性获取系统角色信息", notes="根据系统角色信息对象属性获取系统角色信息信息")
    @ApiImplicitParam(name = "holiday", value = "系统角色信息对象", required = false, dataType = "Holiday", paramType = "query")
    public RestResult<HolidayVo> getByHoliday(Holiday holiday) {
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
    @GetMapping("/listByHoliday")
    @ApiOperation(value="根据Holiday对象属性检索所有系统角色信息", notes="根据Holiday对象属性检索所有系统角色信息信息")
    @ApiImplicitParam(name = "holiday", value = "系统角色信息对象", required = false, dataType = "Holiday", paramType = "query")
    public RestResult<Collection<HolidayVo>> listByHoliday(Holiday holiday) {
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
    @GetMapping("/pageByHoliday")
    @ApiOperation(value="根据Holiday对象属性分页检索系统角色信息", notes="根据Holiday对象属性分页检索系统角色信息信息")
    @ApiImplicitParam(name = "holiday", value = "系统角色信息对象", required = false, dataType = "Holiday", paramType = "query")
    public RestResult<IPage<HolidayVo>> pageByHoliday(Holiday holiday) {
        IPage<HolidayVo> holidays = holidayService.pageByBean(holiday);
        holidays.setRecords(holidayService.setVoProperties(holidays.getRecords()));
        log.info(String.format("根据Holiday对象属性分页检索系统角色信息: %s ",JSONUtil.toJsonStr(holidays)));
        return RestResult.ok(holidays);
    }

}
