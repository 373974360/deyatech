package com.deyatech.common.enums;

/**
 * 取件状态
 * Created by yangyan on 16/7/13.
 */
public enum TakeStatusEnum implements IEnums<Integer> {

    /* 窗口取件 */
    /**
     * 窗口自取：待取件
     */
    WindowToTake(11, "待取件"),
    /**
     * 窗口自取：已取件
     */
    WindowTook(12, "已取件"),

    /* 信包箱自提 */
    /**
     * 信包箱自提：未预约
     */
    MailboxToSend(21, "待投递"),
    /**
     * 信包箱自提：待取件
     */
    MailboxToTake(22, "待取件"),
    /**
     * 信包箱自提：已取件
     */
    MailboxTook(23, "已取件"),

    /* 快递邮寄 */
    /**
     * 快递邮寄：待投递
     */
    PostToSend(31, "待投递"),
    /**
     * 快递邮寄：邮寄中
     */
    PostSent(32, "邮寄中"),
    /**
     * 快递邮寄：已取件
     */
    PostTook(33, "已取件");

    int value = 1;

    String name;


    public Integer getCode() {
        return this.value;
    }

    public String getValue(){
        return this.name;
    }

    TakeStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
