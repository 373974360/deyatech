package com.deyatech.common.enums;

/**
 * @author doukang
 * @description 整改状态
 * @date 2019/11/11 17:22
 */
public enum EvaluationReformStatusEnum implements IEnums<Integer> {

    Default(0, "默认"),
    Changing(1, "整改中"),
    Changed(2, "整改完成"),
    Unchanged(3, "无法整改");

    private Integer code;

    private String value;

    EvaluationReformStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
