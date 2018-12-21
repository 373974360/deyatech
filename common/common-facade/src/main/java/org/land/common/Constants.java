package org.land.common;

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
    String DEFAULT_PARENT_ROOT = "0";

    /**
     * 默认第一级Level
     */
    Integer DEFAULT_ROOT_LEVEL = 1;

    /**
     * 默认treePosition分隔符
     */
    String DEFAULT_TREE_POSITION_SPLIT = "&";

}
