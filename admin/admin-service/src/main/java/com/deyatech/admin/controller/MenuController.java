package com.deyatech.admin.controller;

import com.deyatech.admin.entity.Menu;
import com.deyatech.admin.vo.MenuVo;
import com.deyatech.admin.service.MenuService;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.entity.CascaderResult;
import com.deyatech.common.utils.CascaderUtil;
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
import com.deyatech.common.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统菜单信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-03-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/menu")
@Api(tags = {"系统菜单信息接口"})
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
    @ApiOperation(value="单个保存或者更新系统菜单信息", notes="根据系统菜单信息对象保存或者更新系统菜单信息信息")
    @ApiImplicitParam(name = "menu", value = "系统菜单信息对象", required = true, dataType = "Menu", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(Menu menu) {
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
    @ApiOperation(value="批量保存或者更新系统菜单信息", notes="根据系统菜单信息对象集合批量保存或者更新系统菜单信息信息")
    @ApiImplicitParam(name = "menuList", value = "系统菜单信息对象集合", required = true, allowMultiple = true, dataType = "Menu", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<Menu> menuList) {
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
    @ApiOperation(value="根据Menu对象属性逻辑删除系统菜单信息", notes="根据系统菜单信息对象逻辑删除系统菜单信息信息")
    @ApiImplicitParam(name = "menu", value = "系统菜单信息对象", required = true, dataType = "Menu", paramType = "query")
    public RestResult<Boolean> removeByMenu(Menu menu) {
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
    @ApiOperation(value="根据ID批量逻辑删除系统菜单信息", notes="根据系统菜单信息对象ID批量逻辑删除系统菜单信息信息")
    @ApiImplicitParam(name = "ids", value = "系统菜单信息对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(Collection<Serializable> ids) {
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
    @ApiOperation(value="根据Menu对象属性获取系统菜单信息", notes="根据系统菜单信息对象属性获取系统菜单信息信息")
    @ApiImplicitParam(name = "menu", value = "系统菜单信息对象", required = false, dataType = "Menu", paramType = "query")
    public RestResult<MenuVo> getByMenu(Menu menu) {
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
    @GetMapping("/listByMenu")
    @ApiOperation(value="根据Menu对象属性检索所有系统菜单信息", notes="根据Menu对象属性检索所有系统菜单信息信息")
    @ApiImplicitParam(name = "menu", value = "系统菜单信息对象", required = false, dataType = "Menu", paramType = "query")
    public RestResult<Collection<MenuVo>> listByMenu(Menu menu) {
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
    @GetMapping("/pageByMenu")
    @ApiOperation(value="根据Menu对象属性分页检索系统菜单信息", notes="根据Menu对象属性分页检索系统菜单信息信息")
    @ApiImplicitParam(name = "menu", value = "系统菜单信息对象", required = false, dataType = "Menu", paramType = "query")
    public RestResult<IPage<MenuVo>> pageByMenu(Menu menu) {
        IPage<MenuVo> menus = menuService.pageByBean(menu);
        menus.setRecords(menuService.setVoProperties(menus.getRecords()));
        log.info(String.format("根据Menu对象属性分页检索系统菜单信息: %s ",JSONUtil.toJsonStr(menus)));
        return RestResult.ok(menus);
    }

    /**
     * 获取系统菜单信息的tree对象
     *
     * @param menu
     * @return
     */
    @GetMapping("/getTree")
    @ApiOperation(value="获取系统菜单信息的tree对象", notes="获取系统菜单信息的tree对象")
    public RestResult<Collection<MenuVo>> getMenuTree(Menu menu) {
        Collection<MenuVo> menuTree = menuService.getMenuTree(menu);
        log.info(String.format("获取系统菜单信息的tree对象: %s ",JSONUtil.toJsonStr(menuTree)));
        return RestResult.ok(menuTree);
    }

    /**
     * 获取系统菜单信息的级联对象
     *
     * @param menu
     * @return
     */
    @GetMapping("/getCascader")
    @ApiOperation(value="获取系统菜单信息的级联对象", notes="获取系统菜单信息的级联对象")
    @ApiImplicitParam(name = "menu", value = "menu", required = false, dataType = "Menu", paramType = "query")
    public RestResult<List<CascaderResult>> getCascader(Menu menu) {
        Collection<MenuVo> menuVos = menuService.getMenuTree(menu);
        List<CascaderResult> cascaderResults = CascaderUtil.getResult("Id", "Name","TreePosition", menu.getId(), menuVos);
        log.info(String.format("获取系统菜单信息的级联对象: %s ",JSONUtil.toJsonStr(cascaderResults)));
        return RestResult.ok(cascaderResults);
    }
}
