package com.deyatech.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.deyatech.admin.entity.MetadataCollection;
import com.deyatech.admin.service.MetadataCollectionService;
import com.deyatech.admin.vo.MetadataCollectionMetadataVo;
import com.deyatech.admin.vo.MetadataCollectionVo;
import com.deyatech.common.entity.RestResult;
import io.swagger.annotations.ApiImplicitParams;
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
 * 元数据集 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-08-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/metadataCollection")
@Api(tags = {"元数据集接口"})
public class MetadataCollectionController extends BaseController {

    @Autowired
    MetadataCollectionService metadataCollectionService;

    /**
     * 单个保存或者更新
     *
     * @param metadataCollectionVo
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新", notes="根据对象保存或者更新信息")
    @ApiImplicitParam(name = "metadataCollection", value = "对象", required = true, dataType = "MetadataCollection", paramType = "query")
    public RestResult saveOrUpdate(MetadataCollectionVo metadataCollectionVo, String metadataCollectionMetadataJson) {
        if (StrUtil.isNotBlank(metadataCollectionMetadataJson)) {
            List<MetadataCollectionMetadataVo> metadataVoList =
                    JSONUtil.toList(JSONUtil.parseArray(metadataCollectionMetadataJson), MetadataCollectionMetadataVo.class);
            metadataCollectionVo.setMetadataList(metadataVoList);
        }
        log.info(String.format("保存或者更新: %s ", JSONUtil.toJsonStr(metadataCollectionVo)));
        MetadataCollection collection = metadataCollectionService.save(metadataCollectionVo);
        return RestResult.ok(metadataCollectionService.setVoProperties(collection));
    }

    /**
     * 批量保存或者更新
     *
     * @param metadataCollectionList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新", notes="根据对象集合批量保存或者更新信息")
    @ApiImplicitParam(name = "metadataCollectionList", value = "对象集合", required = true, allowMultiple = true, dataType = "MetadataCollection", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<MetadataCollection> metadataCollectionList) {
        log.info(String.format("批量保存或者更新: %s ", JSONUtil.toJsonStr(metadataCollectionList)));
        boolean result = metadataCollectionService.saveOrUpdateBatch(metadataCollectionList);
        return RestResult.ok(result);
    }

    /**
     * 根据MetadataCollection对象属性逻辑删除
     *
     * @param metadataCollection
     * @return
     */
    @PostMapping("/removeByMetadataCollection")
    @ApiOperation(value="根据MetadataCollection对象属性逻辑删除", notes="根据对象逻辑删除信息")
    @ApiImplicitParam(name = "metadataCollection", value = "对象", required = true, dataType = "MetadataCollection", paramType = "query")
    public RestResult<Boolean> removeByMetadataCollection(MetadataCollection metadataCollection) {
        log.info(String.format("根据MetadataCollection对象属性逻辑删除: %s ", metadataCollection));
        boolean result = metadataCollectionService.removeByBean(metadataCollection);
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
        boolean result = metadataCollectionService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据MetadataCollection对象属性获取
     *
     * @param metadataCollection
     * @return
     */
    @GetMapping("/getByMetadataCollection")
    @ApiOperation(value="根据MetadataCollection对象属性获取", notes="根据对象属性获取信息")
    @ApiImplicitParam(name = "metadataCollection", value = "对象", required = false, dataType = "MetadataCollection", paramType = "query")
    public RestResult<MetadataCollectionVo> getByMetadataCollection(MetadataCollection metadataCollection) {
        metadataCollection = metadataCollectionService.getByBean(metadataCollection);
        MetadataCollectionVo metadataCollectionVo = metadataCollectionService.setVoProperties(metadataCollection);
        log.info(String.format("根据id获取：%s", JSONUtil.toJsonStr(metadataCollectionVo)));
        return RestResult.ok(metadataCollectionVo);
    }

    /**
     * 根据MetadataCollection对象属性检索所有
     *
     * @param metadataCollection
     * @return
     */
    @GetMapping("/listByMetadataCollection")
    @ApiOperation(value="根据MetadataCollection对象属性检索所有", notes="根据MetadataCollection对象属性检索所有信息")
    @ApiImplicitParam(name = "metadataCollection", value = "对象", required = false, dataType = "MetadataCollection", paramType = "query")
    public RestResult<Collection<MetadataCollectionVo>> listByMetadataCollection(MetadataCollection metadataCollection) {
        Collection<MetadataCollection> metadataCollections = metadataCollectionService.listByBean(metadataCollection);
        Collection<MetadataCollectionVo> metadataCollectionVos = metadataCollectionService.setVoProperties(metadataCollections);
        log.info(String.format("根据MetadataCollection对象属性检索所有: %s ",JSONUtil.toJsonStr(metadataCollectionVos)));
        return RestResult.ok(metadataCollectionVos);
    }

    /**
     * 根据MetadataCollection对象属性分页检索
     *
     * @param metadataCollection
     * @return
     */
    @GetMapping("/pageByMetadataCollection")
    @ApiOperation(value="根据MetadataCollection对象属性分页检索", notes="根据MetadataCollection对象属性分页检索信息")
    @ApiImplicitParam(name = "metadataCollection", value = "对象", required = false, dataType = "MetadataCollection", paramType = "query")
    public RestResult<IPage<MetadataCollectionVo>> pageByMetadataCollection(MetadataCollection metadataCollection) {
        IPage<MetadataCollectionVo> metadataCollections = metadataCollectionService.pageByBean(metadataCollection);
        metadataCollections.setRecords(metadataCollectionService.setVoProperties(metadataCollections.getRecords()));
        log.info(String.format("根据MetadataCollection对象属性分页检索: %s ",JSONUtil.toJsonStr(metadataCollections)));
        return RestResult.ok(metadataCollections);
    }

    /**
     * 获取所有元数据集（包括关联的元数据）信息
     *
     * @param metadataCollectionVo
     * @return
     */
    @GetMapping("/findAllData")
    @ApiOperation(value="获取所有元数据集（包括关联的元数据）信息", notes="获取所有元数据集（包括关联的元数据）信息")
    @ApiImplicitParam(name = "metadataCollectionVo", value = "对象", required = false, dataType = "MetadataCollectionVo", paramType = "query")
    public RestResult findAllData(MetadataCollectionVo metadataCollectionVo) {
        List<MetadataCollectionVo> data = metadataCollectionService.findAllData(metadataCollectionVo);
        return RestResult.ok(data);
    }

    /**
     * 设置主版本
     *
     * @param id
     * @return
     */
    @PostMapping("/setMainVersion")
    @ApiOperation(value="设置主版本", notes="设置主版本")
    @ApiImplicitParam(name = "id", value = "元数据集id", required = true, dataType = "String", paramType = "query")
    public RestResult setMainVersion(String id) {
        metadataCollectionService.setMainVersion(id);
        return RestResult.ok();
    }

    /**
     * 检查名称是否存在
     *
     * @param enName
     * @param name
     * @return
     */
    @GetMapping("/checkNameExist")
    @ApiOperation(value="检查名称是否存在", notes="检查名称是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enName", value = "英文名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "String", paramType = "query")
    })
    public RestResult checkNameExist(String enName, String name) {
        return RestResult.ok(metadataCollectionService.checkNameExist(enName, name));
    }

    /**
     * 检查英文名称是否存在
     *
     * @param id
     * @param name
     * @param enName
     * @return
     */
    @GetMapping("/checkEnNameExist")
    @ApiOperation(value="检查英文名称是否存在", notes="检查英文名称是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "enName", value = "英文名称", required = true, dataType = "String", paramType = "query")
    })
    public RestResult checkEnNameExist(String id, String name, String enName) {
        return RestResult.ok(metadataCollectionService.checkEnNameExist(id, name, enName));
    }

    /**
     * 检查版本号是否存在
     *
     * @param id
     * @param enName
     * @param version
     * @return
     */
    @GetMapping("/checkVersionExist")
    @ApiOperation(value="检查版本号是否存在", notes="检查版本号是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "enName", value = "英文名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "version", value = "版本号", required = true, dataType = "String", paramType = "query")
    })
    public RestResult checkVersionExist(String id, String enName, String version) {
        return RestResult.ok(metadataCollectionService.checkVersionExist(id, enName, version));
    }
}
