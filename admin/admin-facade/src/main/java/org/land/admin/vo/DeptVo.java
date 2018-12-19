package org.land.admin.vo;

import org.land.admin.entity.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * <p>
 * 系统部门信息扩展对象
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DeptVo extends Dept {
    private String label;
    private List<DeptVo> children;
    private Integer level;
}
