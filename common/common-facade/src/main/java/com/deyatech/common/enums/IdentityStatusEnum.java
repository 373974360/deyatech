package com.deyatech.common.enums;

/**
 * @Description:
 * @Author: songshanshan
 * @Date: 2019/6/3 10:39
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public enum IdentityStatusEnum  implements IEnums<Integer> {
    /**
     * 表示男性
     */
    AGENT(1, "代办人"),
    /**
     * 表示女性
     */
    SHAREHOLDER(2, "股东");


    private Integer code;
    private String value;

    IdentityStatusEnum(final Integer code, final String value) {
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
