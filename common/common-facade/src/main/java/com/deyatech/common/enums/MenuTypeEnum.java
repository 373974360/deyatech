package com.deyatech.common.enums;

/**
 * <p>
 * 菜单类型枚举类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:28
 */
public enum MenuTypeEnum implements IEnums<Integer> {

    MENU(1, "菜单"),
    BUTTON(2, "按钮");

    Integer code = 1;
    String value;

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    MenuTypeEnum(final Integer code, final String value) {
        this.code = code;
        this.value = value;
    }
}
