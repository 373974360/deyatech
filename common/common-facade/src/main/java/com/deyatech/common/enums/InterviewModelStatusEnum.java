package com.deyatech.common.enums;

/**
 * <p>
 * 访谈模型状态枚举类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:28
 */
public enum InterviewModelStatusEnum implements IEnums<Integer> {
    TRAILER(0, "预告"),
    LIVE(1, "直播"),
    PAST(2, "往期");
    private Integer code;
    private String value;
    InterviewModelStatusEnum(Integer code, String value) {
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
