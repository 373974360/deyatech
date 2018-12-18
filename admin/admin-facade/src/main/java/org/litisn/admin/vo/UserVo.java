package org.litisn.admin.vo;

import org.litisn.admin.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统用户信息扩展对象
 * </p>
 *
 * @author litisn
 * @since 2018-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserVo extends User {

}
