package com.deyatech.common;

/**
 * <p>
 * 常量定义类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:17
 */
public interface Constants {

    /**
     * 异常信息统一头信息<br>
     * 非常遗憾的通知您,程序发生了异常
     */
    String EXCEPTION_HEAD = "发生了异常 :";

    /**
     * 系统当前登录用户id存储Key值
     */
    String CONTEXT_KEY_USER_ID = "currentUserId";

    /**
     * redis中token过期字符串
     */
    String TOKEN = "token";

    /**
     * 刷新token标记
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * jwt加密信息中的用户Id
     */
    String JWT_KEY_ID = "id";

    /**
     * jwt加密信息中的唯一用户名
     */
    String JWT_KEY_UNIQUENAME = "uniqueName";

    /**
     * jwt加密信息中的名称
     */
    String JWT_KEY_NAME = "name";

    /**
     * 12位加密盐
     */
    Integer PASSWORD_ENCORDER_SALT = 12;

    /**
     * data
     */
    String DATA = "data";

    /**
     * get上传默认前缀
     */
    String UPLOAD_DEFAULT_PREFIX_URL = "/upload/";

    /**
     * get方法前缀
     */
    String METHOD_GET_PREX = "get";

    /**
     * 序列号id字段名
     */
    String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * 数据库排序关键字
     */
    String SORT_SQL_KEYWORD = "order by ";

    /**
     * 默认排序
     */
    String DEFAULT_SORT_STR = SORT_SQL_KEYWORD.concat("id_ desc");

    /**
     * 默认翻页当前页
     */
    Long DEFAULT_CURRENT_PAGE = 1L;

    /**
     * 默认翻页每页条数
     */
    Long DEFAULT_PAGE_SIZE = 15L;

    /**
     * 默认父类跟ID
     */
    String ZERO = "0";

    /**
     * 默认第一级Level
     */
    Integer DEFAULT_ROOT_LEVEL = 1;

    /**
     * 默认常用字符串分隔符
     */
    String DEFAULT_STRING_SPLIT = ";";

    /**
     * 默认treePosition分隔符
     */
    String DEFAULT_TREE_POSITION_SPLIT = "&";

    /**
     * 验证码前缀
     */
    String PREFIX_KEY_VERIFY_CODE = "verifyCode_";

    /**
     * 路由标识
     */
    String GATEWAY_HEADER = "deyatech-gateway";

    /**
     * 路由header数据
     */
    String GATEWAY_VALUE = "西安德雅通科技有限公司";

    /**
     * 缓存命名空间
     */
    String CACHE_NAMESPACE = "deyatech:";

    /**
     * 缓存命名空间
     */
    String REDIS_SET_KEY = "~keys";

    /**
     * WebSocket header标记
     */
    String WEBSOCKET_HEADER_SIGN = "Upgrade";

    /**
     * 评价器更新用户操作action
     */
    String COMMENT_ACTION = "action";

    /**
     * 评价器更新用户操作action
     */
    String COMMENT_UPDATE_USER_INFO = "updateUserInfo";

    /**
     * 评价器更新叫号信息操作action
     */
    String COMMENT_UPDATE_CALL_INFO = "updateCallInfo";

    /**
     * 评价器欢迎用户办理业务操作action
     */
    String COMMENT_PLEASE_WAIT = "pleaseWait";

    /**
     * 评价器欢迎用户办理业务操作action
     */
    String COMMENT_SAY_WELCOME = "sayWelcome";

    /**
     * 评价器请求用户评价操作action
     */
    String COMMENT_PLEASE_USER_SUBMIT = "pleaseComment";

    /**
     * 是否发送消息
     */
    String MESSAGE_TO = "1";

    /**
     * 是否呼叫器响铃
     */
    String IS_RING = "1";
    /**
     *  微警认证返回状态
     */
    Integer AUTH_RES_CODE = 0;
    /**
     * 是否推送微信消息
     */
    Integer MSG_STATUS = 0;
    /**
     * 身份认证结果
     */
    Integer CERT_RES = -1;

    String QUEUE_MAILBOX_UPDATE = "mailbox.update";

    String QUEUE_MAILBOX_RESERVE_HAND = "mailbox.reserve.hand";

    String QUEUE_MAILBOX_RESERVE_TAKE = "mailbox.reserve.take";

    String QUEUE_EMS_WAYBILL_GOT_HAND_REQUEST = "ems.waybillGot.request";

    String QUEUE_EXPRESS_UPDATE = "express.update";

    String QUEUE_LOGISTICS_UPDATE = "logistics.update";

    /**
     * 模板存放文件夹名
     * */
    String TEMPLATE_DIR_NAME = "/template";

    /**
     * 工作流相关常量
     */
    String VARIABLE_USER_ID = "userId";

    String VARIABLE_SOURCE = "source";

    String VARIABLE_DEPARTMENT = "department";

    String WORKFLOW_RANG_KEY_SPLIT = ":";

    String VARIABLE_STASH = "stash";
    String VARIABLE_STASH_TYPE_AUTO_PASS = "auto_pass";
}
