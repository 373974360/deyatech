package com.deyatech.admin.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.deyatech.admin.config.CustomFormConfig;
import com.deyatech.admin.entity.Metadata;
import com.deyatech.admin.service.MetadataCollectionMetadataService;
import com.deyatech.admin.service.MetadataService;
import com.deyatech.admin.vo.MetadataVo;
import com.deyatech.common.entity.RestResult;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.RestController;
import com.deyatech.common.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 元数据 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-08-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/metadata")
@Api(tags = {"元数据接口"})
public class MetadataController extends BaseController {

    @Autowired
    MetadataService metadataService;
    @Autowired
    MetadataCollectionMetadataService metadataCollectionMetadataService;

    /**
     * 单个保存或者更新
     *
     * @param metadataVo
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新", notes="根据对象保存或者更新信息")
    @ApiImplicitParam(name = "metadataVo", value = "对象", required = true, dataType = "MetadataVo", paramType = "query")
    public RestResult<MetadataVo> saveOrUpdate(MetadataVo metadataVo, String relationDataJson) {
        if (StrUtil.isNotBlank(relationDataJson)) {
            List<MetadataVo> relationList = JSONUtil.toList(JSONUtil.parseArray(relationDataJson), MetadataVo.class);
            metadataVo.setRelationList(relationList);
        }
        log.info(String.format("保存或者更新: %s ", JSONUtil.toJsonStr(metadataVo)));
        Metadata metadata = metadataService.save(metadataVo);
        return RestResult.ok(metadataService.setVoProperties(metadata));
    }

    /**
     * 批量保存或者更新
     *
     * @param metadataList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新", notes="根据对象集合批量保存或者更新信息")
    @ApiImplicitParam(name = "metadataList", value = "对象集合", required = true, allowMultiple = true, dataType = "Metadata", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<Metadata> metadataList) {
        log.info(String.format("批量保存或者更新: %s ", JSONUtil.toJsonStr(metadataList)));
        boolean result = metadataService.saveOrUpdateBatch(metadataList);
        return RestResult.ok(result);
    }

    /**
     * 根据Metadata对象属性逻辑删除
     *
     * @param metadata
     * @return
     */
    @PostMapping("/removeByMetadata")
    @ApiOperation(value="根据Metadata对象属性逻辑删除", notes="根据对象逻辑删除信息")
    @ApiImplicitParam(name = "metadata", value = "对象", required = true, dataType = "Metadata", paramType = "query")
    public RestResult<Boolean> removeByMetadata(Metadata metadata) {
        log.info(String.format("根据Metadata对象属性逻辑删除: %s ", metadata));
        Metadata md = metadataService.getByBean(metadata);
        if (Objects.nonNull(md)) {
            int count = metadataCollectionMetadataService.count(metadata.getId());
            if (count > 0) {
                return RestResult.error("元数据已经被使用不能删除");
            }
        }
        boolean result = metadataService.removeByBean(metadata);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除", notes="根据对象ID批量逻辑删除信息")
    @ApiImplicitParam(name = "ids", value = "对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(@RequestParam("ids[]") List<String> ids) {
        log.info(String.format("根据id批量删除: %s ", JSONUtil.toJsonStr(ids)));
        if (CollectionUtil.isNotEmpty(ids)) {
            for (String id : ids) {
                int count = metadataCollectionMetadataService.count(id);
                if (count > 0) {
                    return RestResult.error("元数据已经被使用不能删除");
                }
            }
        }
        boolean result = metadataService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Metadata对象属性获取
     *
     * @param metadata
     * @return
     */
    @GetMapping("/getByMetadata")
    @ApiOperation(value="根据Metadata对象属性获取", notes="根据对象属性获取信息")
    @ApiImplicitParam(name = "metadata", value = "对象", required = false, dataType = "Metadata", paramType = "query")
    public RestResult<MetadataVo> getByMetadata(Metadata metadata) {
        metadata = metadataService.getByBean(metadata);
        MetadataVo metadataVo = metadataService.setVoProperties(metadata);
        log.info(String.format("根据id获取：%s", JSONUtil.toJsonStr(metadataVo)));
        return RestResult.ok(metadataVo);
    }

    /**
     * 根据Metadata对象属性检索所有
     *
     * @param metadata
     * @return
     */
    @GetMapping("/listByMetadata")
    @ApiOperation(value="根据Metadata对象属性检索所有", notes="根据Metadata对象属性检索所有信息")
    @ApiImplicitParam(name = "metadata", value = "对象", required = false, dataType = "Metadata", paramType = "query")
    public RestResult<Collection<MetadataVo>> listByMetadata(Metadata metadata) {
        Collection<Metadata> metadatas = metadataService.listByBean(metadata);
        Collection<MetadataVo> metadataVos = metadataService.setVoProperties(metadatas);
        log.info(String.format("根据Metadata对象属性检索所有: %s ",JSONUtil.toJsonStr(metadataVos)));
        return RestResult.ok(metadataVos);
    }

    /**
     * 根据Metadata对象属性分页检索
     *
     * @param metadata
     * @return
     */
    @GetMapping("/pageByMetadata")
    @ApiOperation(value="根据Metadata对象属性分页检索", notes="根据Metadata对象属性分页检索信息")
    @ApiImplicitParam(name = "metadata", value = "对象", required = false, dataType = "Metadata", paramType = "query")
    public RestResult<IPage<MetadataVo>> pageByMetadata(Metadata metadata) {
        IPage<MetadataVo> metadatas = metadataService.pageByBean(metadata);
        metadatas.setRecords(metadataService.setVoProperties(metadatas.getRecords()));
        log.info(String.format("根据Metadata对象属性分页检索: %s ",JSONUtil.toJsonStr(metadatas)));
        return RestResult.ok(metadatas);
    }

    /**
     * 获取所有数据类型信息
     *
     * @return
     */
    @GetMapping("/dataType")
    @ApiOperation(value = "获取所有数据类型信息", notes = "获取所有数据类型信息")
    public RestResult dataType() {
        return RestResult.ok(CustomFormConfig.getAllDataType());
    }

    /**
     * 获取所有控件类型信息
     *
     * @return
     */
    @GetMapping("/controlType")
    @ApiOperation(value = "获取所有控件类型信息", notes = "获取所有控件类型信息")
    public RestResult controlType() {
        return RestResult.ok(CustomFormConfig.getDataShow());
    }

    /**
     * 根据复合类型元数据id和分类id查询可关联的基本类型元数据
     *
     * @param id
     * @param categoryId
     * @return
     */
    @GetMapping("/findCandidateRelation")
    @ApiOperation(value="根据复合类型元数据id和分类id查询可关联的基本类型元数据", notes="根据复合类型元数据id和分类id查询可关联的基本类型元数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "元数据id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = true, dataType = "String", paramType = "query")
    })
    public RestResult findCandidateRelation(String id, String categoryId) {
        List<Metadata> list = metadataService.findCandidateRelation(id, categoryId);
        return RestResult.ok(metadataService.setVoProperties(list));
    }

