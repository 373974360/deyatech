package com.deyatech.common.enums;


/**
 * <p>
 * 性别枚举类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:28
 */
public enum GenderEnum implements IEnums<Integer> {
    /**
     * 表示男性
     */
    MAN(1, "男"),
    /**
     * 表示女性
     */
    WOMAN(0, "女"),
    /**
     * 标识未知
     */
    UNKNOWN(2, "未知");


    private Integer code;
    private String value;

    GenderEnum(final Integer code, final String value) {
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
