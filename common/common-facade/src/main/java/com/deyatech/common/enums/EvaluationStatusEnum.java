package com.deyatech.common.enums;

/**
 * 审核状态
 * @author: csm
 */
public enum EvaluationStatusEnum implements IEnums<Integer> {

    NO_NEED_AUDIT(1,"无需审核"),
    AUDITED(2,"已审核"),
    UNAUDITED(3,"未审核");

    private Integer code;
    private String value;
    EvaluationStatusEnum(Integer code, String value) {
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
