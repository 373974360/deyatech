package com.deyatech.common.enums;

/**
 * 描述：信件状态的枚举类型
 *
 * @Author: MaChaoWei
 * @Date: 2019/10/29 11:10
 */
public enum AppealProcessStatusEnum implements IEnums<Integer>  {
    P1(1,"转办"),
    P2(2,"回复"),
    P3(3,"发布"),
    P4(4,"退回"),
    P5(5,"判重"),
    P6(6,"置为无效"),
    P7(7,"延期"),
    P8(8,"不予受理");

    private Integer code;
    private String value;
    AppealProcessStatusEnum(Integer code, String value) {
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
