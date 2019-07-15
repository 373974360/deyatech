package com.deyatech.common.enums;

/**
 * 材料类型
 * @Author ycx.
 */
public enum MaterialsTypeEnum implements IEnums<String> {
    /**
     * 证件
     */
    C01("01","证件"),
    /**
     * 证明
     */
    C02("02","证明"),
    /**
     * 申请表
     */
    C03("03","申请表");

    private String code;
    private String value;
    MaterialsTypeEnum(String code, String value) {
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
