package ${package.Service};
<#assign lowerEntity = entity?uncap_first/>
<#assign entityVo = entity + "Vo"/>
<#assign lowerEntityVo = lowerEntity + "Vo"/>

import ${package.Entity}.${entity};
import ${package.Entity?replace("entity","vo")}.${entityVo};
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  ${table.comment!} 服务类
 * </p>
 *
 * @Author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${entity}${table.serviceName} extends ${superServiceClass}<${entity}> {
<#list table.fields as field>
    <#if field.name = "parent_id">
        <#assign isTree=true/>
    </#if>
</#list>
<#if isTree??>

    /**
     * 根据${entity}对象属性检索${table.comment!}的tree对象
     *
     * @param ${lowerEntity}
     * @return
     */
    Collection<${entityVo}> get${entity}Tree(${entity} ${lowerEntity});
</#if>

    /**
     * 单个将对象转换为vo${table.comment!}
     *
     * @param ${lowerEntity}
     * @return
     */
    ${entityVo} setVoProperties(${entity} ${lowerEntity});

    /**
     * 批量将对象转换为vo${table.comment!}
     *
     * @param ${lowerEntity}s
     * @return
     */
    List<${entityVo}> setVoProperties(Collection ${lowerEntity}s);
}
</#if>
