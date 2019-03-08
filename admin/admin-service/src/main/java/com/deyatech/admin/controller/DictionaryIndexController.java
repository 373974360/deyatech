package com.deyatech.admin.controller;

import com.deyatech.admin.entity.DictionaryIndex;
import com.deyatech.admin.vo.DictionaryIndexVo;
import com.deyatech.admin.service.DictionaryIndexService;
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
 * @since 2019-03-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/dictionaryIndex")
@Api(tags = {"系统数据字典索引信息接口"})
public class DictionaryIndexController extends BaseController {
    @Autowired
    DictionaryIndexService dictionaryIndexService;

    /**
     * 单个保存或者更新系统数据字典索引信息
     *
     * @param dictionaryIndex
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新系统数据字典索引信息", notes="根据系统数据字典索引信息对象保存或者更新系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictionaryIndex", value = "系统数据字典索引信息对象", required = true, dataType = "DictionaryIndex", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(DictionaryIndex dictionaryIndex) {
        log.info(String.format("保存或者更新系统数据字典索引信息: %s ", JSONUtil.toJsonStr(dictionaryIndex)));
        boolean result = dictionaryIndexService.saveOrUpdate(dictionaryIndex);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统数据字典索引信息
     *
     * @param dictionaryIndexList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统数据字典索引信息", notes="根据系统数据字典索引信息对象集合批量保存或者更新系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictionaryIndexList", value = "系统数据字典索引信息对象集合", required = true, allowMultiple = true, dataType = "DictionaryIndex", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<DictionaryIndex> dictionaryIndexList) {
        log.info(String.format("批量保存或者更新系统数据字典索引信息: %s ", JSONUtil.toJsonStr(dictionaryIndexList)));
        boolean result = dictionaryIndexService.saveOrUpdateBatch(dictionaryIndexList);
        return RestResult.ok(result);
    }

    /**
     * 根据DictionaryIndex对象属性逻辑删除系统数据字典索引信息
     *
     * @param dictionaryIndex
     * @return
     */
    @PostMapping("/removeByDictionaryIndex")
    @ApiOperation(value="根据DictionaryIndex对象属性逻辑删除系统数据字典索引信息", notes="根据系统数据字典索引信息对象逻辑删除系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictionaryIndex", value = "系统数据字典索引信息对象", required = true, dataType = "DictionaryIndex", paramType = "query")
    public RestResult<Boolean> removeByDictionaryIndex(DictionaryIndex dictionaryIndex) {
        log.info(String.format("根据DictionaryIndex对象属性逻辑删除系统数据字典索引信息: %s ", dictionaryIndex));
        boolean result = dictionaryIndexService.removeByBean(dictionaryIndex);
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
        boolean result = dictionaryIndexService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据DictionaryIndex对象属性获取系统数据字典索引信息
     *
     * @param dictionaryIndex
     * @return
     */
    @GetMapping("/getByDictionaryIndex")
    @ApiOperation(value="根据DictionaryIndex对象属性获取系统数据字典索引信息", notes="根据系统数据字典索引信息对象属性获取系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictionaryIndex", value = "系统数据字典索引信息对象", required = false, dataType = "DictionaryIndex", paramType = "query")
    public RestResult<DictionaryIndexVo> getByDictionaryIndex(DictionaryIndex dictionaryIndex) {
        dictionaryIndex = dictionaryIndexService.getByBean(dictionaryIndex);
        DictionaryIndexVo dictionaryIndexVo = dictionaryIndexService.setVoProperties(dictionaryIndex);
        log.info(String.format("根据id获取系统数据字典索引信息：s%", JSONUtil.toJsonStr(dictionaryIndexVo)));
        return RestResult.ok(dictionaryIndexVo);
    }

    /**
     * 根据DictionaryIndex对象属性检索所有系统数据字典索引信息
     *
     * @param dictionaryIndex
     * @return
     */
    @GetMapping("/listByDictionaryIndex")
    @ApiOperation(value="根据DictionaryIndex对象属性检索所有系统数据字典索引信息", notes="根据DictionaryIndex对象属性检索所有系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictionaryIndex", value = "系统数据字典索引信息对象", required = false, dataType = "DictionaryIndex", paramType = "query")
    public RestResult<Collection<DictionaryIndexVo>> listByDictionaryIndex(DictionaryIndex dictionaryIndex) {
        Collection<DictionaryIndex> dictionaryIndexs = dictionaryIndexService.listByBean(dictionaryIndex);
        Collection<DictionaryIndexVo> dictionaryIndexVos = dictionaryIndexService.setVoProperties(dictionaryIndexs);
        log.info(String.format("根据DictionaryIndex对象属性检索所有系统数据字典索引信息: %s ",JSONUtil.toJsonStr(dictionaryIndexVos)));
        return RestResult.ok(dictionaryIndexVos);
    }

    /**
     * 根据DictionaryIndex对象属性分页检索系统数据字典索引信息
     *
     * @param dictionaryIndex
     * @return
     */
    @GetMapping("/pageByDictionaryIndex")
    @ApiOperation(value="根据DictionaryIndex对象属性分页检索系统数据字典索引信息", notes="根据DictionaryIndex对象属性分页检索系统数据字典索引信息信息")
    @ApiImplicitParam(name = "dictionaryIndex", value = "系统数据字典索引信息对象", required = false, dataType = "DictionaryIndex", paramType = "query")
    public RestResult<IPage<DictionaryIndexVo>> pageByDictionaryIndex(DictionaryIndex dictionaryIndex) {
        IPage<DictionaryIndexVo> dictionaryIndexs = dictionaryIndexService.pageByBean(dictionaryIndex);
        dictionaryIndexs.setRecords(dictionaryIndexService.setVoProperties(dictionaryIndexs.getRecords()));
        log.info(String.format("根据DictionaryIndex对象属性分页检索系统数据字典索引信息: %s ",JSONUtil.toJsonStr(dictionaryIndexs)));
        return RestResult.ok(dictionaryIndexs);
    }

}