package org.land.admin.feign;

import org.land.admin.entity.User;
import org.land.common.entity.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 后台管理模块用户feign接口类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 11:35
 */
@RequestMapping("/admin/user")
@FeignClient(value = "userFeign")
public interface UserFeign {

    /**
     * 根据ID获取系统用户信息
     *
     * @param user
     * @return
     */
    @GetMapping("/getByUser")
    RestResult getByUser(@RequestBody User user);
}
