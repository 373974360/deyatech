package com.deyatech.admin.feign.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.deyatech.admin.entity.*;
import com.deyatech.admin.entity.Dictionary;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.admin.service.*;
import com.deyatech.admin.util.MetaUtils;
import com.deyatech.admin.vo.DictionaryVo;
import com.deyatech.admin.vo.HolidayVo;
import com.deyatech.admin.vo.MetadataCollectionVo;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.entity.EnumsResult;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.enums.EnableEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
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

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    HolidayService holidayService;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    MetadataCollectionService metadataCollectionService;

    @Autowired
    MetaUtils metaUtils;

    @Autowired
    DepartmentService departmentService;

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

    /**
     * 根据用户工号取得用户信息
     * @param empNo
     * @return
     */
    @Override
    public RestResult<UserVo> getUserByEmpNo(String empNo) {
        UserVo userVo = userService.getUserByEmpNo(empNo);
        return RestResult.ok(userVo);
    }
    /**
     * 根据年份查询节假日
     * @param holiday
     * @return
     */
    @Override
    public RestResult<Collection<HolidayVo>> listByHoliday(Holiday holiday) {
        Collection<Holiday> holidays = holidayService.listByBean(holiday);
        Collection<HolidayVo> holidayVos = holidayService.setVoProperties(holidays);
        if (holidayVos.size() == 0){
            holidayVos = null;
        }
        return RestResult.ok(holidayVos);
    }

    /**
     * 返回指定日期 n 个工作日（跳过设置的节假日）之后的日期
     *
     * @param startTime
     * @param workDay
     * @return
     */
    @Override
    public RestResult<Date> workDayAfter(@RequestParam("startTime") Date startTime, @RequestParam("workDay") Integer workDay) {
        return RestResult.ok(holidayService.workDayAfter(startTime, workDay));
    }

    /**
     * 计算某个日期几个小时之后的时间
     *
     * @param startTime
     * @param limitHour
     * @return
     */
    @Override
    public RestResult<Date> workHourAfter(@RequestParam("startTime") Date startTime, @RequestParam("limitHour") Integer limitHour) {
        return RestResult.ok(holidayService.workHourAfter(startTime, limitHour));
    }

    @Override
    public RestResult<DictionaryVo> getDictionary(String key, String code) {
        Dictionary dictionary = new Dictionary();
        dictionary.setIndexId(key);
        dictionary.setCode(code);
        dictionary = dictionaryService.getByBean(dictionary);
        return RestResult.ok(dictionaryService.setVoProperties(dictionary));
    }

    @Override
    public RestResult<DictionaryVo> getKdgsDic(String remark) {
        Dictionary dictionary = new Dictionary();
        dictionary.setIndexId("kdgs");
        dictionary.setRemark(remark);
        dictionary.setEnable(EnableEnum.ENABLE.getCode());
        dictionary = dictionaryService.getByBean(dictionary);
        return RestResult.ok(dictionaryService.setVoProperties(dictionary));
    }

    /**
     * 根据用户编号获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public RestResult<UserVo> getUserByUserId(String userId) {
        User user = userService.getById(userId);
        return RestResult.ok(userService.setVoProperties(user));
    }

    /**
     * 是否是节假日
     * @param date
     * @return
     */
    @Override
    public RestResult<Boolean> isHoliday(@RequestBody Date date) {
        Boolean holiday = holidayService.isHoliday(date);
        return RestResult.ok(holiday);
    }

    @Override
    public RestResult<List<String>> getUserIdsByRoleId(String roleId) {
        List<String> userIds = new ArrayList<>();

        RoleUser bean = new RoleUser();
        bean.setRoleId(roleId);
        Collection<RoleUser> list = roleUserService.listByBean(bean);
        for (RoleUser ru : list) {
            userIds.add(ru.getUserId());
        }

        return RestResult.ok(userIds);
    }

    @Override
    public RestResult<List<String>> getRoleIdsByUserId(String userId) {
        List<String> roleIds = new ArrayList<>();
        RoleUser bean = new RoleUser();
        bean.setUserId(userId);
        Collection<RoleUser> list = roleUserService.listByBean(bean);
        if (CollectionUtil.isNotEmpty(list)) {
            for (RoleUser ru : list) {
                roleIds.add(ru.getRoleId());
            }
        }
        return RestResult.ok(roleIds);
    }

    @Override
    public RestResult<List<String>> findUserIdsByDepartmentId(String departmentId) {
        List<String> userIds = new ArrayList<>();

        User user = new User();
        user.setDepartmentId(departmentId);
        Collection<User> list = userService.listByBean(user);
        for (User u : list) {
            userIds.add(u.getId());
        }
        return RestResult.ok(userIds);
    }

    /**
     * 获取所有元数据集（包括关联的元数据）信息
     *
     * @param metadataCollectionVo
     * @return
     */
    @Override
    public RestResult<List<MetadataCollectionVo>> findAllData(MetadataCollectionVo metadataCollectionVo) {
        List<MetadataCollectionVo> data = metadataCollectionService.findAllData(metadataCollectionVo);
        return RestResult.ok(data);
    }

    /**
     * 插入一条元数据信息
     *
     * @param metaDataCollectionId 元数据集id
     * @param contentId 元数据记录id
     * @param contentMap 元数据信息
     * @return
     */
    @Override
    public RestResult<String> saveOrUpdateMetadata(String metaDataCollectionId, String contentId, @RequestBody Map contentMap) {

        MetadataCollectionVo metadataCollectionVo = this.getMetadataCollectionVoById(metaDataCollectionId);
        // 插入数据
        if (StrUtil.isEmpty(contentId)) {
            contentId = MetaUtils.insert(metadataCollectionVo, contentMap);
            return RestResult.ok(contentId);
        } // 更新
        else {
            MetaUtils.updateById(metadataCollectionVo, contentId, contentMap);
            return RestResult.ok();
        }
    }

    /**
     * 根据id查询元数据记录
     * @param metaDataCollectionId 元数据集id
     * @param contentId 元数据记录id
     * @return
     */
    @Override
    public RestResult<Map> getMetadataById(String metaDataCollectionId, String contentId) {
        MetadataCollectionVo metadataCollectionVo = this.getMetadataCollectionVoById(metaDataCollectionId);
        Map content = MetaUtils.selectById(metadataCollectionVo, contentId);
        return RestResult.ok(content);
    }

    private MetadataCollectionVo getMetadataCollectionVoById(String metaDataCollectionId) {
        MetadataCollectionVo metadataCollectionVo = new MetadataCollectionVo();
        metadataCollectionVo.setId(metaDataCollectionId);
        List<MetadataCollectionVo> data = metadataCollectionService.findAllData(metadataCollectionVo);
        return data.get(0);
    }

    /**
     * 根据id删除元数据记录
     * @param mapList id集合
     * @return
     */
    @Override
    public RestResult removeMetadataByIds(List<Map> mapList) {
        mapList.stream().forEach(m ->
            this.removeMetadataById(m)
        );
        return RestResult.ok();
    }

    @Override
    public RestResult<Department> getDepartmentById(String id) {
        return RestResult.ok(departmentService.getById(id));
    }

    private void removeMetadataById(Map map) {
        MetadataCollectionVo metadataCollectionVo = this.getMetadataCollectionVoById((String) map.get("metaDataCollectionId"));
        MetaUtils.deleteById(metadataCollectionVo, (String) map.get("contentId"));
    }

    /**
     * 获取用户所在部门的所有用户
     *
     * @param userId
     * @return
     */
    @Override
    public RestResult<List<User>> getAllUserIdInUserDepartment(@RequestParam("userId") String userId) {
        return RestResult.ok(userService.getAllUserIdInUserDepartment(userId));
    }

    /**
     * 检索所有的部门
     * @return
     */
    public RestResult<List<Department>> getAllDepartments() {
        return RestResult.ok(departmentService.list());
    }
}
