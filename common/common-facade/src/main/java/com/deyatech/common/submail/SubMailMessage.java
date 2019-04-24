package com.deyatech.common.submail;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * submail短信封装类
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class SubMailMessage implements Serializable {
    private String to;
    private Map<String, String> vars;
}
