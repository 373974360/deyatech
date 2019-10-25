package com.deyatech.common.enums;

/**
 * 售卖方式
 * @Author csm
 */
public enum SellEnum implements IEnums<Integer> {

    /**
     * 批发
     */
    WHOLESALE(0,"批发"),
    /**
     * 市版本审批
     */
    RETAIL(1,"零售");

    private Integer code;
    private String value;
    SellEnum(Integer code, String value) {
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
