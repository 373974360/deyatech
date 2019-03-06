package com.deyatech.admin.controller;

import com.deyatech.admin.entity.DictIndex;
import com.deyatech.admin.vo.DictIndexVo;
import com.deyatech.admin.service.DictIndexService;
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
 * 系统数据字典索引信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-03-06
 */
@Slf4j
@RestController
@RequestMapping("/admin/dictIndex")
@Api(tags = {"系统数据字典索引信息接口"})
public class DictIndexController extends BaseController {
    @Autowired
    DictIndexService dictIndexService;

    /**
     * 单个保存或者更新系统数据字典索引信息
     *
     * @param dictIndex
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新系统数据字典索引信息", notes="根据系统数据字典索引信息对象保存或者更新系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictIndex", value = "系统数据字典索引信息对象", required = true, dataType = "DictIndex", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(DictIndex dictIndex) {
        log.info(String.format("保存或者更新系统数据字典索引信息: %s ", JSONUtil.toJsonStr(dictIndex)));
        boolean result = dictIndexService.saveOrUpdate(dictIndex);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统数据字典索引信息
     *
     * @param dictIndexList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统数据字典索引信息", notes="根据系统数据字典索引信息对象集合批量保存或者更新系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictIndexList", value = "系统数据字典索引信息对象集合", required = true, allowMultiple = true, dataType = "DictIndex", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<DictIndex> dictIndexList) {
        log.info(String.format("批量保存或者更新系统数据字典索引信息: %s ", JSONUtil.toJsonStr(dictIndexList)));
        boolean result = dictIndexService.saveOrUpdateBatch(dictIndexList);
        return RestResult.ok(result);
    }

    /**
     * 根据DictIndex对象属性逻辑删除系统数据字典索引信息
     *
     * @param dictIndex
     * @return
     */
    @PostMapping("/removeByDictIndex")
    @ApiOperation(value="根据DictIndex对象属性逻辑删除系统数据字典索引信息", notes="根据系统数据字典索引信息对象逻辑删除系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictIndex", value = "系统数据字典索引信息对象", required = true, dataType = "DictIndex", paramType = "query")
    public RestResult<Boolean> removeByDictIndex(DictIndex dictIndex) {
        log.info(String.format("根据DictIndex对象属性逻辑删除系统数据字典索引信息: %s ", dictIndex));
        boolean result = dictIndexService.removeByBean(dictIndex);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统数据字典索引信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除系统数据字典索引信息", notes="根据系统数据字典索引信息对象ID批量逻辑删除系统数据字典索引信息信息")
    @ApiImplicitParam(name = "ids", value = "系统数据字典索引信息对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统数据字典索引信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = dictIndexService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据DictIndex对象属性获取系统数据字典索引信息
     *
     * @param dictIndex
     * @return
     */
    @GetMapping("/getByDictIndex")
    @ApiOperation(value="根据DictIndex对象属性获取系统数据字典索引信息", notes="根据系统数据字典索引信息对象属性获取系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictIndex", value = "系统数据字典索引信息对象", required = false, dataType = "DictIndex", paramType = "query")
    public RestResult<DictIndexVo> getByDictIndex(DictIndex dictIndex) {
        dictIndex = dictIndexService.getByBean(dictIndex);
        DictIndexVo dictIndexVo = dictIndexService.setVoProperties(dictIndex);
        log.info(String.format("根据id获取系统数据字典索引信息：s%", JSONUtil.toJsonStr(dictIndexVo)));
        return RestResult.ok(dictIndexVo);
    }

    /**
     * 根据DictIndex对象属性检索所有系统数据字典索引信息
     *
     * @param dictIndex
     * @return
     */
    @GetMapping("/listByDictIndex")
    @ApiOperation(value="根据DictIndex对象属性检索所有系统数据字典索引信息", notes="根据DictIndex对象属性检索所有系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictIndex", value = "系统数据字典索引信息对象", required = false, dataType = "DictIndex", paramType = "query")
    public RestResult<Collection<DictIndexVo>> listByDictIndex(DictIndex dictIndex) {
        Collection<DictIndex> dictIndexs = dictIndexService.listByBean(dictIndex);
        Collection<DictIndexVo> dictIndexVos = dictIndexService.setVoProperties(dictIndexs);
        log.info(String.format("根据DictIndex对象属性检索所有系统数据字典索引信息: %s ",JSONUtil.toJsonStr(dictIndexVos)));
        return RestResult.ok(dictIndexVos);
    }

    /**
     * 根据DictIndex对象属性分页检索系统数据字典索引信息
     *
     * @param dictIndex
     * @return
     */
    @GetMapping("/pageByDictIndex")
    @ApiOperation(value="根据DictIndex对象属性分页检索系统数据字典索引信息", notes="根据DictIndex对象属性分页检索系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictIndex", value = "系统数据字典索引信息对象", required = false, dataType = "DictIndex", paramType = "query")
    public RestResult<IPage<DictIndexVo>> pageByDictIndex(DictIndex dictIndex) {
        IPage<DictIndexVo> dictIndexs = dictIndexService.pageByBean(dictIndex);
        dictIndexs.setRecords(dictIndexService.setVoProperties(dictIndexs.getRecords()));
        log.info(String.format("根据DictIndex对象属性分页检索系统数据字典索引信息: %s ",JSONUtil.toJsonStr(dictIndexs)));
        return RestResult.ok(dictIndexs);
    }

}
