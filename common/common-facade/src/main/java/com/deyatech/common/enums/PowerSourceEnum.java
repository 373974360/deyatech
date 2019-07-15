package com.deyatech.common.enums;

/**
 * 权力来源
 * @Author ycx.
 */
public enum PowerSourceEnum implements IEnums<String> {
    /**
     * 法定本级行使
     */
    C01("01","法定本级行使"),
    /**
     * 国务院下放
     */
    C02("02","国务院下放"),
    /**
     * 委托本级行使
     */
    C03("03","委托本级行使"),
    /**
     * 下放本级行使
     */
    C04("04","下放本级行使"),
    /**
     * 法定授权行使
     */
    C05("05","法定授权行使");

    private String code;
    private String value;
    PowerSourceEnum(String code, String value) {
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
