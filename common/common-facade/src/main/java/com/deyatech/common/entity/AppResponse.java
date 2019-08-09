package com.deyatech.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 第三方返回结果类
 * @Author: songshanshan
 * @Date: 2019/8/7 14:17
 * @Version: 1.0
 * @Created in idea by autoCode
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse {

    private String RESP_CODE;
    private String RESP_DESC;
    private Object DATA;
    private String projId;

    public static AppResponse ok(String RESP_DESC, String projId) {
        return new AppResponse("0000", RESP_DESC, null, projId);
    }

    public static AppResponse ok(Object object) {
        return new AppResponse("0000", "调用成功", object, null);
    }

    public static AppResponse ok(String RESP_DESC) {
        return new AppResponse("0000", RESP_DESC, null, null);
    }

    public static AppResponse ok(String RESP_DESC, Object object, String projId) {
        return new AppResponse("0000", RESP_DESC, object, projId);
    }

    public static AppResponse error(String RESP_DESC, String projId) {
        return new AppResponse("8888", RESP_DESC, null, projId);
    }

    public static AppResponse error(String RESP_DESC) {
        return new AppResponse("8888", RESP_DESC, null, null);
    }

    public static AppResponse build(String RESP_CODE, String RESP_DESC, String projId) {
        return new AppResponse(RESP_CODE, RESP_DESC, null, projId);
    }

    public static AppResponse transCommonResult(RestResult commonResult) {
        AppResponse appResponse = new AppResponse();
        appResponse.setRESP_CODE(commonResult.isOk() ? "0000" : "8888");
        appResponse.setRESP_DESC(commonResult.getMessage());
        appResponse.setDATA(commonResult.getData());
        return appResponse;
    }

}
