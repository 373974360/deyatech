package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Entity?replace("entity","vo")}.${entity}Vo;
import ${superServiceClassPackage};
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

    /**
     * 单个将对象转换为vo${table.comment!}
     *
     * @param ${entity?uncap_first}
     * @return
     */
    ${entity}Vo setVoProperties(${entity} ${entity?uncap_first});

    /**
     * 批量将对象转换为vo${table.comment!}
     *
     * @param ${entity?uncap_first}s
     * @return
     */
    List<${entity}Vo> setVoProperties(Collection ${entity?uncap_first}s);
}
</#if>
