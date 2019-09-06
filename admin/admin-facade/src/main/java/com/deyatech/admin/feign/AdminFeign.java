package com.deyatech.admin.feign;

import com.deyatech.admin.entity.Holiday;
import com.deyatech.admin.entity.User;
import com.deyatech.admin.vo.DictionaryVo;
import com.deyatech.admin.vo.HolidayVo;
import com.deyatech.admin.vo.MetadataCollectionVo;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.entity.EnumsResult;
import com.deyatech.common.entity.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据用户工号取得用户信息
     * @param empNo
     * @return
     */
    @RequestMapping("/user/getUserByEmpNo")
    RestResult<UserVo> getUserByEmpNo(@RequestParam("empNo") String empNo);

    /**
     * 年份查询节假日
     * @param holiday
     * @return
     */
    @RequestMapping(value = "/holiday/listByHoliday", method = RequestMethod.POST)
    RestResult<Collection<HolidayVo>> listByHoliday(@RequestBody Holiday holiday);

    /**
     * 返回指定日期 n 个工作日（跳过设置的节假日）之后的日期
     *
     * @param startTime
     * @param workDay
     * @return
     */
    @RequestMapping("/holiday/afterDay")
    RestResult<Date> workDayAfter(@RequestParam("startTime") Date startTime, @RequestParam("workDay") Integer workDay);

    /**
     * 计算某个日期几个小时之后的时间
     *
     * @param startTime
     * @param limitHour
     * @return
     */
    @RequestMapping("/holiday/afterHour")
    RestResult<Date> workHourAfter(@RequestParam("startTime") Date startTime, @RequestParam("limitHour") Integer limitHour);

    /**
     * 获取字典项信息
     *
     * @param key
     * @param code
     * @return
     */
    @RequestMapping(value = "/dictionary/getDictionary", method = RequestMethod.GET)
    RestResult<DictionaryVo> getDictionary(@RequestParam("key") String key, @RequestParam("code") String code);

    /**
     * 根据快递公司代码获取字典项信息
     *
     * @param remark
     * @return
     */
    @RequestMapping(value = "/dictionary/getKdgsDic", method = RequestMethod.GET)
    RestResult<DictionaryVo> getKdgsDic(@RequestParam("remark") String remark);

    /**
     * 根据用户编号获取用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/getUserByUserId")
    RestResult<UserVo> getUserByUserId(@RequestParam("userId") String userId);

    /**
     * 判断某天是否是节假日
     *
     * @param date
     * @return
     */
    @RequestMapping(value = "/holiday/isHoliday", method = RequestMethod.POST)
    RestResult<Boolean> isHoliday(@RequestBody Date date);

    /**
     * 根据角色id获取用户id
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/roleUser/getUserIdsByRoleId")
    RestResult<List<String>> getUserIdsByRoleId(String roleId);

    /**
     * 根据用户id查询角色id
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/roleUser/getRoleIdsByUserId")
    RestResult<List<String>> getRoleIdsByUserId(String userId);

    /**
     * 获取所有元数据集（包括关联的元数据）信息
     *
     * @param metadataCollectionVo
     * @return
     */
    @RequestMapping(value = "/metadataCollection/findAllData")
    RestResult<List<MetadataCollectionVo>> findAllData(@RequestBody MetadataCollectionVo metadataCollectionVo);

    /**
     * 插入一条元数据信息
     *
     * @param metaDataCollectionId 元数据集id
     * @param contentId 元数据记录id
     * @param contentMap 元数据信息
     * @return
     */
    @RequestMapping(value = "/metadataCollection/saveOrUpdateMetadata")
    RestResult<String> saveOrUpdateMetadata(@RequestParam("metaDataCollectionId") String metaDataCollectionId,
                                            @RequestParam(value = "contentId", required = false) String contentId, @RequestBody Map contentMap);

    /**
     * 根据id查询元数据记录
     * @param metaDataCollectionId 元数据集id
     * @param contentId 元数据记录id
     * @return
     */
    @RequestMapping(value = "/metadataCollection/getMetadataById")
    RestResult<Map> getMetadataById(@RequestParam("metaDataCollectionId") String metaDataCollectionId,
                                    @RequestParam("contentId") String contentId);

    /**
     * 根据ids删除元数据记录
     * @param mapList id集合
     * @return
     */
    @RequestMapping(value = "/metadataCollection/removeMetadataById")
    RestResult removeMetadataByIds(@RequestBody List<Map> mapList);

}
