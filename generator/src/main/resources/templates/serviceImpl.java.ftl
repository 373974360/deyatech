package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Entity?replace("entity","vo")}.${entity}Vo;
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${entity}${table.serviceName};
import ${superServiceImplClassPackage};
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @Author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${entity}${table.serviceName} {

    /**
     * 单个将对象转换为vo${table.comment!}
     *
     * @param ${entity?uncap_first}
     * @return
     */
    @Override
    public ${entity}Vo setVoProperties(${entity} ${entity?uncap_first}){
        ${entity}Vo ${entity?uncap_first}Vo = new ${entity}Vo();
        BeanUtil.copyProperties(${entity?uncap_first}, ${entity?uncap_first}Vo);
        return ${entity?uncap_first}Vo;
    }

    /**
     * 批量将对象转换为vo${table.comment!}
     *
     * @param ${entity?uncap_first}s
     * @return
     */
    @Override
    public List<${entity}Vo> setVoProperties(Collection ${entity?uncap_first}s){
        List<${entity}Vo> ${entity?uncap_first}Vos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(${entity?uncap_first}s)) {
            for (Object ${entity?uncap_first} : ${entity?uncap_first}s) {
                ${entity}Vo ${entity?uncap_first}Vo = new ${entity}Vo();
                BeanUtil.copyProperties(${entity?uncap_first}, ${entity?uncap_first}Vo);
                ${entity?uncap_first}Vos.add(${entity?uncap_first}Vo);
            }
        }
        return ${entity?uncap_first}Vos;
    }
}
</#if>
