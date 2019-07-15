package com.deyatech.common.enums;

/**
 * 年审或年检次数
 * @Author ycx.
 */
public enum YearReviewInspectionNumEnum implements IEnums<Integer> {
    /**
     * 1年一次
     */
    ONE_YEAR_ONCE(1,"1年一次"),
    /**
     * 2年一次
     */
    TWO_YEAR_ONCE(2,"2年一次"),
    /**
     * 3年一次
     */
    THREE_YEAR_ONCE(3,"3年一次"),
    /**
     * 4年一次
     */
    FOUR_YEAR_ONCE(4,"4年一次"),
    /**
     * 5年一次
     */
    FIVE_YEAR_ONCE(5,"5年一次");

    private Integer code;
    private String value;
    YearReviewInspectionNumEnum(Integer code, String value) {
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
