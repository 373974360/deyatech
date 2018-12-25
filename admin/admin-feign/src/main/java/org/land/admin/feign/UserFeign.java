package org.land.admin.feign;

import org.land.admin.entity.User;
import org.land.admin.vo.UserVo;
import org.land.common.entity.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * <p>
 * 后台管理模块用户feign接口类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 11:35
 */
@RequestMapping("/feign/admin/user")
@FeignClient(value = "admin-service")
public interface UserFeign {

    /**
     * 根据ID获取系统用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/getByUser", method = RequestMethod.POST)
    RestResult<UserVo> getByUser(@RequestBody User user);
}
