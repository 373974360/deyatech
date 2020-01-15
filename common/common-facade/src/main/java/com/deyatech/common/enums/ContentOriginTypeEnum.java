package com.deyatech.common.enums;

/**
 * 内容栏目关联状态
 *
 * @author ycx
 */
public enum ContentOriginTypeEnum implements IEnums<Integer> {
    /**
     * 原始添加
     */
    ADD(1, "原"),
    /**
     * 栏目聚合
     */
    AGGREGATION(2,"聚"),
    /**
     * 投递
     */
    DELIVER(3,"投"),
    /**
     * 引用
     */
    QUOTE(4,"引");

    private Integer code;
    private String value;

    ContentOriginTypeEnum(final Integer code, final String value) {
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
