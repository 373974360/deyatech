package org.land.admin.controller;

import org.land.admin.entity.Menu;
import org.land.admin.vo.MenuVo;
import org.land.admin.service.MenuService;
import org.land.common.entity.RestResult;
import org.land.common.entity.CascaderResult;
import org.land.common.utils.CascaderUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.web.bind.annotation.RestController;
import org.land.common.base.BaseController;

/**
 * <p>
 * 系统菜单信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2018-12-21
 */
@Slf4j
@RestController
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {
    @Autowired
    MenuService menuService;

    /**
     * 单个保存或者更新系统菜单信息
     *
     * @param menu
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public RestResult saveOrUpdate(Menu menu) {
        log.info(String.format("保存或者更新系统菜单信息: %s ", JSONUtil.toJsonStr(menu)));
        boolean result = menuService.saveOrUpdate(menu);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统菜单信息
     *
     * @param menuList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    public RestResult saveOrUpdateBatch(Collection<Menu> menuList) {
        log.info(String.format("批量保存或者更新系统菜单信息: %s ", JSONUtil.toJsonStr(menuList)));
        boolean result = menuService.saveOrUpdateBatch(menuList);
        return RestResult.ok(result);
    }

    /**
     * 根据Menu对象属性逻辑删除系统菜单信息
     *
     * @param menu
     * @return
     */
    @PostMapping("/removeByMenu")
    public RestResult removeByMenu(Menu menu) {
        log.info(String.format("根据Menu对象属性逻辑删除系统菜单信息: %s ", menu));
        boolean result = menuService.removeByBean(menu);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统菜单信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    public RestResult removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统菜单信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = menuService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Menu对象属性获取系统菜单信息
     *
     * @param menu
     * @return
     */
    @GetMapping("/getByMenu")
    public RestResult getByMenu(Menu menu) {
        menu = menuService.getByBean(menu);
        MenuVo menuVo = menuService.setVoProperties(menu);
        log.info(String.format("根据id获取系统菜单信息：s%", JSONUtil.toJsonStr(menuVo)));
        return RestResult.ok(menuVo);
    }

    /**
     * 根据Menu对象属性检索所有系统菜单信息
     *
     * @param menu
     * @return
     */
    @GetMapping("/listByBean")
    public RestResult listByBean(Menu menu) {
        Collection<Menu> menus = menuService.listByBean(menu);
        Collection<MenuVo> menuVos = menuService.setVoProperties(menus);
        log.info(String.format("根据Menu对象属性检索所有系统菜单信息: %s ",JSONUtil.toJsonStr(menuVos)));
        return RestResult.ok(menuVos);
    }

    /**
     * 根据Menu对象属性分页检索系统菜单信息
     *
     * @param menu
     * @return
     */
    @GetMapping("/pageByBean")
    public RestResult pageByBean(Menu menu) {
        IPage menus = menuService.pageByBean(menu);
        menus.setRecords(menuService.setVoProperties(menus.getRecords()));
        log.info(String.format("根据Menu对象属性分页检索系统菜单信息: %s ",JSONUtil.toJsonStr(menus)));
        return RestResult.ok(menus);
    }

    /**
     * 获取系统菜单信息的tree对象
     *
     * @return
     */
    @GetMapping("/getTree")
    public RestResult getMenuTree() {
        Collection<MenuVo> menuTree = menuService.getMenuTree();
        log.info(String.format("获取系统菜单信息的tree对象: %s ",JSONUtil.toJsonStr(menuTree)));
        return RestResult.ok(menuTree);
    }

    /**
     * 获取系统菜单信息的级联对象
     *
     * @param id
     * @return
     */
    @GetMapping("/getCascader")
    public RestResult getCascader(String id) {
        Collection<MenuVo> menuVos = menuService.getMenuTree();
        List<CascaderResult> cascaderResults = CascaderUtil.getResult("Id", "Name","TreePosition", id, menuVos);
        log.info(String.format("获取系统菜单信息的级联对象: %s ",JSONUtil.toJsonStr(cascaderResults)));
        return RestResult.ok(cascaderResults);
    }
}
