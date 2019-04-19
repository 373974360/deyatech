package com.deyatech.admin.feign.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.admin.entity.Menu;
import com.deyatech.admin.entity.User;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.admin.service.DictionaryIndexService;
import com.deyatech.admin.service.MenuService;
import com.deyatech.admin.service.UserService;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.entity.EnumsResult;
import com.deyatech.common.entity.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * MenuFeign实现类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-25 16:36
 */
@RestController
public class AdminFeignImpl implements AdminFeign {

    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

    @Autowired
    DictionaryIndexService dictionaryIndexService;

    @Override
    public RestResult<String[]> getAllPermissionsByUserId(@RequestParam("userId") String userId) {
        return RestResult.ok(menuService.getAllPermissionsByUserId(userId));
    }

    @Override
    public RestResult<String[]> getAllRequestsByUserId(@RequestParam("userId") String userId) {
        return RestResult.ok(menuService.getAllRequestsByUserId(userId));
    }

    @Override
    public RestResult<String[]> getAllRequests() {
        Collection<Menu> menus = menuService.listByBean(null);
        String[] requests = null;
        if (CollectionUtil.isNotEmpty(menus)) {
            requests = menus.stream().map(Menu::getRequest).collect(Collectors.toList()).toArray(new String[menus.size()]);
        }
        return RestResult.ok(requests);
    }

    @Override
    public RestResult<UserVo> getByUser(@RequestBody User user) {
        User byBean = userService.getByBean(user);
        return RestResult.ok(userService.setVoProperties(byBean));
    }

    @Override
    public RestResult<List<EnumsResult>> getDictsAll() {
        List<EnumsResult> resultList = dictionaryIndexService.getDictsAll();
        return RestResult.ok(resultList);
    }

    /**
     * 获取系统所有的后台用户
     * @return
     */
    @Override
    public RestResult<Collection<UserVo>> selectAllUsers() {
        Collection<UserVo> users = userService.selectAllUserInfo();
        return RestResult.ok(users);
    }

}
