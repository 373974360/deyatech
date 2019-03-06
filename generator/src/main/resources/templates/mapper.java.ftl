package ${package.Mapper};

import ${package.Entity}.${entity};
import com.deyatech.common.base.BaseMapper;

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @Author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
