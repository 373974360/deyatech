package com.deyatech.common.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.deyatech.common.Constants;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>
 * 实体类跟数据库字段之间操作工具类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-18 11:02
 */
public class ColumnUtil {

    /**
     * 将object对象转为map对象，key值为对应的sql字段名
     *
     * @param object
     * @return
     */
    public static Map<String, Object> objectToColumnMap(Object object) {
        Map<String, Object> map = MapUtil.newHashMap();
        if (ObjectUtil.isNotNull(object)) {
            Map<String, Method> methodMap = MapUtil.newHashMap();
            Field[] allFields = getAllFields(object);
            Method[] allMethods = getAllMethods(object);
            for (Method method : allMethods) {
                if (method.getName().toLowerCase().indexOf(Constants.METHOD_GET_PREX) >= 0) {
                    methodMap.put(method.getName().toLowerCase(), method);
                }
            }
            for (Field field : allFields) {
                if (!Constants.SERIAL_VERSION_UID.toLowerCase().equals(field.getName().toLowerCase())) {
                    String mapKey = getColumnNameByField(field);
                    if (StrUtil.isBlank(mapKey)) {
                        break;
                    } else {
                        Method method = methodMap.get(Constants.METHOD_GET_PREX.concat(field.getName()).toLowerCase());
                        if (ObjectUtil.isNotNull(method)) {
                            Object invoke = null;
                            try {
                                invoke = method.invoke(object);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            if (null != invoke) {
                                map.put(mapKey, invoke);
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * 返回对象的属性名对应的数据库字段名
     *
     * @param property
     * @param object
     * @return
     */
    public static String propertyNameToColumnName(String property, Object object) {
        String column = null;
        if (StrUtil.isNotBlank(property)) {
            Field[] allFields = getAllFields(object);
            for (Field field : allFields) {
                if (property.toLowerCase().equals(field.getName().toLowerCase())) {
                    column = getColumnNameByField(field);
                }
            }
        }
        return column;
    }

    /**
     * 替换字符串中所有的属性名为数据库字段名
     *
     * @param property
     * @param object
     * @return
     */
    public static String replacePropertyToColumn(String property, Object object) {
        if (StrUtil.isNotBlank(property)) {
            Field[] allFields = getAllFields(object);
            for (Field field : allFields) {
                String fieldName = field.getName();
                String columnName = getColumnNameByField(field);
                if (StrUtil.isBlank(columnName)) {
                    columnName = fieldName;
                }
                property = property.replaceAll(fieldName, columnName);
            }
        }
        return property;
    }


    /**
     * 获取对象的所有属性
     *
     * @param object
     * @return
     */
    private static Field[] getAllFields(Object object) {
        Class<?> objectClass = object.getClass();
        Class<?> superclass = object.getClass().getSuperclass();
        Field[] fields = objectClass.getDeclaredFields();
        Field[] superClassFields = superclass.getDeclaredFields();
        fields = ArrayUtil.addAll(fields, superClassFields);
        return fields;
    }

    /**
     * 获取对象的所有方法
     *
     * @param object
     * @return
     */
    private static Method[] getAllMethods(Object object) {
        Class<?> objectClass = object.getClass();
        Class<?> superclass = object.getClass().getSuperclass();
        Method[] methods = objectClass.getMethods();
        Method[] superclassMethods = superclass.getMethods();
        methods = ArrayUtil.addAll(methods, superclassMethods);
        return methods;
    }

    /**
     * 根据对象反射属性获取数据库字段名
     *
     * @param field
     * @return
     */
    private static String getColumnNameByField(Field field) {
        TableField tableField = field.getAnnotation(TableField.class);
        TableId tableId = field.getAnnotation(TableId.class);
        if (ObjectUtil.isNotNull(tableField)) {
            return tableField.value();
        } else if (ObjectUtil.isNotNull(tableId)) {
            return tableId.value();
        }
        return null;
    }

}
