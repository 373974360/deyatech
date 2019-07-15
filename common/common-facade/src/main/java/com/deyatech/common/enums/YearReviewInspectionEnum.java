package com.deyatech.common.enums;

/**
 * 年审或年检
 * @Author ycx.
 */
public enum YearReviewInspectionEnum implements IEnums<Integer> {
    /**
     * 年审
     */
    ONE(1,"年审"),
    /**
     * 年检
     */
    TWO(2,"年检"),
    /**
     * 无
     */
    THREE(3,"无");

    private Integer code;
    private String value;
    YearReviewInspectionEnum(Integer code, String value) {
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
