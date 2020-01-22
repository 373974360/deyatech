package com.deyatech.common.enums;

public enum StationUserTypeEnum implements IEnums<Integer> {
    /**
     * 站点管理员
     */
    STATION_ADMIN(1,"站点管理员"),
    /**
     * 普通用户
     */
    NORMAL_USER(2,"站点管理员"),
    /**
     * 机构管理员
     */
    AGENCY_ADMIN(3,"机构管理员");

    private Integer code;
    private String value;

    StationUserTypeEnum(final Integer code, final String value) {
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
