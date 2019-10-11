package com.deyatech.common.enums;

/**
 * 栏目内容权限
 *
 * @Author ycx.
 */
public enum TemplateAuthority implements IEnums<String> {
    /**
     * 全部
     */
    ALL("all","全部"),
    /**
     * 部门
     */
    DEPARTMENT("department","部门"),
    /**
     * 用户
     */
    USER("user","用户");

    private String code;
    private String value;
    TemplateAuthority(String code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public String getCode() {
        return this.code;
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
