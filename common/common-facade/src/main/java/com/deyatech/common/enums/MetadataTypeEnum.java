package com.deyatech.common.enums;

/**
 * 元数据类型
 */
public enum MetadataTypeEnum implements IEnums<Integer> {

    BASIC(1, "基本类型"),
    COMPOSITE(2, "复合类型");

    int code;

    String value;

    MetadataTypeEnum(int code, String value) {
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
