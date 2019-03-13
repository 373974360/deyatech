package com.deyatech.common.enums;

/**
 * <p>
 * 角色类型枚举类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:28
 */
public enum RoleTypeEnum implements IEnums<Integer> {

    SERVICE(1, "业务角色"),
    SYSTEM(2, "系统角色");
    Integer code;
    String value;

    RoleTypeEnum(final Integer code, final String value) {
        this.code = code;
        this.value = value;
    }


    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
