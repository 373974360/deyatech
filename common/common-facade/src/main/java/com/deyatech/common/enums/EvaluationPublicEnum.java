package com.deyatech.common.enums;

/**
 * 是否公开
 * @author: csm
 */
public enum EvaluationPublicEnum implements IEnums<Integer> {

    PUBLIC(1,"公开"),
    NOT_PUBLIC(2,"不公开");

    private Integer code;
    private String value;
    EvaluationPublicEnum(Integer code, String value) {
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
