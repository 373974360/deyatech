package com.deyatech.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 日志实体对象
 * </p>
 *
 * @author: lee.
 * @since: 2019/5/22 17:45
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class Logs {
    private String time;
    private String thread;
    private String classes;
    private String level;
    private String logger;
    private String message;
}
