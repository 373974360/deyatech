package com.deyatech.admin.feign;

import com.deyatech.admin.entity.User;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.entity.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * admin模块feign远程调用类
 * </p>
 *
 * @author: lee.
 * @since: 2019/3/6 15:40
 */
@RequestMapping("/feign/admin")
@FeignClient(value = "admin-service")
public interface AdminFeign {

    /**
     * 根据用户ID查找该用户拥有的所有权限
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/menu/getAllPermissionsByUserId", method = RequestMethod.GET)
    RestResult<String[]> getAllPermissionsByUserId(@RequestParam("userId") String userId);

    /**
     * 根据ID获取系统用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/getByUser", method = RequestMethod.POST)
    RestResult<UserVo> getByUser(@RequestBody User user);
}
