package org.land.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * element-ui 级联结果封装对象
 *
 * @author : lee.
 * @since : 2018-12-14 13:27
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CascaderResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 对象对应的值
     */
    private String value;

    /**
     * 前台显示的中文名称
     */
    private String label;

    /**
     * 描述树结构的信息
     */
    private String treePosition;

    /**
     * 是否可用标识
     */
    private boolean disabled = false;

    /**
     * 子对象信息
     */
    private List<CascaderResult> children;

}
