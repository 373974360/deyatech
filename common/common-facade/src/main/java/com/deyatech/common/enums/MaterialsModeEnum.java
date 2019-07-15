package com.deyatech.common.enums;

/**
 * 材料形式
 * @Author ycx.
 */
public enum MaterialsModeEnum implements IEnums<String> {
    /**
     * 原件
     */
    C01("01","原件"),
    /**
     * 复印件
     */
    C02("02","复印件"),
    /**
     * 原件或复印件
     */
    C03("03","原件或复印件"),
    /**
     * 纸质材料
     */
    C04("04","纸质材料"),
    /**
     * 电子材料
     */
    C05("05","电子材料");

    private String code;
    private String value;
    MaterialsModeEnum(String code, String value) {
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
