package org.land.admin.vo;

import org.land.admin.entity.Dict;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统数据字典明细信息扩展对象
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DictVo extends Dict {
}
