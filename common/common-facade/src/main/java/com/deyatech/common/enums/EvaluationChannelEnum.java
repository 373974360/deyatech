package com.deyatech.common.enums;

/**
 * 评价渠道
 * @author: csm
 */
public enum EvaluationChannelEnum implements IEnums<String> {

    PC("1","PC端"),
    APP("2","移动端"),
    TWO_DIMENSIONAL_CODE("3","二维码"),
    ZWDT_TABLET_PC("4","政务大厅平板电脑"),
    ZWDT_OTHER_TERMINAL("5","政务大厅其他终端");

    private String code;
    private String value;
    EvaluationChannelEnum(String code, String value) {
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
