package org.land.admin.vo;

import org.land.admin.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.List;

/**
 * <p>
 * 系统菜单信息扩展对象
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MenuVo extends Menu {
    private String label;
    private List<MenuVo> children;
    private Integer level;
}
