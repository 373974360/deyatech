package com.deyatech.admin.controller;

import com.deyatech.admin.entity.MetadataCollectionMetadata;
import com.deyatech.admin.service.MetadataCollectionMetadataService;
import com.deyatech.admin.vo.MetadataCollectionMetadataVo;
import com.deyatech.common.entity.RestResult;
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
 * 元数据集元数据关联 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-08-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/metadataCollectionMetadata")
@Api(tags = {"元数据集元数据关联接口"})
public class MetadataCollectionMetadataController extends BaseController {

    @Autowired
    MetadataCollectionMetadataService metadataCollectionMetadataService;

    /**
     * 单个保存或者更新
     *
     * @param metadataCollectionMetadata
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新", notes="根据对象保存或者更新信息")
    @ApiImplicitParam(name = "metadataCollectionMetadata", value = "对象", required = true, dataType = "MetadataCollectionMetadata", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(MetadataCollectionMetadata metadataCollectionMetadata) {
        log.info(String.format("保存或者更新: %s ", JSONUtil.toJsonStr(metadataCollectionMetadata)));
        boolean result = metadataCollectionMetadataService.saveOrUpdate(metadataCollectionMetadata);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新
     *
     * @param metadataCollectionMetadataList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新", notes="根据对象集合批量保存或者更新信息")
    @ApiImplicitParam(name = "metadataCollectionMetadataList", value = "对象集合", required = true, allowMultiple = true, dataType = "MetadataCollectionMetadata", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<MetadataCollectionMetadata> metadataCollectionMetadataList) {
        log.info(String.format("批量保存或者更新: %s ", JSONUtil.toJsonStr(metadataCollectionMetadataList)));
        boolean result = metadataCollectionMetadataService.saveOrUpdateBatch(metadataCollectionMetadataList);
        return RestResult.ok(result);
    }

    /**
     * 根据MetadataCollectionMetadata对象属性逻辑删除
     *
     * @param metadataCollectionMetadata
     * @return
     */
    @PostMapping("/removeByMetadataCollectionMetadata")
    @ApiOperation(value="根据MetadataCollectionMetadata对象属性逻辑删除", notes="根据对象逻辑删除信息")
    @ApiImplicitParam(name = "metadataCollectionMetadata", value = "对象", required = true, dataType = "MetadataCollectionMetadata", paramType = "query")
    public RestResult<Boolean> removeByMetadataCollectionMetadata(MetadataCollectionMetadata metadataCollectionMetadata) {
        log.info(String.format("根据MetadataCollectionMetadata对象属性逻辑删除: %s ", metadataCollectionMetadata));
        boolean result = metadataCollectionMetadataService.removeByBean(metadataCollectionMetadata);
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
        boolean result = metadataCollectionMetadataService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据MetadataCollectionMetadata对象属性获取
     *
     * @param metadataCollectionMetadata
     * @return
     */
    @GetMapping("/getByMetadataCollectionMetadata")
    @ApiOperation(value="根据MetadataCollectionMetadata对象属性获取", notes="根据对象属性获取信息")
    @ApiImplicitParam(name = "metadataCollectionMetadata", value = "对象", required = false, dataType = "MetadataCollectionMetadata", paramType = "query")
    public RestResult<MetadataCollectionMetadataVo> getByMetadataCollectionMetadata(MetadataCollectionMetadata metadataCollectionMetadata) {
        metadataCollectionMetadata = metadataCollectionMetadataService.getByBean(metadataCollectionMetadata);
        MetadataCollectionMetadataVo metadataCollectionMetadataVo = metadataCollectionMetadataService.setVoProperties(metadataCollectionMetadata);
        log.info(String.format("根据id获取：%s", JSONUtil.toJsonStr(metadataCollectionMetadataVo)));
        return RestResult.ok(metadataCollectionMetadataVo);
    }

    /**
     * 根据MetadataCollectionMetadata对象属性检索所有
     *
     * @param metadataCollectionMetadata
     * @return
     */
    @GetMapping("/listByMetadataCollectionMetadata")
    @ApiOperation(value="根据MetadataCollectionMetadata对象属性检索所有", notes="根据MetadataCollectionMetadata对象属性检索所有信息")
    @ApiImplicitParam(name = "metadataCollectionMetadata", value = "对象", required = false, dataType = "MetadataCollectionMetadata", paramType = "query")
    public RestResult<Collection<MetadataCollectionMetadataVo>> listByMetadataCollectionMetadata(MetadataCollectionMetadata metadataCollectionMetadata) {
        Collection<MetadataCollectionMetadata> metadataCollectionMetadatas = metadataCollectionMetadataService.listByBean(metadataCollectionMetadata);
        Collection<MetadataCollectionMetadataVo> metadataCollectionMetadataVos = metadataCollectionMetadataService.setVoProperties(metadataCollectionMetadatas);
        log.info(String.format("根据MetadataCollectionMetadata对象属性检索所有: %s ",JSONUtil.toJsonStr(metadataCollectionMetadataVos)));
        return RestResult.ok(metadataCollectionMetadataVos);
    }

    /**
     * 根据MetadataCollectionMetadata对象属性分页检索
     *
     * @param metadataCollectionMetadata
     * @return
     */
    @GetMapping("/pageByMetadataCollectionMetadata")
    @ApiOperation(value="根据MetadataCollectionMetadata对象属性分页检索", notes="根据MetadataCollectionMetadata对象属性分页检索信息")
    @ApiImplicitParam(name = "metadataCollectionMetadata", value = "对象", required = false, dataType = "MetadataCollectionMetadata", paramType = "query")
    public RestResult<IPage<MetadataCollectionMetadataVo>> pageByMetadataCollectionMetadata(MetadataCollectionMetadata metadataCollectionMetadata) {
        IPage<MetadataCollectionMetadataVo> metadataCollectionMetadatas = metadataCollectionMetadataService.pageByBean(metadataCollectionMetadata);
        metadataCollectionMetadatas.setRecords(metadataCollectionMetadataService.setVoProperties(metadataCollectionMetadatas.getRecords()));
        log.info(String.format("根据MetadataCollectionMetadata对象属性分页检索: %s ",JSONUtil.toJsonStr(metadataCollectionMetadatas)));
        return RestResult.ok(metadataCollectionMetadatas);
    }

}
