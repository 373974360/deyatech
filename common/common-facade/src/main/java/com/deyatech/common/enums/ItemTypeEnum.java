package com.deyatech.common.enums;

/**
 * 事项小类型（原事项类型）
 * @Author ycx.
 */
public enum ItemTypeEnum implements IEnums<String> {
    /**
     * 行政许可
     */
    XK("XK","行政许可"),
    /**
     * 公共服务
     */
    FW("FW","公共服务"),
    /**
     * 行政给付
     */
    JF("JF","行政给付"),
    /**
     * 行政征收
     */
    ZS("ZS","行政征收"),
    /**
     * 行政确认
     */
    QR("QR","行政确认"),
    /**
     * 行政奖励
     */
    JL("JL","行政奖励"),
    /**
     * 告知性备案
     */
    GB("GB","告知性备案"),
    /**
     * 审核性备案
     */
    SB("SB","审核性备案"),
    /**
     * 其他权利事项
     */
    QT("QT","其他权利事项"),
    /**
     * 其他
     */
    QQ("QQ","其他"),
    /**
     * 许可
     */
    SX("SX","许可");

    private String code;
    private String value;
    ItemTypeEnum(String code, String value) {
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
