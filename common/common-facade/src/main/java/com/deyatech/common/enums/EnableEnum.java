package com.deyatech.common.enums;

/**
 * <p>
 * 是否可用枚举类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:28
 */
public enum EnableEnum implements IEnums<Integer> {

    /**
     * 表示有效(启用)
     */
    ENABLE(1, "启用"),
    /**
     * 表示失效(禁用)
     */
    DISABLE(0, "禁用"),
    /**
     * 表示已删除
     */
    DELETED(-1, "已删除");

    private Integer code;
    private String value;

    EnableEnum(final Integer code, final String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
