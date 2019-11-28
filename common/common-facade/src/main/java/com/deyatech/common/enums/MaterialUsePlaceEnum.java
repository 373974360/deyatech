package com.deyatech.common.enums;

/**
 * 站点材料使用地方
 * @Author ycx.
 */
public enum MaterialUsePlaceEnum implements IEnums<String> {
    /**
     * 网站配置
     */
    RESOURCE_SETTING("resource_setting","网站配置"),
    /**
     * 访谈模型
     */
    INTERVIEW_MODEL("interview_model","访谈模型"),
    /**
     * 内容模板
     */
    STATION_TEMPLATE("station_template","内容模板"),
    /**
     * 未使用
     */
    UNUSED("unused", "未使用"),


    /**
     * 未知
     */
    UNKNOWN("unknown", "未知");

    private String code;
    private String value;
    MaterialUsePlaceEnum(String code, String value) {
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
