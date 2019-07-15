package com.deyatech.common.enums;

public enum ExpressCompanyEnum implements IEnums<String> {

    SFEXPRESS("SFEXPRESS", "顺丰速运", "SFEXPRESS"),
    EMS("EMS", "邮政EMS", "EMS"),
    YTO("YTO", "圆通速递", "YTO"),
    STO("STO", "申通快递", "STO"),
    YUNDA("YUNDA", "韵达快递", "YUNDA"),
    HTKY("HTKY", "百世快递", "HTKY"),
    ZTO("ZTO", "中通快递", "ZTO"),
    ZJS("ZJS", "宅急送", "ZJS"),
    TTKDEX("TTKDEX", "天天快递", "TTKDEX"),
    KYEXPRESS("KYEXPRESS", "跨越速运", "KYEXPRESS"),
    GTO("GTO", "国通快递", "GTO"),
    DEPPON("DEPPON", "德邦物流", "DEPPON");

    private String code;

    private String value;

    private String remark;

    ExpressCompanyEnum(String code, String value, String remark) {
        this.code = code;
        this.value = value;
        this.remark = remark;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }

    public static ExpressCompanyEnum getByRemark(String remark) {
        for (ExpressCompanyEnum company : values()) {
            if (company.getRemark().equals(remark)) {
                return company;
            }
        }
        return null;
    }
}
