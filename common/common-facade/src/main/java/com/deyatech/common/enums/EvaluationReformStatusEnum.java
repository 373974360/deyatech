package com.deyatech.common.enums;

/**
 * @author doukang
 * @description 整改状态
 * @date 2019/11/11 17:22
 */
public enum EvaluationReformStatusEnum implements IEnums<Integer> {

    Default(0, "默认"),
    PoorInvalidApply(10, "差评无效申请"),
    PoorInvalidPass(11,  "差评无效审核通过"),
    PoorInvalidReject(12, "差评无效审核驳回"),
    Changing(20, "整改"),
    Changed(21, "整改完成"),
    UnchangedApply(220, "无法整改申请"),
    UnchangedPass(221, "无法整改审核通过"),
    UnchangedReject(222, "无法整改审核驳回"),
    Delay(3, "延期"),
    Revisit(999, "回访");

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
