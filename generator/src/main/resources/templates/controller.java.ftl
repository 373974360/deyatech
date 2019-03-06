package ${package.Controller};
<#assign lowerEntity = entity?uncap_first/>
<#assign entityVo = entity + "Vo"/>
<#assign lowerEntityVo = lowerEntity + "Vo"/>

import ${package.Entity}.${entity};
import ${package.Entity?replace("entity","vo")}.${entityVo};
import ${package.Service}.${entity}Service;
import com.deyatech.common.entity.RestResult;
<#list table.fields as field>
    <#if field.name = "parent_id">
        <#assign isTree=true/>
    </#if>
</#list>
<#if isTree??>
import com.deyatech.common.entity.CascaderResult;
import com.deyatech.common.utils.CascaderUtil;
import java.util.List;
</#if>
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.Serializable;
import java.util.Collection;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import com.deyatech.common.base.BaseController;
</#if>
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 * @author: ${author}
 * @since ${date}
 */
@Slf4j
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/${lowerEntity}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if swagger2>
@Api(tags = {"${table.comment!}接口"})
</#if>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    ${entity}Service ${lowerEntity}Service;

    /**
     * 单个保存或者更新${table.comment!}
     *
     * @param ${lowerEntity}
     * @return
     */
    @PostMapping("/saveOrUpdate")
    <#if swagger2>
    @ApiOperation(value="单个保存或者更新${table.comment!}", notes="根据${table.comment!}对象保存或者更新${table.comment!}信息")
    @ApiImplicitParam(name = "${lowerEntity}", value = "${table.comment!}对象", required = true, dataType = "${entity}", paramType = "query")
    </#if>
    public RestResult<Boolean> saveOrUpdate(${entity} ${lowerEntity}) {
        log.info(String.format("保存或者更新${table.comment!}: %s ", JSONUtil.toJsonStr(${lowerEntity})));
        boolean result = ${lowerEntity}Service.saveOrUpdate(${lowerEntity});
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新${table.comment!}
     *
     * @param ${lowerEntity}List
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    <#if swagger2>
    @ApiOperation(value="批量保存或者更新${table.comment!}", notes="根据${table.comment!}对象集合批量保存或者更新${table.comment!}信息")
    @ApiImplicitParam(name = "${lowerEntity}List", value = "${table.comment!}对象集合", required = true, allowMultiple = true, dataType = "${entity}", paramType = "query")
    </#if>
    public RestResult<Boolean> saveOrUpdateBatch(Collection<${entity}> ${lowerEntity}List) {
        log.info(String.format("批量保存或者更新${table.comment!}: %s ", JSONUtil.toJsonStr(${lowerEntity}List)));
        boolean result = ${lowerEntity}Service.saveOrUpdateBatch(${lowerEntity}List);
        return RestResult.ok(result);
    }

    /**
     * 根据${entity}对象属性逻辑删除${table.comment!}
     *
     * @param ${lowerEntity}
     * @return
     */
    @PostMapping("/removeBy${entity}")
    <#if swagger2>
    @ApiOperation(value="根据${entity}对象属性逻辑删除${table.comment!}", notes="根据${table.comment!}对象逻辑删除${table.comment!}信息")
    @ApiImplicitParam(name = "${lowerEntity}", value = "${table.comment!}对象", required = true, dataType = "${entity}", paramType = "query")
    </#if>
    public RestResult<Boolean> removeBy${entity}(${entity} ${lowerEntity}) {
        log.info(String.format("根据${entity}对象属性逻辑删除${table.comment!}: %s ", ${lowerEntity}));
        boolean result = ${lowerEntity}Service.removeByBean(${lowerEntity});
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除${table.comment!}
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    <#if swagger2>
    @ApiOperation(value="根据ID批量逻辑删除${table.comment!}", notes="根据${table.comment!}对象ID批量逻辑删除${table.comment!}信息")
    @ApiImplicitParam(name = "ids", value = "${table.comment!}对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    </#if>
    public RestResult<Boolean> removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除${table.comment!}: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = ${lowerEntity}Service.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据${entity}对象属性获取${table.comment!}
     *
     * @param ${lowerEntity}
     * @return
     */
    @GetMapping("/getBy${entity}")
    <#if swagger2>
    @ApiOperation(value="根据${entity}对象属性获取${table.comment!}", notes="根据${table.comment!}对象属性获取${table.comment!}信息")
    @ApiImplicitParam(name = "${lowerEntity}", value = "${table.comment!}对象", required = false, dataType = "${entity}", paramType = "query")
    </#if>
    public RestResult<${entityVo}> getBy${entity}(${entity} ${lowerEntity}) {
        ${lowerEntity} = ${lowerEntity}Service.getByBean(${lowerEntity});
        ${entityVo} ${lowerEntityVo} = ${lowerEntity}Service.setVoProperties(${lowerEntity});
        log.info(String.format("根据id获取${table.comment!}：s%", JSONUtil.toJsonStr(${lowerEntityVo})));
        return RestResult.ok(${lowerEntityVo});
    }

    /**
     * 根据${entity}对象属性检索所有${table.comment!}
     *
     * @param ${lowerEntity}
     * @return
     */
    @GetMapping("/listBy${entity}")
    <#if swagger2>
    @ApiOperation(value="根据${entity}对象属性检索所有${table.comment!}", notes="根据${entity}对象属性检索所有${table.comment!}信息")
    @ApiImplicitParam(name = "${lowerEntity}", value = "${table.comment!}对象", required = false, dataType = "${entity}", paramType = "query")
    </#if>
    public RestResult<Collection<${entityVo}>> listBy${entity}(${entity} ${lowerEntity}) {
        Collection<${entity}> ${lowerEntity}s = ${lowerEntity}Service.listByBean(${lowerEntity});
        Collection<${entityVo}> ${lowerEntityVo}s = ${lowerEntity}Service.setVoProperties(${lowerEntity}s);
        log.info(String.format("根据${entity}对象属性检索所有${table.comment!}: %s ",JSONUtil.toJsonStr(${lowerEntityVo}s)));
        return RestResult.ok(${lowerEntityVo}s);
    }

    /**
     * 根据${entity}对象属性分页检索${table.comment!}
     *
     * @param ${lowerEntity}
     * @return
     */
    @GetMapping("/pageBy${entity}")
    <#if swagger2>
    @ApiOperation(value="根据${entity}对象属性分页检索${table.comment!}", notes="根据${entity}对象属性分页检索${table.comment!}信息")
    @ApiImplicitParam(name = "${lowerEntity}", value = "${table.comment!}对象", required = false, dataType = "${entity}", paramType = "query")
    </#if>
    public RestResult<IPage<${entityVo}>> pageBy${entity}(${entity} ${lowerEntity}) {
        IPage<${entityVo}> ${lowerEntity}s = ${lowerEntity}Service.pageByBean(${lowerEntity});
        ${lowerEntity}s.setRecords(${lowerEntity}Service.setVoProperties(${lowerEntity}s.getRecords()));
        log.info(String.format("根据${entity}对象属性分页检索${table.comment!}: %s ",JSONUtil.toJsonStr(${lowerEntity}s)));
        return RestResult.ok(${lowerEntity}s);
    }

    <#if isTree??>
    /**
     * 获取${table.comment!}的tree对象
     *
     * @param ${lowerEntity}
     * @return
     */
    @GetMapping("/getTree")
    <#if swagger2>
    @ApiOperation(value="获取${table.comment!}的tree对象", notes="获取${table.comment!}的tree对象")
    </#if>
    public RestResult<Collection<${entityVo}>> get${entity}Tree(${entity} ${lowerEntity}) {
        Collection<${entityVo}> ${lowerEntity}Tree = ${lowerEntity}Service.get${entity}Tree(${lowerEntity});
        log.info(String.format("获取${table.comment!}的tree对象: %s ",JSONUtil.toJsonStr(${lowerEntity}Tree)));
        return RestResult.ok(${lowerEntity}Tree);
    }

    /**
     * 获取${table.comment!}的级联对象
     *
     * @param ${lowerEntity}
     * @return
     */
    @GetMapping("/getCascader")
    <#if swagger2>
    @ApiOperation(value="获取${table.comment!}的级联对象", notes="获取${table.comment!}的级联对象")
    @ApiImplicitParam(name = "${lowerEntity}", value = "${lowerEntity}", required = false, dataType = "${entity}", paramType = "query")
    </#if>
    public RestResult<List<CascaderResult>> getCascader(${entity} ${lowerEntity}) {
        Collection<${entityVo}> ${lowerEntityVo}s = ${lowerEntity}Service.get${entity}Tree(${lowerEntity});
        List<CascaderResult> cascaderResults = CascaderUtil.getResult("Id", "Name","TreePosition", ${lowerEntity}.getId(), ${lowerEntityVo}s);
        log.info(String.format("获取${table.comment!}的级联对象: %s ",JSONUtil.toJsonStr(cascaderResults)));
        return RestResult.ok(cascaderResults);
    }
    </#if>
}
</#if>