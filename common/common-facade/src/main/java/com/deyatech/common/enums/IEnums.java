package com.deyatech.common.enums;

import java.io.Serializable;

/**
 * <p>
 * code和value枚举类型接口,提供方法返回value和code
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 18:20
 */
public interface IEnums<ValueType extends Serializable> {
    /**
     * 名称
     *
     * @return
     */
    String getValue();

    /**
     * 数据
     *
     * @return
     */
    ValueType getCode();
}
