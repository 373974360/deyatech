package com.deyatech.common.enums;

/**
 * 办件类型
 * @Author ycx.
 */
public enum DealTypeEnum implements IEnums<String> {
    /**
     * 承诺件
     */
    C01("01","承诺件"),
    /**
     * 承诺件
     */
    C02("02","即办件");

    private String code;
    private String value;
    DealTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public String getCode() {
        return this.code;
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
