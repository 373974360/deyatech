package com.deyatech.admin.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.Metadata;
import com.deyatech.admin.entity.MetadataCollection;
import com.deyatech.admin.service.MetadataCollectionMetadataService;
import com.deyatech.admin.service.MetadataCollectionService;
import com.deyatech.admin.service.MetadataService;
import com.deyatech.admin.util.MetaUtils;
import com.deyatech.admin.vo.MetadataCollectionMetadataVo;
import com.deyatech.admin.vo.MetadataCollectionVo;
import com.deyatech.common.base.BaseController;
import com.deyatech.common.entity.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private MetadataService metadataService;

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
        log.info(String.format("保存或者更新: %s ", JSONUtil.toJsonStr(metadataCollectionVo)));
        if (StrUtil.isNotBlank(metadataCollectionMetadataJson)) {
            List<MetadataCollectionMetadataVo> metadataVoList =
                    JSONUtil.toList(JSONUtil.parseArray(metadataCollectionMetadataJson), MetadataCollectionMetadataVo.class);
            metadataCollectionVo.setMetadataList(metadataVoList);
        }
        // 编辑时检查元数据有没有变更
        if (StrUtil.isNotEmpty(metadataCollectionVo.getId())) {
            StringBuilder errorMsg = new StringBuilder();
            List<MetadataCollectionMetadataVo> metadataVoList = metadataCollectionVo.getMetadataList();
            if (CollectionUtil.isNotEmpty(metadataVoList)) {
                for (MetadataCollectionMetadataVo metadataVo : metadataVoList) {
                    // 数据库中
                    Metadata db = metadataService.setVoProperties(metadataService.getById(metadataVo.getMetadataId()));
                    String msg = "";
                    // 类型不相等
                    if (!metadataVo.getDataType().equals(db.getDataType())) {
                        msg = ",类型改变";
                    }
                    // 控件不等
                    if (!metadataVo.getControlType().equals(db.getControlType())) {
                        msg += ",控件改变";
                    }
                    int len1 = Integer.parseInt(metadataVo.getDataLength());
                    int len2 = Integer.parseInt(db.getDataLength());
                    // 长度不等
                    if (len1 < len2) {
                        msg += ",长度变小";
                    }
                    if (StrUtil.isNotEmpty(msg)) {
                        errorMsg.append("【");
                        errorMsg.append(db.getName());
                        errorMsg.append(db.getBriefName());
                        errorMsg.append("】");
                        errorMsg.append(msg.substring(1));
                    }
                }
            }
            long count = MetaUtils.countTotal(MetaUtils.getTableName(metadataCollectionVo));
            if (errorMsg.length() > 0 && count > 0) {
                errorMsg.append("不能保存。");
                return RestResult.error(errorMsg.toString());
            }
        }
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
        MetadataCollection mc = metadataCollectionService.getByBean(metadataCollection);
        MetadataCollectionVo mcvo = metadataCollectionService.setVoProperties(mc);
        if (Objects.nonNull(mc)) {
            long count = MetaUtils.countTotal(MetaUtils.getTableName(mcvo));
            if (count > 0) {
                return RestResult.error(MetaUtils.getTableName(mcvo) + "表有数据不能删除");
            }
        }
        boolean result = metadataCollectionService.removeByBean(metadataCollection);
        if (result && Objects.nonNull(mc)) {
            // 删除数据库表
            MetaUtils.dropTable(MetaUtils.getTableName(mcvo));
        }
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
            for (String id :ids) {
                MetadataCollection mc = metadataCollectionService.getById(id);
                MetadataCollectionVo mcvo = metadataCollectionService.setVoProperties(mc);
                long count = MetaUtils.countTotal(MetaUtils.getTableName(mcvo));
                if (count > 0) {
                    return RestResult.error(MetaUtils.getTableName(mcvo) + "表有数据不能删除");
                }
            }
        }
        List<MetadataCollection> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(ids)) {
            for (String id : ids) {
                list.add(metadataCollectionService.getById(id));
            }
        }
        boolean result = metadataCollectionService.removeByIds(ids);
        if (result) {
            for (MetadataCollection mc : list) {
                metadataCollectionService.setMainVersionByEnName(mc.getEnName());
                MetadataCollectionVo mcvo = metadataCollectionService.setVoProperties(mc);
                // 删除数据库表
                MetaUtils.dropTable(MetaUtils.getTableName(mcvo));
            }
        }
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
