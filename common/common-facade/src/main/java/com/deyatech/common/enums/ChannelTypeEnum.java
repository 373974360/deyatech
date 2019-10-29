package com.deyatech.common.enums;

/**
 * @Description: 预约渠道
 * @Author: songshanshan
 * @Date: 2019/5/10 16:46
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public enum ChannelTypeEnum  implements IEnums<Integer> {
    /**
     * 表示已取消
     */
    WECHAT(2, "微信"),
    /**
     * 表示未赴约
     */
    ONEMORE(1, "最多跑一次平台"),
    /**
     * 表示未赴约
     */
    LOBBY(3, "线下大厅"),
    /**
     * 表示未赴约
     */
    TERMINAL(4, "自助终端");



    private Integer code;
    private String value;

    ChannelTypeEnum(final Integer code, final String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
