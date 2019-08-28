package com.deyatech.common.enums;

/**
 * <p>
 * 访谈嘉宾类型枚举类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:28
 */
public enum InterviewGuestTypeEnum implements IEnums<Integer> {
    HOST(1, "主持人"),
    GUEST(2, "嘉宾");
    private Integer code;
    private String value;
    InterviewGuestTypeEnum(Integer code, String value) {
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
