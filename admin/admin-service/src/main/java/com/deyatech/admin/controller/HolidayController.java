package com.deyatech.admin.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.Holiday;
import com.deyatech.admin.service.HolidayService;
import com.deyatech.admin.vo.HolidayVo;
import com.deyatech.common.base.BaseController;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.log.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 节假日信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-03-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/holiday")
@Api(tags = {"节假日信息接口"})
public class HolidayController extends BaseController {
    @Autowired
    HolidayService holidayService;

    /**
     * 单个保存或者更新节假日信息
     *
     * @param holiday
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新节假日信息", notes="根据节假日信息对象保存或者更新节假日信息信息")
    @ApiImplicitParam(name = "holiday", value = "节假日信息对象", required = true, dataType = "Holiday", paramType = "query")
    @SysLog(module = "节假日",notes = "单个保存或者更新节假日信息")
    public RestResult<Boolean> saveOrUpdate(@RequestBody Holiday holiday) {
        log.info(String.format("保存或者更新节假日信息: %s ", JSONUtil.toJsonStr(holiday)));
        boolean result = holidayService.saveOrUpdate(holiday);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新节假日信息
     *
     * @param year
     * @param value
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新节假日信息", notes="根据节假日信息对象集合批量保存或者更新节假日信息信息")
    @ApiImplicitParam(name = "holidayList", value = "节假日信息对象集合", required = true, allowMultiple = true, dataType = "Holiday", paramType = "query")
    @SysLog(module = "节假日",notes = "批量保存或者更新节假日信息")
    public RestResult<Boolean> saveOrUpdateBatch(String year,@RequestParam(value="value[]") String[] value) {
        log.info(String.format("批量保存或者更新节假日信息: %s ", JSONUtil.toJsonStr(year+""+value)));
        boolean result = holidayService.saveOrUpdateBatch(year,value);
        return RestResult.ok(result);
    }

    /**
     * 根据Holiday对象属性逻辑删除节假日信息
     *
     * @param holiday
     * @return
     */
    @PostMapping("/removeByHoliday")
    @ApiOperation(value="根据Holiday对象属性逻辑删除节假日信息", notes="根据节假日信息对象逻辑删除节假日信息信息")
    @ApiImplicitParam(name = "holiday", value = "节假日信息对象", required = true, dataType = "Holiday", paramType = "query")
    @SysLog(module = "节假日",notes = "根据Holiday对象属性逻辑删除节假日信息")
    public RestResult<Boolean> removeByHoliday(Holiday holiday) {
        log.info(String.format("根据Holiday对象属性逻辑删除节假日信息: %s ", holiday));
        boolean result = holidayService.removeByBean(holiday);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除节假日信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除节假日信息", notes="根据节假日信息对象ID批量逻辑删除节假日信息信息")
    @ApiImplicitParam(name = "ids", value = "节假日信息对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    @SysLog(module = "节假日",notes = "根据ID批量逻辑删除节假日信息")
    public RestResult<Boolean> removeByIds(@RequestBody List<String> ids) {
        log.info(String.format("根据id批量删除节假日信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = holidayService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Holiday对象属性获取节假日信息
     *
     * @param holiday
     * @return
     */
    @GetMapping("/getByHoliday")
    @ApiOperation(value="根据Holiday对象属性获取节假日信息", notes="根据节假日信息对象属性获取节假日信息信息")
    @ApiImplicitParam(name = "holiday", value = "节假日信息对象", required = false, dataType = "Holiday", paramType = "query")
    public RestResult<HolidayVo> getByHoliday(Holiday holiday) {
        holiday = holidayService.getByBean(holiday);
        HolidayVo holidayVo = holidayService.setVoProperties(holiday);
        log.info(String.format("根据id获取节假日信息：s%", JSONUtil.toJsonStr(holidayVo)));
        return RestResult.ok(holidayVo);
    }

    /**
     * 根据Holiday对象属性检索所有节假日信息
     *
     * @param holiday
     * @return
     */
    @GetMapping("/listByHoliday")
    @ApiOperation(value="根据Holiday对象属性检索所有节假日信息", notes="根据Holiday对象属性检索所有节假日信息信息")
    @ApiImplicitParam(name = "holiday", value = "节假日信息对象", required = false, dataType = "Holiday", paramType = "query")
    public RestResult<Collection<HolidayVo>> listByHoliday(Holiday holiday) {
        Collection<Holiday> holidays = holidayService.listByBean(holiday);
        Collection<HolidayVo> holidayVos = holidayService.setVoProperties(holidays);
        log.info(String.format("根据Holiday对象属性检索所有节假日信息: %s ",JSONUtil.toJsonStr(holidayVos)));
        return RestResult.ok(holidayVos);
    }

    /**
     * 根据Holiday对象属性分页检索节假日信息
     *
     * @param holiday
     * @return
     */
    @GetMapping("/pageByHoliday")
    @ApiOperation(value="根据Holiday对象属性分页检索节假日信息", notes="根据Holiday对象属性分页检索节假日信息信息")
    @ApiImplicitParam(name = "holiday", value = "节假日信息对象", required = false, dataType = "Holiday", paramType = "query")
    public RestResult<IPage<HolidayVo>> pageByHoliday(Holiday holiday) {
        IPage<HolidayVo> holidays = holidayService.pageByBean(holiday);
        holidays.setRecords(holidayService.setVoProperties(holidays.getRecords()));
        log.info(String.format("根据Holiday对象属性分页检索节假日信息: %s ",JSONUtil.toJsonStr(holidays)));
        return RestResult.ok(holidays);
    }

}
