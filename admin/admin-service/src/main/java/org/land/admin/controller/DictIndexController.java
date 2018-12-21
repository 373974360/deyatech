package org.land.admin.controller;

import org.land.admin.entity.DictIndex;
import org.land.admin.vo.DictIndexVo;
import org.land.admin.service.DictIndexService;
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
 * 系统数据字典索引信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2018-12-21
 */
@Slf4j
@RestController
@RequestMapping("/admin/dictIndex")
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
    public RestResult saveOrUpdate(DictIndex dictIndex) {
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
    public RestResult saveOrUpdateBatch(Collection<DictIndex> dictIndexList) {
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
    public RestResult removeByDictIndex(DictIndex dictIndex) {
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
    public RestResult removeByIds(Collection<Serializable> ids) {
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
    public RestResult getByDictIndex(DictIndex dictIndex) {
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
    @GetMapping("/listByBean")
    public RestResult listByBean(DictIndex dictIndex) {
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
    @GetMapping("/pageByBean")
    public RestResult pageByBean(DictIndex dictIndex) {
        IPage dictIndexs = dictIndexService.pageByBean(dictIndex);
        dictIndexs.setRecords(dictIndexService.setVoProperties(dictIndexs.getRecords()));
        log.info(String.format("根据DictIndex对象属性分页检索系统数据字典索引信息: %s ",JSONUtil.toJsonStr(dictIndexs)));
        return RestResult.ok(dictIndexs);
    }

}
