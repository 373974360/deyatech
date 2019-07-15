package com.deyatech.common.enums;

/**
 * <p>
 * 抽号号码状态枚举类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:28
 */
public enum CallStatusEnum implements IEnums<Integer> {

    /**
     * 表示已过期
     */
    EXPIRED(4, "已过期"),
    /**
     * 表示已评价
     */
    COMMENTED(3, "已评价"),
    /**
     * 表示已受理
     */
    ACCEPTED(2, "已受理"),
    /**
     * 表示已叫号
     */
    CALLED(1, "已叫号"),
    /**
     * 表示等待叫号
     */
    WATTING(0, "等待叫号");


    private Integer code;
    private String value;

    CallStatusEnum(final Integer code, final String value) {
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
