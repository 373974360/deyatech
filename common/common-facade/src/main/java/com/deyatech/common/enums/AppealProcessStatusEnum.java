package com.deyatech.common.enums;

/**
 * 描述：信件状态的枚举类型
 *
 * @Author: MaChaoWei
 * @Date: 2019/10/29 11:10
 */
public enum AppealProcessStatusEnum implements IEnums<Integer>  {
    P1(1,"受理信件"),
    P2(2,"不予受理"),
    P3(3,"置为无效"),
    P4(4,"申请延期"),
    P5(5,"延期审核"),
    P6(6,"信件转办"),
    P7(7,"回复信件"),
    P8(8,"退回信件"),
    P9(9,"发布信件");

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
