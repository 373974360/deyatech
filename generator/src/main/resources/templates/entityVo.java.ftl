package ${package.Entity?replace("entity","vo")};
<#assign lowerEntity = entity?uncap_first/>
<#assign entityVo = entity + "Vo"/>
<#assign lowerEntityVo = lowerEntity + "Vo"/>
<#list table.fields as field>
    <#if field.name = "parent_id">
        <#assign isTree=true/>
    </#if>
</#list>

import ${package.Entity}.${entity};
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
</#if>
<#if isTree??>
import java.util.List;
</#if>

/**
 * <p>
 * ${table.comment!}扩展对象
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
    <#else>
@EqualsAndHashCode(callSuper = false)
    </#if>
@Accessors(chain = true)
</#if>
<#if swagger2>
@ApiModel(value = "${table.comment!}扩展对象", description = "${table.comment!}扩展对象", parent = ${entity}.class)
</#if>
public class ${entityVo} extends ${entity} {
<#if isTree??>
    @ApiModelProperty(value = "树结构中显示的名称", dataType = "String")
    private String label;

    @ApiModelProperty(value = "树结构中子节点对象集合", dataType = "List<${entityVo}>")
    private List<${entityVo}> children;

    @ApiModelProperty(value = "树结构中的层级", dataType = "String")
    private Integer level;
</#if>
}
