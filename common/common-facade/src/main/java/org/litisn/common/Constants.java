package org.litisn.common;

/**
 * <p>
 * 常量定义类
 * </p>
 *
 * @author: litisn
 * @since: 2018-12-14 16:17
 */
public interface Constants {
    /**
     * 系统当前登录用户id存储Key值
     */
    String CONTEXT_KEY_USER_ID = "currentUserId";

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
}
