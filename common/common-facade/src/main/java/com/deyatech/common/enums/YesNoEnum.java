package com.deyatech.common.enums;

/**
 * <p>
 * 是否枚举类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:28
 */
public enum YesNoEnum implements IEnums<Integer> {

    /**
     * 表示是
     */
    YES(1, "是"),
    /**
     * 表示否
     */
    NO(0, "否");

    private Integer code;
    private String value;

    YesNoEnum(final Integer code, final String value) {
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
