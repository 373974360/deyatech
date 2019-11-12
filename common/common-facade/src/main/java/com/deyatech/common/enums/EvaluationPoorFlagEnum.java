package com.deyatech.common.enums;

/**
 * @author doukang
 * @description 差评有效标记
 * @date 2019/11/11 17:22
 */
public enum  EvaluationPoorFlagEnum implements IEnums<Integer> {

    Default(0, "默认"),
    Valid(1, "有效"),
    Invalid(2, "无效");

    private Integer code;

    private String value;

    EvaluationPoorFlagEnum(Integer code, String value) {
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
