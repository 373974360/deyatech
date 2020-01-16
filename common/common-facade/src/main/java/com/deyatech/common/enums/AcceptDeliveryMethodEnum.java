package com.deyatech.common.enums;

/**
 * @Description 寄送方式
 * @Author doukang
 * @Date 2020-01-09 16:38
 */
public enum AcceptDeliveryMethodEnum implements IEnums<Integer> {

    Window(1, "大厅窗口"),
    Mailbox(2, "信包箱"),
    Post(3, "邮寄"),
    MailboxPost(5, "信包箱邮寄");

    private Integer code;

    private String value;

    AcceptDeliveryMethodEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
