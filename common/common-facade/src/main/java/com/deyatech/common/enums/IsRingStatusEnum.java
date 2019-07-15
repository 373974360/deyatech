package com.deyatech.common.enums;


/**
 * @Description:
 * @Author: maxiaoqiang
 * @Date: 2019-04-25 16:21
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public enum IsRingStatusEnum implements IEnums<Integer> {
    /**
     * 表示发声
     */
    PHONATE(1,"发声"),

    /**
     * 表示不发声
     */
    SILENCE(0,"不发生");

    private Integer code;
    private String value;

    IsRingStatusEnum(final Integer code,final String value){
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
