package org.land.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * element-ui 级联结果封装对象
 *
 * @author : lee.
 * @since : 2018-12-14 13:27
 */
@Slf4j
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class CascaderResult implements Serializable {

    private String value;
    private String label;
    private boolean disabled = false;
    private List<CascaderResult> children;
    private String treePosition;

}
