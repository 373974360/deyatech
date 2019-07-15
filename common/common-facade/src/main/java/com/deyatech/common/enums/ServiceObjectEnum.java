package com.deyatech.common.enums;

/**
 * 服务对象
 * @Author ycx.
 */
public enum ServiceObjectEnum implements IEnums<Integer> {
    /**
     * 自然人
     */
    NATURAL_PERSON(1,"自然人"),
    /**
     * 法人
     */
    LEGAL_PERSON(2,"法人"),
    /**
     * 其他组织
     */
    OTHER_ORGANIZATIONS(3,"其他组织");

    private Integer code;
    private String value;
    ServiceObjectEnum(Integer code, String value) {
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
