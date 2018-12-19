package ${package.Controller};
<#assign lowerEntity = entity?uncap_first/>
<#assign entityVo = entity + "Vo"/>
<#assign lowerEntityVo = lowerEntity + "Vo"/>

import ${package.Entity}.${entity};
import ${package.Entity?replace("entity","vo")}.${entityVo};
import ${package.Service}.${entity}Service;
import org.land.common.entity.RestResult;
<#list table.fields as field>
    <#if field.name = "parent_id">
        <#assign isTree=true/>
    </#if>
</#list>
<#if isTree??>
import org.land.common.entity.CascaderResult;
import org.land.common.utils.CascaderUtil;
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
import ${superControllerClassPackage};
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
    public RestResult saveOrUpdate(${entity} ${lowerEntity}) {
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
    public RestResult saveOrUpdateBatch(Collection<${entity}> ${lowerEntity}List) {
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
    public RestResult removeBy${entity}(${entity} ${lowerEntity}) {
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
    public RestResult removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除${table.comment!}: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = ${lowerEntity}Service.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据ID获取${table.comment!}
     *
     * @param ${lowerEntity}
     * @return
     */
    @GetMapping("/getBy${entity}")
    public RestResult getBy${entity}(${entity} ${lowerEntity}) {
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
    @GetMapping("/listByBean")
    public RestResult listByBean(${entity} ${lowerEntity}) {
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
    @GetMapping("/pageByBean")
    public RestResult pageByBean(${entity} ${lowerEntity}) {
        IPage ${lowerEntity}s = ${lowerEntity}Service.pageByBean(${lowerEntity});
        ${lowerEntity}s.setRecords(${lowerEntity}Service.setVoProperties(${lowerEntity}s.getRecords()));
        log.info(String.format("根据${entity}对象属性分页检索${table.comment!}: %s ",JSONUtil.toJsonStr(${lowerEntity}s)));
        return RestResult.ok(${lowerEntity}s);
    }

    <#if isTree??>
    /**
     * 获取${table.comment!}的tree对象
     *
     * @return
     */
    @GetMapping("/getTree")
    public RestResult get${entity}Tree() {
        Collection<${entityVo}> ${lowerEntity}Tree = ${lowerEntity}Service.get${entity}Tree();
        log.info(String.format("获取${table.comment!}的tree对象: %s ",JSONUtil.toJsonStr(${lowerEntity}Tree)));
        return RestResult.ok(${lowerEntity}Tree);
    }

    /**
     * 获取${table.comment!}的级联对象
     *
     * @param id
     * @return
     */
    @GetMapping("/getCascader")
    public RestResult getCascader(String id) {
        Collection<${entityVo}> ${lowerEntityVo}s = ${lowerEntity}Service.get${entity}Tree();
        List<CascaderResult> cascaderResults = CascaderUtil.getResult("Id", "Name","TreePosition", id, ${lowerEntityVo}s);
        log.info(String.format("获取${table.comment!}的级联对象: %s ",JSONUtil.toJsonStr(cascaderResults)));
        return RestResult.ok(cascaderResults);
    }
    </#if>
}
</#if>
