package com.deyatech.common.enums;

/**
 * 是否匿名
 * @author: csm
 */
public enum EvaluationAnonymityEnum implements IEnums<Integer> {

    ANONYMITY(1,"匿名"),
    NOT_ANONYMITY(2,"不匿名");

    private Integer code;
    private String value;
    EvaluationAnonymityEnum(Integer code, String value) {
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
