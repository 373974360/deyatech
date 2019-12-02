package com.deyatech.common.enums;

/**
 * 描述：信件状态的枚举类型
 *
 * @Author: MaChaoWei
 * @Date: 2019/10/29 11:10
 */
public enum AppealStatusEnum implements IEnums<Integer>  {
    A1(0,"待处理"),
    A2(1,"处理中"),
    A3(2,"待审核"),
    A4(3,"已办结");

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
