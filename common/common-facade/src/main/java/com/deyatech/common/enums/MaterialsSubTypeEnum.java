package com.deyatech.common.enums;

/**
 * 材料子类型
 * @Author ycx.
 */
public enum MaterialsSubTypeEnum implements IEnums<String> {
    /**
     * 第二代居民身份证
     */
    C0101("0101","第二代居民身份证"),
    /**
     * 营业执照
     */
    C0102("0102","营业执照"),
    /**
     * 不动产权证
     */
    C0103("0103","不动产权证"),
    /**
     * 驾驶员的从业资格证
     */
    C0104("0104","驾驶员的从业资格证"),
    /**
     * 社会团体法人登记证书
     */
    C0105("0105","社会团体法人登记证书"),
    /**
     * 户口本
     */
    C0106("0106","户口本"),
    /**
     * 居住证
     */
    C0107("0107","居住证"),
    /**
     * 机动车行驶证
     */
    C0108("0108","机动车行驶证"),
    /**
     * 机动车驾驶证
     */
    C0109("0109","机动车驾驶证"),
    /**
     * 道路运输经营许可证
     */
    C0110("0110","道路运输经营许可证"),
    /**
     * 房屋所有权证
     */
    C0111("0111","房屋所有权证"),
    /**
     * 护照
     */
    C0112("0112","护照"),
    /**
     * 房产证
     */
    C0113("0113","房产证"),
    /**
     * 个人所得税完税证明
     */
    C0201("0201","个人所得税完税证明"),
    /**
     * 监管协议备案
     */
    C0202("0202","监管协议备案"),
    /**
     * 社保证明
     */
    C0203("0203","社保证明"),
    /**
     * 纳税证明
     */
    C0204("0204","纳税证明"),
    /**
     * 个人照片
     */
    C0205("0205","个人照片");

    private String code;
    private String value;
    MaterialsSubTypeEnum(String code, String value) {
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
