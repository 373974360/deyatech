package com.deyatech.common.enums;

/**
 * 交件状态
 * Created by yangyan on 16/7/13.
 */
public enum HandStatusEnum implements IEnums<Integer> {

    /* 大厅交件 */
    /**
     * 大厅交件：待交件
     */
    WindowToHand(11, "待交件"),
    /**
     * 大厅交件：已交件
     */
    WindowHanded(12, "已交件"),

    /* 信包箱交件 */
    /**
     * 信包箱交件：未预约
     */
    MailboxToSend(21, "待投递"),
    /**
     * 信包箱交件：待交件
     */
    MailboxToHand(22, "已投递"),
    /**
     * 信包箱交件：已交件
     */
    MailboxHanded(23, "已交件"),

    /* 邮寄交件 */
    /**
     * 邮寄交件：待投递
     */
    PostToSend(31, "待投递"),
    /**
     * 邮寄交件：邮寄中
     */
    PostSent(32, "邮寄中"),
    /**
     * 邮寄交件：已交件
     */
    PostHanded(33, "已交件"),

    /* 信包箱邮寄 */
    /**
     * 信包箱邮寄：待投递
     */
    MailboxPostToSend(51, "待投递"),
    /**
     * 信包箱邮寄：已投递未邮寄
     */
    MailboxPostToPost(52, "已投递未邮寄"),
    /**
     * 信包箱邮寄：邮寄中
     */
    MailboxPostMailing(53, "邮寄中"),
    /**
     * 信包箱邮寄：已交件
     */
    MailboxPostHanded(54, "已交件");

    int value = 1;

    String name;


    public Integer getCode() {
        return this.value;
    }

    public String getValue(){
        return this.name;
    }

    HandStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
