package com.deyatech.common.enums;

/**
 * 数据的状态
 * Created by yangyan on 16/7/13.
 */
public enum TakeTypeEnum implements IEnums<Integer> {
    /**
     * 表示窗口自取
     */
    Window(1,"综合窗口取件"),
    /**
     * 信包箱自提
     */
    Mailbox(2, "信包箱自提"),
    /**
     * 表示邮寄
     */
    Post(3,"快递邮寄");
    /**
     * 电子证照
     */
//    CERT_FILE(4,"电子证照");

    int takeType = 1;

    String takeTypeName;


    public Integer getCode() {
        return this.takeType;
    }

    public String getValue(){
        return this.takeTypeName;
    }

    TakeTypeEnum(int takeType, String takeTypeName) {
        this.takeType = takeType;
        this.takeTypeName = takeTypeName;
    }
}
