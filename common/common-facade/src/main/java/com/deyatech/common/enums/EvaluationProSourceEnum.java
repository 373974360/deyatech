package com.deyatech.common.enums;

/**
 * 办件来源
 */
public enum EvaluationProSourceEnum implements IEnums<String> {

    Net("1", "网上申报"),
    City("2", "城市服务"),
    Self("3", "自助机"),
    Window("4", "大厅窗口"),
    CompWindow("6", "大厅综窗");

    private String code;

    private String value;

    EvaluationProSourceEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
