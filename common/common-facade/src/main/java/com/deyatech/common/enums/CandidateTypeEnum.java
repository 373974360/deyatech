package com.deyatech.common.enums;

/**
 * @author doukang
 * @description 备选帐号类型
 * @date 2019/8/5 10:06
 */
public enum CandidateTypeEnum implements IEnums<Integer> {

    USER(1, "用户"),
    GROUP(2, "角色"),
    DEPARTMENT(3, "部门");

    int code;
    String value;

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    CandidateTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