    /**
     * 检查中文名称是否存在
     *
     * @param id
     * @param name
     * @return
     */
    @GetMapping("/checkNameExist")
    @ApiOperation(value="检查中文名称是否存在", notes="检查中文名称是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "元数据id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "中文名称", required = true, dataType = "String", paramType = "query")
    })
    public RestResult checkNameExist(String id, String name) {
        return RestResult.ok(metadataService.checkNameExist(id, name));
    }

    /**
     * 检查字段名是否存在
     *
     * @param id
     * @param briefName
     * @return
     */
    @GetMapping("/checkBriefNameExist")
    @ApiOperation(value="检查字段名是否存在", notes="检查字段名是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "元数据id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "briefName", value = "字段名", required = true, dataType = "String", paramType = "query")
    })
    public RestResult checkBriefNameExist(String id, String briefName) {
        return RestResult.ok(metadataService.checkBriefNameExist(id, briefName));
    }

    /**
     * 检查英文名称是否存在
     *
     * @param id
     * @param enName
     * @return
     */
    @GetMapping("/checkEnNameExist")
    @ApiOperation(value="检查英文名称是否存在", notes="检查英文名称是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "元数据id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "enName", value = "英文名称", required = true, dataType = "String", paramType = "query")
    })
    public RestResult checkEnNameExist(String id, String enName) {
        return RestResult.ok(metadataService.checkEnNameExist(id, enName));
    }
}
