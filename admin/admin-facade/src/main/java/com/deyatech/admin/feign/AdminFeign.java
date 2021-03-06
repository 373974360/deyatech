package com.deyatech.admin.feign;

import com.deyatech.admin.entity.*;
import com.deyatech.admin.vo.*;
import com.deyatech.common.entity.CascaderResult;
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
     * 获取两个日期之间的工作日天数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("/holiday/workIntervalDayAfter")
    RestResult workIntervalDayAfter(@RequestParam("startTime") Date startTime,@RequestParam("endTime") Date endTime);

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
    @RequestMapping(value = "/roleUser/getUserIdsByRoleId", method = RequestMethod.GET)
    RestResult<List<String>> getUserIdsByRoleId(@RequestParam("roleId") String roleId);

    /**
     * 根据用户id查询角色id
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/roleUser/getRoleIdsByUserId", method = RequestMethod.GET)
    RestResult<List<String>> getRoleIdsByUserId(@RequestParam("userId") String userId);

    /**
     * 根据用户id查询角色id
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/roleUser/getRolesByUserId", method = RequestMethod.GET)
    RestResult<List<Role>> getRolesByUserId(@RequestParam("userId") String userId);

    /**
     * 根据部门id查询用户id
     *
     * @param departmentId
     * @return
     */
    @RequestMapping(value = "findUserIdsByDepartmentId", method = RequestMethod.GET)
    RestResult<List<String>> findUserIdsByDepartmentId(@RequestParam("departmentId") String departmentId);

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

    /**
     * 根据ID获取部门对象
     * @param id id集合
     * @return
     */
    @RequestMapping(value = "/department/getDepartmentById")
    RestResult<Department> getDepartmentById(@RequestParam("id") String id);

    /**
     * 获取用户所在部门及子部门的所有用户ID
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/department/getAllUserIdInUserDepartment")
    RestResult<List<User>> getAllUserIdInUserDepartment(@RequestParam("userId") String userId);

    /**
     * 检索所有的部门
     * @return
     */
    @RequestMapping(value = "/department/getAllDepartments")
    RestResult<List<Department>> getAllDepartments();

    /**
     * 根据parentId获取组织机构树
     * @return
     */
    @RequestMapping(value = "/department/getDepartmentTreeByParentId")
    RestResult<List<CascaderResult>> getDepartmentTreeByParentId(@RequestParam("parentId") String parentId,@RequestParam("layer") Integer layer);

    /**
     * 获取字典数据
     *
     * @param indexId
     * @return
     */
    @RequestMapping(value = "/dictionary/getDictionaryListByIndexId")
    RestResult<List<DictionaryVo>> getDictionaryListByIndexId(@RequestParam("indexId") String indexId);

    /**
     * 根据条件查询角色列表
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/role/getRoleList")
    RestResult<List<Role>> getRoleList(@RequestBody Role role);

    /**
     * 检查元数据集是否存在
     * true 存在， false 不存在
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/metadataCollection/checkMetadataCollectionById")
    RestResult<Boolean> checkMetadataCollectionById(@RequestParam("id") String id);

    /**
     * 获取字典项信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/dictionary/getDictionaryById")
    RestResult<DictionaryVo> getDictionaryById(@RequestParam("id") String id);

    /**
     * 获取字典项信息
     *
     * @param indexId
     * @return
     */
    @RequestMapping(value = "/dictionary/getDictionaryByIndexId")
    RestResult<List<Dictionary>> getDictionaryByIndexId(@RequestParam("indexId") String indexId);



    /**
     * 新增用户
     * @return
     */
    @PostMapping("/user/insertUser")
    RestResult<Boolean> insertUser(@RequestBody User user);

    /**
     * 移除用户
     * @return
     */
    @PostMapping("/user/removeUser")
    RestResult<Boolean> removeUser(@RequestBody User user);



    /**
     * 新增机构
     * @return
     */
    @PostMapping("/user/insertDepartment")
    RestResult<Boolean> insertDepartment(@RequestBody Department department);

    /**
     * 移除机构
     * @return
     */
    @PostMapping("/user/removeDepartment")
    RestResult<Boolean> removeDepartment(@RequestBody Department department);


    /**
     * 根据角色类型获取用户列表
     * 角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
     * @param type
     * @return
     */
    @RequestMapping(value = "/user/getUserListByType")
    RestResult<List<UserVo>> getUserListByType(@RequestParam("type") Integer type);
}
