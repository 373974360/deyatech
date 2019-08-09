package com.deyatech.common.enums;

/**
 * 水印类型枚举
 *
 * @author ycx
 */
public enum WaterMarkTypeEnum implements IEnums<Integer> {
    /**
     * 表示图片
     */
    PICTURE(1, "图片"),
    /**
     * 表示文字
     */
    WORD(2, "文字");

    private Integer code;
    private String value;

    WaterMarkTypeEnum(final Integer code, final String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
