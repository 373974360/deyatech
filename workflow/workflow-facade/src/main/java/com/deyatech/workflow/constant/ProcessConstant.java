package com.deyatech.workflow.constant;

/**
 * 流程常量
 *
 * @author ycx
 * @since 2020-01-06
 */
public class ProcessConstant {
    /**
     * 工作流相关常量
     */
    public static final String BUSINESS_ID = "businessId";
    public static final String REASON = "reason";
    public static final String VARIABLE_USER_ID = "userId";
    public static final String VARIABLE_DEPARTMENT = "department";
    public static final String VARIABLE_SOURCE = "source";
    public static final String WORKFLOW_RANG_KEY_SPLIT = ":";

    public static final String EXCHANGE_PROCESS = "exchange.process";
    public static final String QUEUE_PROCESS = "queue.process";
    public static final String ROUTING_KEY_PROCESS = "routing.key.process.*";
    // 完成
    public static final String QUEUE_PROCESS_FINISH = "queue.process.finish";
    public static final String ROUTING_KEY_PROCESS_FINISH = "routing.key.process.finish";
    // 拒绝
    public static final String QUEUE_PROCESS_REJECT = "queue.process.reject";
    public static final String ROUTING_KEY_PROCESS_REJECT = "routing.key.process.reject";
    // 取消
    public static final String QUEUE_PROCESS_CANCEL = "queue.process.cancel";
    public static final String ROUTING_KEY_PROCESS_CANCEL = "routing.key.process.cancel";
}
