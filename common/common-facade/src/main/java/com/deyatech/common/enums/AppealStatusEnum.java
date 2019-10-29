package com.deyatech.common.enums;

/**
 * 描述：信件状态的枚举类型
 *
 * @Author: MaChaoWei
 * @Date: 2019/10/29 11:10
 */
public enum AppealStatusEnum implements IEnums<Integer>  {
    A1(1,"未处理"),
    A2(2,"已受理"),
    A3(3,"不予受理"),
    A4(4,"办理中"),
    A5(5,"已办结"),
    A6(6,"无效件"),
    A7(7,"重复件"),
    A8(8,"退回件");

    private Integer code;
    private String value;
    AppealStatusEnum(Integer code, String value) {
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
