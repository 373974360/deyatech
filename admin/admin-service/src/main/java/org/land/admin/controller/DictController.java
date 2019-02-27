package org.land.admin.controller;

import org.land.admin.entity.Dict;
import org.land.admin.vo.DictVo;
import org.land.admin.service.DictService;
import org.land.common.entity.RestResult;
import cn.hutool.core.lang.Assert;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统数据字典明细信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-02-27
 */
@Slf4j
@RestController
@RequestMapping("/admin/dict")
@Api(tags = {"系统数据字典明细信息接口"})
public class DictController extends BaseController {
    @Autowired
    DictService dictService;

    /**
     * 单个保存或者更新系统数据字典明细信息
     *
     * @param dict
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新系统数据字典明细信息", notes="根据系统数据字典明细信息对象保存或者更新系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dict", value = "系统数据字典明细信息对象", required = true, dataType = "Dict", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(Dict dict) {
        Assert.notNull(dict);
        log.info(String.format("保存或者更新系统数据字典明细信息: %s ", JSONUtil.toJsonStr(dict)));
        boolean result = dictService.saveOrUpdate(dict);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统数据字典明细信息
     *
     * @param dictList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统数据字典明细信息", notes="根据系统数据字典明细信息对象集合批量保存或者更新系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dictList", value = "系统数据字典明细信息对象集合", required = true, allowMultiple = true, dataType = "Dict", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<Dict> dictList) {
        Assert.notNull(dictList);
        log.info(String.format("批量保存或者更新系统数据字典明细信息: %s ", JSONUtil.toJsonStr(dictList)));
        boolean result = dictService.saveOrUpdateBatch(dictList);
        return RestResult.ok(result);
    }

    /**
     * 根据Dict对象属性逻辑删除系统数据字典明细信息
     *
     * @param dict
     * @return
     */
    @PostMapping("/removeByDict")
    @ApiOperation(value="根据Dict对象属性逻辑删除系统数据字典明细信息", notes="根据系统数据字典明细信息对象逻辑删除系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dict", value = "系统数据字典明细信息对象", required = true, dataType = "Dict", paramType = "query")
    public RestResult<Boolean> removeByDict(Dict dict) {
        Assert.notNull(dict);
        log.info(String.format("根据Dict对象属性逻辑删除系统数据字典明细信息: %s ", dict));
        boolean result = dictService.removeByBean(dict);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统数据字典明细信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除系统数据字典明细信息", notes="根据系统数据字典明细信息对象ID批量逻辑删除系统数据字典明细信息信息")
    @ApiImplicitParam(name = "ids", value = "系统数据字典明细信息对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(Collection<Serializable> ids) {
        Assert.notNull(ids);
        log.info(String.format("根据id批量删除系统数据字典明细信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = dictService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Dict对象属性获取系统数据字典明细信息
     *
     * @param dict
     * @return
     */
    @GetMapping("/getByDict")
    @ApiOperation(value="根据Dict对象属性获取系统数据字典明细信息", notes="根据系统数据字典明细信息对象属性获取系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dict", value = "系统数据字典明细信息对象", required = false, dataType = "Dict", paramType = "query")
    public RestResult<DictVo> getByDict(Dict dict) {
        dict = dictService.getByBean(dict);
        DictVo dictVo = dictService.setVoProperties(dict);
        log.info(String.format("根据id获取系统数据字典明细信息：s%", JSONUtil.toJsonStr(dictVo)));
        return RestResult.ok(dictVo);
    }

    /**
     * 根据Dict对象属性检索所有系统数据字典明细信息
     *
     * @param dict
     * @return
     */
    @GetMapping("/listByBean")
    @ApiOperation(value="根据Dict对象属性检索所有系统数据字典明细信息", notes="根据Dict对象属性检索所有系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dict", value = "系统数据字典明细信息对象", required = false, dataType = "Dict", paramType = "query")
    public RestResult<Collection<DictVo>> listByBean(Dict dict) {
        Collection<Dict> dicts = dictService.listByBean(dict);
        Collection<DictVo> dictVos = dictService.setVoProperties(dicts);
        log.info(String.format("根据Dict对象属性检索所有系统数据字典明细信息: %s ",JSONUtil.toJsonStr(dictVos)));
        return RestResult.ok(dictVos);
    }

    /**
     * 根据Dict对象属性分页检索系统数据字典明细信息
     *
     * @param dict
     * @return
     */
    @GetMapping("/pageByBean")
    @ApiOperation(value="根据Dict对象属性分页检索系统数据字典明细信息", notes="根据Dict对象属性分页检索系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dict", value = "系统数据字典明细信息对象", required = false, dataType = "Dict", paramType = "query")
    public RestResult<IPage<DictVo>> pageByBean(Dict dict) {
        IPage<DictVo> dicts = dictService.pageByBean(dict);
        dicts.setRecords(dictService.setVoProperties(dicts.getRecords()));
        log.info(String.format("根据Dict对象属性分页检索系统数据字典明细信息: %s ",JSONUtil.toJsonStr(dicts)));
        return RestResult.ok(dicts);
    }

}
