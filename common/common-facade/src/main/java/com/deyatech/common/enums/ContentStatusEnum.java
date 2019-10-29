package com.deyatech.common.enums;

/**
 * 内容发布状态
 *
 * @author ycx
 */
public enum ContentStatusEnum implements IEnums<Integer> {
    /**
     * 已发布
     */
    PUBLISH(2,"已发布"),
    /**
     * 待审
     */
    VERIFY(3,"待审"),
    /**
     * 撤销
     */
    CANCEL(4, "撤销"),
    /**
     * 退稿
     */
    REJECT(5, "退稿"),
    /**
     * 草稿
     */
    DRAFT(1,"草稿"),
    /**
     * 回收站
     */
    RECYCLE(-1,"回收站");


    private Integer code;
    private String value;

    ContentStatusEnum(final Integer code, final String value) {
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
