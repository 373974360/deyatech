package com.deyatech.common.enums;
/**
 * <p>
 * 访谈模型状发布举类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:28
 */
public enum InterviewModelPublishEnum implements IEnums<Integer> {
    PUBLISHED(1, "已发布"),
    UNPUBLISHED(2, "未发布");
    private Integer code;
    private String value;
    InterviewModelPublishEnum(Integer code, String value) {
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
