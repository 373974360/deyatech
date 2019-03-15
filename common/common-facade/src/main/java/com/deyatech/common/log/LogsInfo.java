package com.deyatech.common.log;

import lombok.Data;

import java.util.Date;

@Data
public class LogsInfo {

    private String notes;
    private String method;
    private String requestUrl;
    private String userId;
    private String params;
    private Long time;
    private String ip;
    private String createBy;

}
