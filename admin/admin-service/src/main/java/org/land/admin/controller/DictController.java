package org.land.admin.controller;

import org.land.admin.entity.Dict;
import org.land.admin.vo.DictVo;
import org.land.admin.service.DictService;
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
 * 系统数据字典明细信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2018-12-19
 */
@Slf4j
@RestController
@RequestMapping("/admin/dict")
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
    public RestResult saveOrUpdate(Dict dict) {
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
    public RestResult saveOrUpdateBatch(Collection<Dict> dictList) {
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
    public RestResult removeByDict(Dict dict) {
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
    public RestResult removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统数据字典明细信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = dictService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据ID获取系统数据字典明细信息
     *
     * @param dict
     * @return
     */
    @GetMapping("/getByDict")
    public RestResult getByDict(Dict dict) {
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
    public RestResult listByBean(Dict dict) {
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
    public RestResult pageByBean(Dict dict) {
        IPage dicts = dictService.pageByBean(dict);
        dicts.setRecords(dictService.setVoProperties(dicts.getRecords()));
        log.info(String.format("根据Dict对象属性分页检索系统数据字典明细信息: %s ",JSONUtil.toJsonStr(dicts)));
        return RestResult.ok(dicts);
    }

}
