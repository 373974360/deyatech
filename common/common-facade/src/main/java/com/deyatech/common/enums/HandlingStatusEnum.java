package com.deyatech.common.enums;

/**
 * 办件状态
 *
 * @author ycx
 */
public enum HandlingStatusEnum implements IEnums<Integer> {
    PROCESSING(10, "在办"), //综合窗口通过后,转为在办
    FINISH(15, "办结"),   //成功完成
    CLOSE(99, "不予受理");  //部门不予受理,终止

    private Integer code;
    private String value;
    HandlingStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
    @Override
    public Integer getCode() {
        return this.code;
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
