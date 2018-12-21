package org.land.common.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * Jwt信息对象
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 13:32
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class JwtInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 帐号
     */
    private String uniqueName;

    /**
     * 密码
     */
    private String name;

    /**
     * 缓存时间
     */
    private Long buffer;
}
