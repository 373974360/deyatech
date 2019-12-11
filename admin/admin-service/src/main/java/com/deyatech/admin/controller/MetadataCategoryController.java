package com.deyatech.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deyatech.admin.entity.MetadataCategory;
import com.deyatech.admin.service.MetadataCategoryService;
import com.deyatech.admin.vo.MetadataCategoryVo;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.entity.CascaderResult;
import com.deyatech.common.utils.CascaderUtil;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.deyatech.common.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 元数据分类 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-08-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/metadataCategory")
@Api(tags = {"元数据分类接口"})
public class MetadataCategoryController extends BaseController {

    @Autowired
    MetadataCategoryService metadataCategoryService;

    /**
     * 单个保存或者更新元数据分类
     *
     * @param metadataCategory
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新元数据分类", notes="根据元数据分类对象保存或者更新元数据分类信息")
    @ApiImplicitParam(name = "metadataCategory", value = "元数据分类对象", required = true, dataType = "MetadataCategory", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(MetadataCategory metadataCategory) {
        log.info(String.format("保存或者更新元数据分类: %s ", JSONUtil.toJsonStr(metadataCategory)));
        boolean result = metadataCategoryService.saveOrUpdate(metadataCategory);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新元数据分类
     *
     * @param metadataCategoryList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新元数据分类", notes="根据元数据分类对象集合批量保存或者更新元数据分类信息")
    @ApiImplicitParam(name = "metadataCategoryList", value = "元数据分类对象集合", required = true, allowMultiple = true, dataType = "MetadataCategory", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<MetadataCategory> metadataCategoryList) {
        log.info(String.format("批量保存或者更新元数据分类: %s ", JSONUtil.toJsonStr(metadataCategoryList)));
        boolean result = metadataCategoryService.saveOrUpdateBatch(metadataCategoryList);
        return RestResult.ok(result);
    }

    /**
     * 根据MetadataCategory对象属性逻辑删除元数据分类
     *
     * @param metadataCategory
     * @return
     */
    @PostMapping("/removeByMetadataCategory")
    @ApiOperation(value="根据MetadataCategory对象属性逻辑删除元数据分类", notes="根据元数据分类对象逻辑删除元数据分类信息")
    @ApiImplicitParam(name = "metadataCategory", value = "元数据分类对象", required = true, dataType = "MetadataCategory", paramType = "query")
    public RestResult<Boolean> removeByMetadataCategory(MetadataCategory metadataCategory) {
        log.info(String.format("根据MetadataCategory对象属性逻辑删除元数据分类: %s ", metadataCategory));
        boolean result = metadataCategoryService.removeByBean(metadataCategory);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除元数据分类
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除元数据分类", notes="根据元数据分类对象ID批量逻辑删除元数据分类信息")
    @ApiImplicitParam(name = "ids", value = "元数据分类对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(@RequestParam("ids[]") List<String> ids) {
        log.info(String.format("根据id批量删除元数据分类: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = metadataCategoryService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据MetadataCategory对象属性获取元数据分类
     *
     * @param metadataCategory
     * @return
     */
    @GetMapping("/getByMetadataCategory")
    @ApiOperation(value="根据MetadataCategory对象属性获取元数据分类", notes="根据元数据分类对象属性获取元数据分类信息")
    @ApiImplicitParam(name = "metadataCategory", value = "元数据分类对象", required = false, dataType = "MetadataCategory", paramType = "query")
    public RestResult<MetadataCategoryVo> getByMetadataCategory(MetadataCategory metadataCategory) {
        metadataCategory = metadataCategoryService.getByBean(metadataCategory);
        MetadataCategoryVo metadataCategoryVo = metadataCategoryService.setVoProperties(metadataCategory);
        log.info(String.format("根据id获取元数据分类：%s", JSONUtil.toJsonStr(metadataCategoryVo)));
        return RestResult.ok(metadataCategoryVo);
    }

    /**
     * 根据MetadataCategory对象属性检索所有元数据分类
     *
     * @param metadataCategory
     * @return
     */
    @GetMapping("/listByMetadataCategory")
    @ApiOperation(value="根据MetadataCategory对象属性检索所有元数据分类", notes="根据MetadataCategory对象属性检索所有元数据分类信息")
    @ApiImplicitParam(name = "metadataCategory", value = "元数据分类对象", required = false, dataType = "MetadataCategory", paramType = "query")
    public RestResult<Collection<MetadataCategoryVo>> listByMetadataCategory(MetadataCategory metadataCategory) {
        Collection<MetadataCategory> metadataCategorys = metadataCategoryService.listByBean(metadataCategory);
        Collection<MetadataCategoryVo> metadataCategoryVos = metadataCategoryService.setVoProperties(metadataCategorys);
        log.info(String.format("根据MetadataCategory对象属性检索所有元数据分类: %s ",JSONUtil.toJsonStr(metadataCategoryVos)));
        return RestResult.ok(metadataCategoryVos);
    }

    /**
     * 根据MetadataCategory对象属性分页检索元数据分类
     *
     * @param metadataCategory
     * @return
     */
    @GetMapping("/pageByMetadataCategory")
    @ApiOperation(value="根据MetadataCategory对象属性分页检索元数据分类", notes="根据MetadataCategory对象属性分页检索元数据分类信息")
    @ApiImplicitParam(name = "metadataCategory", value = "元数据分类对象", required = false, dataType = "MetadataCategory", paramType = "query")
    public RestResult<IPage<MetadataCategoryVo>> pageByMetadataCategory(MetadataCategory metadataCategory) {
        IPage<MetadataCategoryVo> metadataCategorys = metadataCategoryService.pageByBean(metadataCategory);
        metadataCategorys.setRecords(metadataCategoryService.setVoProperties(metadataCategorys.getRecords()));
        log.info(String.format("根据MetadataCategory对象属性分页检索元数据分类: %s ",JSONUtil.toJsonStr(metadataCategorys)));
        return RestResult.ok(metadataCategorys);
    }

    /**
     * 获取元数据分类的tree对象
     *
     * @param metadataCategory
     * @return
     */
    @GetMapping("/getTree")
    @ApiOperation(value="获取元数据分类的tree对象", notes="获取元数据分类的tree对象")
    public RestResult<Collection<MetadataCategoryVo>> getMetadataCategoryTree(MetadataCategory metadataCategory) {
        Collection<MetadataCategoryVo> metadataCategoryTree = metadataCategoryService.getMetadataCategoryTree(metadataCategory);
        log.info(String.format("获取元数据分类的tree对象: %s ",JSONUtil.toJsonStr(metadataCategoryTree)));
        return RestResult.ok(metadataCategoryTree);
    }

    /**
     * 获取元数据分类的级联对象
     *
     * @param metadataCategory
     * @return
     */
    @GetMapping("/getCascader")
    @ApiOperation(value="获取元数据分类的级联对象", notes="获取元数据分类的级联对象")
    @ApiImplicitParam(name = "metadataCategory", value = "元数据分类", required = false, dataType = "MetadataCategory", paramType = "query")
    public RestResult<List<CascaderResult>> getCascader(MetadataCategory metadataCategory) {
        Collection<MetadataCategoryVo> metadataCategoryVos = metadataCategoryService.getMetadataCategoryTree(metadataCategory);
        List<CascaderResult> cascaderResults = CascaderUtil.getResult("Id", "Name","TreePosition", metadataCategory.getId(), metadataCategoryVos);
        log.info(String.format("获取元数据分类的级联对象: %s ",JSONUtil.toJsonStr(cascaderResults)));
        return RestResult.ok(cascaderResults);
    }

    /**
     * 检查分类下是否存在元数据
     *
     * @param id
     * @return
     */
    @GetMapping("/checkMetadataExist")
    @ApiOperation(value="检查分类下是否存在元数据", notes="检查分类下是否存在元数据")
    @ApiImplicitParam(name = "id", value = "元数据分类id", required = true, dataType = "String", paramType = "query")
    public RestResult checkMetadataExist(String id) {
        return RestResult.ok(metadataCategoryService.checkMetadataExist(id));
    }

    /**
     * 下一个排序号
     *
     * @return
     */
    @RequestMapping("/getNextSortNo")
    @ApiOperation(value = "下一个排序号", notes = "下一个排序号")
    public RestResult<Integer> getNextSortNo(String id) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        queryWrapper.select("ifnull(max(sort_no), 0) + 1 as sortNo");
        return RestResult.ok(metadataCategoryService.getMap(queryWrapper).get("sortNo"));
    }
}
