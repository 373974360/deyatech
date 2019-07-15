package com.deyatech.common.enums;

/**
 * @Description: 认证类型
 * @Author: songshanshan
 * @Date: 2019/5/27 13:52
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public enum AuthTypeEnum implements IEnums<Integer> {

    GZHREGULAR(1,"GzhRegular"),

    SCANAUTH(2,"ScanAuth"),

    APPREGULAR(3,"AppRegular");


    private Integer code;
    private String value;

    AuthTypeEnum(final Integer code, final String value) {
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

