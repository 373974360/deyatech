package com.deyatech.admin.controller;

import cn.hutool.json.JSONUtil;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.model.RedisResult;
import com.deyatech.common.utils.CacheUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述：缓存管理前端控制器
 *
 * @Author: MaChaoWei
 * @Date: 2019/3/27 10:34
 */
@Slf4j
@RestController
@RequestMapping("/admin/cache")
@Api(tags = {"缓存管理接口"})
public class CacheController {


    /**
     * 删除redis缓存信息
     *
     * @param keys
     * @return CommonResult.ok()
     */
    @PostMapping("/del")
    @ApiOperation(value = "删除redis缓存信息", notes = "删除redis缓存信息")
    @ApiImplicitParam(value = "key", name = "key", dataType = "java.lang.String", paramType = "query")
    public RestResult<Boolean> delCache(@RequestParam(value = "keys[]", required = false) List<String> keys) {
        CacheUtil.getCache().dels(keys);
        return RestResult.ok(true);
    }

    /**
     * 清空redis缓存信息
     *
     * @return CommonResult.ok()
     */
    @PostMapping("/flush")
    @ApiOperation(value = "清空redis缓存信息", notes = "清空redis缓存信息")
    public RestResult<Boolean> flushCache() {
        CacheUtil.getCache().delAll("*");
        return RestResult.ok(true);
    }

    /**
     * 根据翻页参数、搜索参数查找redis缓存信息
     *
     * @param key
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据翻页参数、搜索参数查找redis缓存信息", notes = "根据翻页参数、搜索参数查找redis缓存信息")
    @ApiImplicitParam(value = "key", name = "key", dataType = "java.lang.String", paramType = "query")
    public RestResult<RedisResult> pageByCacheIndex(String key) {
        List<RedisResult> allResult = CacheUtil.getCache().getAllResult(key);
        log.info(String.format("根据翻页参数、搜索参数查找redis缓存信息: %s ", JSONUtil.toJsonStr(allResult)));
        return RestResult.ok(allResult);
    }
}
