package com.deyatech.common.enums;

/**
 * @Description 寄送状态
 * @Author doukang
 * @Date 2020-01-09 15:56
 */
public enum AcceptDeliveryStatusEnum implements IEnums<Integer> {

    WindowToSend(11, "待投递"),
    WindowTook(12, "已取件"),

    MailboxToSend(21, "待投递"),
    MailboxSent(22, "已投递"),
    MailboxTook(23, "已取件"),

    PostToSend(31, "待投递"),
    PostMailing(32, "邮寄中"),
    PostTook(33, "已取件"),

    MailboxPostToSend(51, "待投递"),
    MailboxPostSent(52, "已投递"),
    MailboxPostMailing(53, "邮寄中"),
    MailboxPostTook(54, "已取件");

    private Integer code;

    private String value;

    AcceptDeliveryStatusEnum(Integer code, String value) {
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
