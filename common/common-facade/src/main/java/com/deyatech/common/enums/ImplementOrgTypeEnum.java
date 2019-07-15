package com.deyatech.common.enums;

/**
 * 实施主体性质
 * @Author ycx.
 */
public enum ImplementOrgTypeEnum implements IEnums<String> {
    /**
     * 法定机关
     */
    C01("01","法定机关"),
    /**
     * 授权组织
     */
    C02("02","授权组织"),
    /**
     * 受委托组织
     */
    C03("03","受委托组织");

    private String code;
    private String value;
    ImplementOrgTypeEnum(String code, String value) {
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
