package com.deyatech.common.enums;

/**
 * 数据的状态
 * Created by yangyan on 16/7/13.
 */
public enum HandTypeEnum implements IEnums<Integer> {
    /**
     * 大厅交件
     */
    Window(1, "大厅交件"),
    /**
     * 信包箱交件
     */
    Mailbox(2, "信包箱交件"),
    /**
     * 邮寄交件
     */
    Post(3, "邮寄交件"),
    /**
     * 网上交件
     */
//    Net(4, "网上提交"),
    /**
     * 信包箱邮寄
     */
    MailboxPost(5, "信包箱邮寄");

    int handType = 1;

    String handTypeName;


    HandTypeEnum(int handType, String handTypeName) {
        this.handType = handType;
        this.handTypeName = handTypeName;
    }

    public Integer getCode() {
        return this.handType;
    }

    public String getValue() {
        return this.handTypeName;
    }
}
