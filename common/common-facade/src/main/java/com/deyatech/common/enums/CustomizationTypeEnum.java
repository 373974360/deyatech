package com.deyatech.common.enums;

/**
 * 自定义功能类型
 *
 * @author ycx
 */
public enum CustomizationTypeEnum implements IEnums<String> {
    /**
     * 栏目表头
     */
    TABLE_HEAD_CATALOG("TABLE_HEAD_CATALOG","栏目表头"),
    /**
     * 内容表头
     */
    TABLE_HEAD_CONTENT("TABLE_HEAD_CONTENT","内容表头");

    private String code;
    private String value;

    CustomizationTypeEnum(String code, String value) {
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

