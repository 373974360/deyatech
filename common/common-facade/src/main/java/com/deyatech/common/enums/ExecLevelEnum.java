package com.deyatech.common.enums;

/**
 * 行使层级
 * @Author ycx.
 */
public enum ExecLevelEnum implements IEnums<String> {
    /**
     * 乡(镇)级
     */
    TOWN_LEVEL("01","乡(镇)级"),
    /**
     * 区(县)级
     */
    COUNTY_LEVEL("02","区(县)级"),
    /**
     * 市级
     */
    CITY_LEVEL("03","市级"),
    /**
     * 省级
     */
    PROVINCE_LEVEL("04","省级"),
    /**
     * 国家级
     */
    NATION_LEVEL("05","国家级");

    private String code;
    private String value;
    ExecLevelEnum(String code, String value) {
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
