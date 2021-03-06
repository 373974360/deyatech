package com.deyatech.admin.feign.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deyatech.admin.entity.Dictionary;
import com.deyatech.admin.entity.*;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.admin.service.*;
import com.deyatech.admin.util.MetaUtils;
import com.deyatech.admin.vo.*;
import com.deyatech.common.entity.CascaderResult;
import com.deyatech.common.entity.EnumsResult;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.enums.EnableEnum;
import com.deyatech.common.utils.CascaderUtil;
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
    @Autowired
    RoleService roleService;

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
            requests = menus.stream().map(Menu::getRequest).distinct().filter(m -> StrUtil.isNotEmpty(m)).collect(Collectors.toList()).toArray(new String[0]);
        }
        return RestResult.ok(requests);
    }

    @Override
    public RestResult<UserVo> getByUser(@RequestBody User user) {
        User byBean = userService.getByBean(user);
        if(ObjectUtil.isNotNull(byBean)){
            return RestResult.ok(userService.setVoProperties(byBean));
        }else{
            return RestResult.ok(null);
        }
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
    public RestResult workIntervalDayAfter(Date startTime, Date endTime) {
        return RestResult.ok(holidayService.workIntervalDayAfter(startTime, endTime));
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
    public RestResult<List<Role>> getRolesByUserId(String userId) {
        List<Role> roles = new ArrayList<>();
        RoleUser bean = new RoleUser();
        bean.setUserId(userId);
        Collection<RoleUser> list = roleUserService.listByBean(bean);
        if (CollectionUtil.isNotEmpty(list)) {
            for (RoleUser ru : list) {
                roles.add(roleService.getById(ru.getRoleId()));
            }
        }
        return RestResult.ok(roles);
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

    @Override
    public RestResult<List<CascaderResult>> getDepartmentTreeByParentId(String parentId,Integer layer) {
        Collection<DepartmentVo> departmentVos = departmentService.getDepartmentTreeByParentId(parentId,layer);
        List<CascaderResult> cascaderResults = CascaderUtil.getResult("Id", "Name","TreePosition", null, departmentVos);
        return RestResult.ok(cascaderResults);
    }

    /**
     * 获取字典数据
     *
     * @param indexId
     * @return
     */
    @Override
    public RestResult<List<DictionaryVo>> getDictionaryListByIndexId(String indexId) {
        Dictionary dictionary = new Dictionary();
        dictionary.setIndexId(indexId);
        dictionary.setSortSql("sort_no asc");
        return RestResult.ok(dictionaryService.setVoProperties(dictionaryService.listByBean(dictionary)));
    }

    /**
     * 根据条件查询角色列表
     *
     * @param role
     * @return
     */
    @Override
    public RestResult<List<Role>> getRoleList(Role role) {
        return RestResult.ok(roleService.listByBean(role));
    }

    @Override
    public RestResult<Boolean> checkMetadataCollectionById(String id) {
        int count = metadataCollectionService.count(id);
        return RestResult.ok(count > 0 ? true : false);
    }

    /**
     * 获取字典项信息
     *
     * @param id
     * @return
     */
    @Override
    public RestResult<DictionaryVo> getDictionaryById(@RequestParam("id") String id) {
        if (StrUtil.isEmpty(id))
            return RestResult.ok(null);
        Dictionary dictionary = dictionaryService.getById(id);
        return RestResult.ok(dictionaryService.setVoProperties(dictionary));
    }

    @Override
    public RestResult<List<Dictionary>> getDictionaryByIndexId(String indexId) {
        if (StrUtil.isEmpty(indexId))
            return RestResult.ok(null);
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("index_id", indexId);
        return RestResult.ok(dictionaryService.list(queryWrapper));
    }

    @Override
    public RestResult insertUser(User user) {
        boolean result = userService.saveOrEdit(user);
        return RestResult.ok(result);
    }

    @Override
    public RestResult removeUser(User user) {
        boolean result = userService.removeByBean(user);
        if (result) {
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(user.getId());
            roleUserService.removeByBean(roleUser);
        }
        return RestResult.ok(result);
    }

    @Override
    public RestResult<Boolean> insertDepartment(Department department) {
        Department dept = departmentService.getById(department.getParentId());
        if(StrUtil.isBlank(department.getParentId())){
            department.setParentId("0");
        }
        if(ObjectUtil.isNotNull(dept)){
            if(StrUtil.isNotBlank(dept.getTreePosition())){
                department.setTreePosition(dept.getTreePosition()+"&"+department.getId());
            }else{
                department.setTreePosition("&"+department.getId());
            }
        }else if(!department.getParentId().equals("0")){
            department.setTreePosition("&"+department.getParentId());
        }
        boolean result = departmentService.saveOrUpdate(department);
        return RestResult.ok(result);
    }

    @Override
    public RestResult<Boolean> removeDepartment(Department department) {
        boolean result = departmentService.removeByBean(department);
        return RestResult.ok(result);
    }

    @Override
    public RestResult<List<UserVo>> getUserListByType(Integer type) {
        Role role = new Role();
        role.setType(type);
        Collection<Role> roleList = roleService.listByBean(role);
        List<User> userVoList = CollectionUtil.newArrayList();
        if(CollectionUtil.isNotEmpty(roleList)){
            for (Role role1:roleList){
                RoleUser roleUser = new RoleUser();
                roleUser.setRoleId(role1.getId());
                Collection<RoleUser> roleUsers = roleUserService.listByBean(roleUser);
                if(CollectionUtil.isNotEmpty(roleUsers)){
                    for (RoleUser roleUser1:roleUsers){
                        User user = userService.getById(roleUser1.getUserId());
                        if(ObjectUtil.isNotNull(user)){
                            userVoList.add(user);
                        }
                    }
                }
            }
        }
        return RestResult.ok(userService.setVoProperties(userVoList));
    }
}
