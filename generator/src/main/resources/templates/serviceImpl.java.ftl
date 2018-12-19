package ${package.ServiceImpl};
<#assign lowerEntity = entity?uncap_first/>
<#assign entityVo = entity + "Vo"/>
<#assign lowerEntityVo = lowerEntity + "Vo"/>
<#list table.fields as field>
    <#if field.name = "parent_id">
        <#assign isTree=true/>
    </#if>
</#list>

import ${package.Entity}.${entity};
import ${package.Entity?replace("entity","vo")}.${entityVo};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${entity}${table.serviceName};
import ${superServiceImplClassPackage};
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
<#if isTree??>
import org.land.common.Constants;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ObjectUtil;
</#if>
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
<#if isTree??>

    /**
     * 根据${entity}对象属性检索${table.comment!}的tree对象
     *
     * @return
     */
    @Override
    public Collection<${entityVo}> get${entity}Tree() {
        List<${entityVo}> ${lowerEntity}Vos = setVoProperties(super.list());
        List<${entityVo}> root${entity}s = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(${lowerEntityVo}s)) {
            for (${entityVo} ${lowerEntityVo} : ${lowerEntityVo}s) {
                ${lowerEntityVo}.setLabel(${lowerEntityVo}.getName());
                if(StrUtil.isNotBlank(${lowerEntityVo}.getTreePosition())){
                    String[] split = ${lowerEntityVo}.getTreePosition().split(Constants.DEFAULT_TREE_POSITION_SPLIT);
                    ${lowerEntityVo}.setLevel(split.length);
                }else{
                    ${lowerEntityVo}.setLevel(Constants.DEFAULT_ROOT_LEVEL);
                }
                if (ObjectUtil.equal(${lowerEntityVo}.getParentId(), Constants.DEFAULT_PARENT_ROOT)) {
                    root${entity}s.add(${lowerEntityVo});
                }
                for (${entityVo} childVo : ${lowerEntityVo}s) {
                    if (ObjectUtil.equal(childVo.getParentId(), ${lowerEntityVo}.getId())) {
                        if (ObjectUtil.isNull(${lowerEntityVo}.getChildren())) {
                            List<${entityVo}> children = CollectionUtil.newArrayList();
                            children.add(childVo);
                            ${lowerEntityVo}.setChildren(children);
                        } else {
                            ${lowerEntityVo}.getChildren().add(childVo);
                        }
                    }
                }
            }
        }
        return root${entity}s;
    }
</#if>

    /**
     * 单个将对象转换为vo${table.comment!}
     *
     * @param ${lowerEntity}
     * @return
     */
    @Override
    public ${entityVo} setVoProperties(${entity} ${lowerEntity}){
        ${entityVo} ${lowerEntity}Vo = new ${entityVo}();
        BeanUtil.copyProperties(${lowerEntity}, ${lowerEntity}Vo);
        return ${lowerEntity}Vo;
    }

    /**
     * 批量将对象转换为vo${table.comment!}
     *
     * @param ${lowerEntity}s
     * @return
     */
    @Override
    public List<${entityVo}> setVoProperties(Collection ${lowerEntity}s){
        List<${entityVo}> ${lowerEntity}Vos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(${lowerEntity}s)) {
            for (Object ${lowerEntity} : ${lowerEntity}s) {
                ${entityVo} ${lowerEntity}Vo = new ${entityVo}();
                BeanUtil.copyProperties(${lowerEntity}, ${lowerEntity}Vo);
                ${lowerEntity}Vos.add(${lowerEntity}Vo);
            }
        }
        return ${lowerEntity}Vos;
    }
}
</#if>
