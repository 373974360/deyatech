package com.deyatech.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.deyatech.common.entity.CascaderResult;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * element-ui 级联工具类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-19 20:58
 */
@Slf4j
public class CascaderUtil {

    public static List<CascaderResult> getResult(String valuePropertyName, String labelPropertyName, String treePositionPropertyName, Serializable disableId, Collection list) {
        List<CascaderResult> cascaderResults = CollectionUtil.newArrayList();
        try {
            if (CollectionUtil.isNotEmpty(list)) {
                for (Object object : list) {
                    CascaderResult cascaderResult = new CascaderResult();
                    Method[] methods = object.getClass().getMethods();
                    List<CascaderResult> cascaderResultChildren;
                    for (Method method : methods) {
                        if (method.getName().startsWith("get") && method.getName().indexOf("get" + valuePropertyName) >= 0) {
                            String id = (String) method.invoke(object);
                            if (ObjectUtil.equal(id, disableId)) {
                                cascaderResult.setDisabled(true);
                            }
                            cascaderResult.setValue(StrUtil.utf8Str(id));
                        }
                        if (method.getName().startsWith("get") && method.getName().indexOf("get" + labelPropertyName) >= 0) {
                            cascaderResult.setLabel(StrUtil.utf8Str(method.invoke(object)));
                        }
                        if (method.getName().startsWith("get") && method.getName().indexOf("get" + treePositionPropertyName) >= 0) {
                            cascaderResult.setTreePosition(StrUtil.utf8Str(method.invoke(object)));
                        }
                        if (method.getName().indexOf("getChildren") >= 0) {
                            List<Object> children = (List<Object>) method.invoke(object);
                            if (CollectionUtil.isNotEmpty(children)) {
                                cascaderResultChildren = getResult(valuePropertyName, labelPropertyName, treePositionPropertyName, disableId, children);
                                cascaderResult.setChildren(cascaderResultChildren);
                            }
                        }
                    }
                    cascaderResults.add(cascaderResult);
                }
            }
        } catch (Exception e) {
            log.error("转换级联结构失败", e);
        }
        return cascaderResults;
    }
}
