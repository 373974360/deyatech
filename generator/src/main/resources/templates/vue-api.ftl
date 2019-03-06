import request from '@/router/axios';
<#assign lowerEntity = entity?uncap_first/>
export function get${entity}(id) {
    return request({
        url: '/manage/${package.ModuleName}/${lowerEntity}/getBy${entity}',
        method: 'get',
        params: {id}
    })
}

export function createOrUpdate${entity}(${lowerEntity}Vo){
    const data = ${lowerEntity}Vo;
    return request({
        url: '/manage/${package.ModuleName}/${lowerEntity}/saveOrUpdate',
        method: 'post',
        data
    })
}

export function del${entity}(id) {
    const data = {id};
    return request({
        url: '/manage/${package.ModuleName}/${lowerEntity}/removeBy${entity}',
        method: 'post',
        data
    })
}
export function del${entity}s(ids) {
    const data = {ids};
    return request({
        url: '/manage/${package.ModuleName}/${lowerEntity}/removeByIds',
        method: 'post',
        data
    })
}
<#list table.fields as field>
    <#if field.name = "parent_id">
        <#assign isTree=true/>
    </#if>
</#list>
<#if isTree??>
export function get${entity}Tree(query) {
    return request({
        url: '/manage/${package.ModuleName}/${lowerEntity}/getTree',
        method: 'get',
        params: query
    });
}

export function get${entity}Cascader(query) {
    return request({
        url: '/manage/${package.ModuleName}/${lowerEntity}/getCascader',
        method: 'get',
        params: query
    });
}

<#else>
export function get${entity}List(query) {
    return request({
        url: '/manage/${package.ModuleName}/${lowerEntity}/pageBy${entity}',
        method: 'get',
        params: query
    });
}
</#if>


