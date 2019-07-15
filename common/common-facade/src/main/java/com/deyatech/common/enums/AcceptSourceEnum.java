package com.deyatech.common.enums;

/**
 * 办理形式
 * @Author ycx.
 */
public enum AcceptSourceEnum implements IEnums<Integer> {
    /**
     * 通用
     */
    C0(0,"通用"),
    /**
     * 一网
     */
    C1(1,"一网"),
    /**
     * 一窗
     */
    C2(2,"一窗"),
    /**
     * 审批
     */
    C3(3,"审批"),
    /**
     * 小程序
     */
    C4(4,"小程序"),
    /**
     * 市版本审批
     */
    C5(5,"市版本审批");

    private Integer code;
    private String value;
    AcceptSourceEnum(Integer code, String value) {
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
