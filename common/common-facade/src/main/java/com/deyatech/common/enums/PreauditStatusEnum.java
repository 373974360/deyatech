package com.deyatech.common.enums;

/**
 * 预审状态
 * @Author csm
 */
public enum PreauditStatusEnum implements IEnums<Integer> {

    /**
     * 等待预审
     */
    PENDING(1, "等待预审"),
    /**
     * 整改
     */
    REJECT(2, "整改"),
    /**
     * 通过审核
     */
    PASS(3, "通过审核"),

    /**
     * 不予受理
     */
    CLOSE(4, "不予受理");

    private Integer code;
    private String value;
    PreauditStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public Integer getCode() {
        return this.code;
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
