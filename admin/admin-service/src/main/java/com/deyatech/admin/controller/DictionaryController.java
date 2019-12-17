package com.deyatech.admin.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.Dictionary;
import com.deyatech.admin.service.DictionaryService;
import com.deyatech.admin.vo.DictionaryVo;
import com.deyatech.common.base.BaseController;
import com.deyatech.common.entity.CascaderResult;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.utils.CascaderUtil;
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
 * 系统数据字典明细信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-03-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/dictionary")
@Api(tags = {"系统数据字典明细信息接口"})
public class DictionaryController extends BaseController {
    @Autowired
    DictionaryService dictionaryService;

    /**
     * 单个保存或者更新系统数据字典明细信息
     *
     * @param dictionary
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新系统数据字典明细信息", notes="根据系统数据字典明细信息对象保存或者更新系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dictionary", value = "系统数据字典明细信息对象", required = true, dataType = "Dictionary", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(Dictionary dictionary) {
        log.info(String.format("保存或者更新系统数据字典明细信息: %s ", JSONUtil.toJsonStr(dictionary)));
        if(!dictionaryService.validataByCodeAndIndexId(dictionary)){
            return RestResult.error("已存在相同的字典项目，请重新提交！");
        }
        if (StrUtil.isEmpty(dictionary.getParentId())) {
            dictionary.setParentId("0");
            dictionary.setTreePosition("&");
        }

        boolean result = dictionaryService.saveOrUpdate(dictionary);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统数据字典明细信息
     *
     * @param dictionaryList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统数据字典明细信息", notes="根据系统数据字典明细信息对象集合批量保存或者更新系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dictionaryList", value = "系统数据字典明细信息对象集合", required = true, allowMultiple = true, dataType = "Dictionary", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<Dictionary> dictionaryList) {
        log.info(String.format("批量保存或者更新系统数据字典明细信息: %s ", JSONUtil.toJsonStr(dictionaryList)));
        if (CollectionUtil.isNotEmpty(dictionaryList)) {
            dictionaryList.stream().forEach(d -> {
                if (StrUtil.isEmpty(d.getParentId())) {
                    d.setParentId("0");
                    d.setTreePosition("&");
                }
            });
        }
        boolean result = dictionaryService.saveOrUpdateBatch(dictionaryList);
        return RestResult.ok(result);
    }

    /**
     * 根据Dictionary对象属性逻辑删除系统数据字典明细信息
     *
     * @param dictionary
     * @return
     */
    @PostMapping("/removeByDictionary")
    @ApiOperation(value="根据Dictionary对象属性逻辑删除系统数据字典明细信息", notes="根据系统数据字典明细信息对象逻辑删除系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dictionary", value = "系统数据字典明细信息对象", required = true, dataType = "Dictionary", paramType = "query")
    public RestResult<Boolean> removeByDictionary(Dictionary dictionary) {
        log.info(String.format("根据Dictionary对象属性逻辑删除系统数据字典明细信息: %s ", dictionary));
        boolean result = dictionaryService.removeByBean(dictionary);
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
    public RestResult<Boolean> removeByIds(@RequestParam(value="ids[]") List<String> ids) {
        log.info(String.format("根据id批量删除系统数据字典明细信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = dictionaryService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Dictionary对象属性获取系统数据字典明细信息
     *
     * @param dictionary
     * @return
     */
    @GetMapping("/getByDictionary")
    @ApiOperation(value="根据Dictionary对象属性获取系统数据字典明细信息", notes="根据系统数据字典明细信息对象属性获取系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dictionary", value = "系统数据字典明细信息对象", required = false, dataType = "Dictionary", paramType = "query")
    public RestResult<DictionaryVo> getByDictionary(Dictionary dictionary) {
        dictionary = dictionaryService.getByBean(dictionary);
        DictionaryVo dictionaryVo = dictionaryService.setVoProperties(dictionary);
        log.info(String.format("根据id获取系统数据字典明细信息：%s", JSONUtil.toJsonStr(dictionaryVo)));
        return RestResult.ok(dictionaryVo);
    }

    /**
     * 根据Dictionary对象属性检索所有系统数据字典明细信息
     *
     * @param dictionary
     * @return
     */
    @GetMapping("/listByDictionary")
    @ApiOperation(value="根据Dictionary对象属性检索所有系统数据字典明细信息", notes="根据Dictionary对象属性检索所有系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dictionary", value = "系统数据字典明细信息对象", required = false, dataType = "Dictionary", paramType = "query")
    public RestResult<Collection<DictionaryVo>> listByDictionary(Dictionary dictionary) {
        dictionary.setSortSql("sort_no asc");
        Collection<Dictionary> dictionarys = dictionaryService.listByBean(dictionary);
        Collection<DictionaryVo> dictionaryVos = dictionaryService.setVoProperties(dictionarys);
        log.info(String.format("根据Dictionary对象属性检索所有系统数据字典明细信息: %s ",JSONUtil.toJsonStr(dictionaryVos)));
        return RestResult.ok(dictionaryVos);
    }

    /**
     * 根据Dictionary对象属性分页检索系统数据字典明细信息
     *
     * @param dictionary
     * @return
     */
    @GetMapping("/pageByDictionary")
    @ApiOperation(value="根据Dictionary对象属性分页检索系统数据字典明细信息", notes="根据Dictionary对象属性分页检索系统数据字典明细信息信息")
    @ApiImplicitParam(name = "dictionary", value = "系统数据字典明细信息对象", required = false, dataType = "Dictionary", paramType = "query")
    public RestResult<IPage<DictionaryVo>> pageByDictionary(Dictionary dictionary) {
        IPage<DictionaryVo> dictionarys = dictionaryService.pageByBean(dictionary);
        dictionarys.setRecords(dictionaryService.setVoProperties(dictionarys.getRecords()));
        log.info(String.format("根据Dictionary对象属性分页检索系统数据字典明细信息: %s ",JSONUtil.toJsonStr(dictionarys)));
        return RestResult.ok(dictionarys);
    }

    /**
     * 下一个排序号
     *
     * @return
     */
    @RequestMapping("/getNextSortNo")
    @ApiOperation(value = "下一个排序号", notes = "下一个排序号")
    public RestResult<Integer> getNextSortNo(String indexId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("index_id", indexId);
        queryWrapper.select("ifnull(max(sort_no), 0) + 1 as sortNo");
        return RestResult.ok(dictionaryService.getMap(queryWrapper).get("sortNo"));
    }

    /**
     * 获取的tree对象
     *
     * @param dictionary
     * @return
     */
    @GetMapping("/getTree")
    @ApiOperation(value="获取的tree对象", notes="获取的tree对象")
    public RestResult<Collection<DictionaryVo>> getDictionaryTree(Dictionary dictionary) {
        Collection<DictionaryVo> dictionaryTree = dictionaryService.getDictionaryTree(dictionary);
        log.info(String.format("获取的tree对象: %s ",JSONUtil.toJsonStr(dictionaryTree)));
        return RestResult.ok(dictionaryTree);
    }

    /**
     * 获取的级联对象
     *
     * @param dictionary
     * @return
     */
    @GetMapping("/getCascader")
    @ApiOperation(value="获取的级联对象", notes="获取的级联对象")
    @ApiImplicitParam(name = "dictionary", value = "dictionary", required = false, dataType = "Dictionary", paramType = "query")
    public RestResult<List<CascaderResult>> getCascader(Dictionary dictionary) {
        Collection<DictionaryVo> dictionaryVos = dictionaryService.getDictionaryTree(dictionary);
        List<CascaderResult> cascaderResults = CascaderUtil.getResult("Id", "CodeText","TreePosition", dictionary.getId(), dictionaryVos);
        log.info(String.format("获取的级联对象: %s ",JSONUtil.toJsonStr(cascaderResults)));
        return RestResult.ok(cascaderResults);
    }
}
