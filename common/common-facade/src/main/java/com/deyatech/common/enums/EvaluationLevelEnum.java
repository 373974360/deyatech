package com.deyatech.common.enums;

/**
 * 评价等级
 * @author: csm
 */
public enum EvaluationLevelEnum implements IEnums<String> {

    VERY_SATISFIED("5","非常满意"),
    SATISFIED("4","满意"),
    BASIC_SATISFIED("3","基本满意"),
    DISSATISFIED("2","不满意"),
    VERY_DISSATISFIED("1","非常不满意");

    private String code;
    private String value;
    EvaluationLevelEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public String getCode() {
        return this.code;
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
