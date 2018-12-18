package ${package.Controller};

import ${package.Entity}.${entity};
import ${package.Entity?replace("entity","vo")}.${entity}Vo;
import ${package.Service}.${entity}Service;
import org.litisn.common.entity.RestResult;
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
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@Slf4j
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    ${entity}Service ${entity?uncap_first}Service;

    /**
     * 单个保存或者更新${table.comment!}
     *
     * @param ${entity?uncap_first}
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public RestResult saveOrUpdate(${entity} ${entity?uncap_first}) {
        log.info("保存或者更新${table.comment!}:s%", JSONUtil.toJsonStr(${entity?uncap_first}));
        boolean result = ${entity?uncap_first}Service.saveOrUpdate(${entity?uncap_first});
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新${table.comment!}
     *
     * @param ${entity?uncap_first}List
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    public RestResult saveOrUpdateBatch(Collection<${entity}> ${entity?uncap_first}List) {
        log.info("批量保存或者更新${table.comment!}:s%", JSONUtil.toJsonStr(${entity?uncap_first}List));
        boolean result = ${entity?uncap_first}Service.saveOrUpdateBatch(${entity?uncap_first}List);
        return RestResult.ok(result);
    }

    /**
     * 根据ID逻辑删除${table.comment!}
     *
     * @param id
     * @return
     */
    @PostMapping("/removeById")
    public RestResult removeById(Serializable id) {
        log.info("根据id删除${table.comment!}:s%", id);
        boolean result = ${entity?uncap_first}Service.removeById(id);
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
        log.info("根据id批量删除${table.comment!}:s%", JSONUtil.toJsonStr(ids));
        boolean result = ${entity?uncap_first}Service.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据ID获取${table.comment!}
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public RestResult getById(Serializable id) {
        ${entity} ${entity?uncap_first} = ${entity?uncap_first}Service.getById(id);
        ${entity}Vo ${entity?uncap_first}Vo = ${entity?uncap_first}Service.setVoProperties(${entity?uncap_first});
        log.info("根据id获取${table.comment!}：s%", JSONUtil.toJsonStr(${entity?uncap_first}Vo));
        return RestResult.ok(${entity?uncap_first}Vo);
    }

    /**
     * 根据${entity}对象属性检索所有${table.comment!}
     *
     * @param ${entity?uncap_first}
     * @return
     */
    @GetMapping("/listByBean")
    public RestResult listByBean(${entity} ${entity?uncap_first}) {
        Collection<${entity}> ${entity?uncap_first}s = ${entity?uncap_first}Service.listByBean(${entity?uncap_first});
        Collection<${entity}Vo> ${entity?uncap_first}Vos = ${entity?uncap_first}Service.setVoProperties(${entity?uncap_first}s);
        log.info("根据${entity}对象属性检索所有${table.comment!}:s%",JSONUtil.toJsonStr(${entity?uncap_first}Vos));
        return RestResult.ok(${entity?uncap_first}Vos);
    }

    /**
     * 根据${entity}对象属性分页检索${table.comment!}
     *
     * @param ${entity?uncap_first}
     * @return
     */
    @GetMapping("/pageByBean")
    public RestResult pageByBean(${entity} ${entity?uncap_first}) {
        IPage ${entity?uncap_first}s = ${entity?uncap_first}Service.pageByBean(${entity?uncap_first});
        ${entity?uncap_first}s.setRecords(${entity?uncap_first}Service.setVoProperties(${entity?uncap_first}s.getRecords()));
        log.info("根据${entity}对象属性分页检索${table.comment!}:s%",JSONUtil.toJsonStr(${entity?uncap_first}s));
        return RestResult.ok(${entity?uncap_first}s);
    }
}
</#if>
