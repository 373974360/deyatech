package com.deyatech.common.enums;

/**
 * 通办范围
 * @Author ycx.
 */
public enum GeneralRangeEnum implements IEnums<String> {
    /**
     * 全国
     */
    C01("01","全国"),
    /**
     * 全省
     */
    C02("02","全省"),
    /**
     * 跨省
     */
    C03("03","跨省"),
    /**
     * 全市
     */
    C04("04","全市"),
    /**
     * 跨市
     */
    C05("05","跨市"),
    /**
     * 全县
     */
    C06("06","全县"),
    /**
     * 跨县
     */
    C07("07","跨县"),
    /**
     * 全区
     */
    C08("08","全区"),
    /**
     * 跨区
     */
    C09("09","跨区");

    private String code;
    private String value;
    GeneralRangeEnum(String code, String value) {
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
