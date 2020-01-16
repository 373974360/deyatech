package com.deyatech.common.enums;

/**
 * @Description 寄送类型
 * @Author doukang
 * @Date 2020-01-13 19:04
 */
public enum AcceptDeliveryTypeEnum implements IEnums<Integer> {

    Hand(1, "交件"),
    Take(2, "取件");

    private Integer code;

    private String value;

    AcceptDeliveryTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
