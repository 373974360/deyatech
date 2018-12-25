package org.land.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 枚举类型对象封装返回结果类
 *
 * @author : lee.
 * @since : 2018-12-14 13:27
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class EnumsResult implements Serializable {

    private String name;
    private List<Map<String,Object>> value;
}
