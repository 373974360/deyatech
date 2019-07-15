package com.deyatech.common.enums;

/**
 * <p>
 * 抽号号码状态枚举类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:28
 */
public enum PreordeStatusEnum implements IEnums<Integer> {

    /**
     * 表示已取消
     */
    CANCELLED(3, "已取消"),
    /**
     * 表示未赴约
     */
    UNDATE(2, "未赴约"),
    /**
     * 表示已赴约
     */
    DATED(1, "已赴约"),
    /**
     * 表示等待赴约
     */
    WATTING(0, "等待赴约");


    private Integer code;
    private String value;

    PreordeStatusEnum(final Integer code, final String value) {
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
