package com.deyatech.admin.feign;

import com.deyatech.admin.entity.User;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.entity.EnumsResult;
import com.deyatech.common.entity.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

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
     * 根据用户ID查找该用户拥有的所有后台请求权限
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/menu/getAllRequestsByUserId", method = RequestMethod.GET)
    RestResult<String[]> getAllRequestsByUserId(@RequestParam("userId") String userId);

    /**
     * 获取系统所有需要验证的请求地址
     *
     * @return
     */
    @RequestMapping(value = "/menu/getAllRequests", method = RequestMethod.GET)
    RestResult<String[]> getAllRequests();

    /**
     * 根据ID获取系统用户信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/getByUser", method = RequestMethod.POST)
    RestResult<UserVo> getByUser(@RequestBody User user);


    /**
     * 返回所有字典索引和字典子项目集合
     *
     * @return
     */
    @RequestMapping(value = "/dicts/getDictsAll", method = RequestMethod.GET)
    RestResult<List<EnumsResult>> getDictsAll();

    /**
     * 获取系统所有的后台用户
     * @return
     */
    @GetMapping("/user/selectAllUsers")
    RestResult<Collection<UserVo>> selectAllUsers();
}
