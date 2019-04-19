/*
Navicat MySQL Data Transfer

Source Server         : 本机虚拟机
Source Server Version : 50720
Source Host           : 192.168.78.133:3306
Source Database       : land

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-12-21 19:03:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_dept
-- ----------------------------
DROP TABLE IF EXISTS `admin_dept`;
CREATE TABLE `admin_dept` (
  `id_` varchar(19) NOT NULL COMMENT '部门编号',
  `name_` varchar(50) NOT NULL COMMENT '部门名称',
  `short_name` varchar(255) DEFAULT NULL COMMENT '部门名称简称',
  `dept_code` varchar(50) DEFAULT NULL COMMENT '部门编码',
  `parent_id` varchar(19) NOT NULL COMMENT '上级部门编号',
  `tree_position` varchar(255) DEFAULT NULL COMMENT '树结构中的索引位置',
  `sort_no` int(3) DEFAULT NULL COMMENT '排序号',
  `enable_` int(1) DEFAULT NULL COMMENT '状态(0为禁用，1为正常，-1为删除)',
  `remark_` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(19) NOT NULL COMMENT '创建用户编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(19) NOT NULL COMMENT '更新用户编号',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version_` int(3) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统部门信息';

-- ----------------------------
-- Records of admin_dept
-- ----------------------------
INSERT INTO `admin_dept` VALUES ('1', '基本开发平台', '基本开发平台', 'JBKFPT', '0', null, '1', '1', null, '1', '2018-12-19 16:18:03', '1', '2018-12-19 18:17:01', '0');
INSERT INTO `admin_dept` VALUES ('2', '研发部', '研发部', 'YFB', '1', '&1', '0', '1', null, '1', '2018-12-19 16:18:03', '1', '2018-12-19 18:15:53', '0');
INSERT INTO `admin_dept` VALUES ('3', '测试部', '测试部', 'CSB', '1', '&1', '1', '1', null, '1', '2018-12-19 16:18:03', '1', '2018-12-19 18:15:53', '0');

-- ----------------------------
-- Table structure for admin_dict
-- ----------------------------
DROP TABLE IF EXISTS `admin_dict`;
CREATE TABLE `admin_dict` (
  `id_` varchar(19) NOT NULL COMMENT '数据字典编号',
  `index_id` varchar(19) NOT NULL COMMENT '数据字典索引编号',
  `code_` varchar(50) DEFAULT NULL COMMENT '英文代码',
  `code_text` varchar(100) DEFAULT NULL COMMENT '文字说明',
  `sort_no` int(11) DEFAULT NULL COMMENT '排序号',
  `editable_` int(1) NOT NULL DEFAULT '1' COMMENT '是否可编辑',
  `enable_` int(1) NOT NULL DEFAULT '1' COMMENT '状态(0为禁用，1为正常，-1为删除)',
  `remark_` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(19) NOT NULL COMMENT '创建用户编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(19) NOT NULL COMMENT '更新用户编号',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version_` int(3) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统数据字典明细信息';

-- ----------------------------
-- Records of admin_dict
-- ----------------------------

-- ----------------------------
-- Table structure for admin_dict_index
-- ----------------------------
DROP TABLE IF EXISTS `admin_dict_index`;
CREATE TABLE `admin_dict_index` (
  `id_` varchar(19) NOT NULL COMMENT '系统字典索引编号',
  `key_` varchar(50) DEFAULT NULL COMMENT '索引关键字',
  `name_` varchar(200) DEFAULT NULL COMMENT '索引名称',
  `enable_` int(1) DEFAULT '1' COMMENT '状态(0为禁用，1为正常，-1为删除)',
  `remark_` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(19) NOT NULL COMMENT '创建用户编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(19) NOT NULL COMMENT '更新用户编号',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version_` int(3) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统数据字典索引信息';

-- ----------------------------
-- Records of admin_dict_index
-- ----------------------------

-- ----------------------------
-- Table structure for admin_holiday
-- ----------------------------
DROP TABLE IF EXISTS `admin_holiday`;
CREATE TABLE `admin_holiday` (
  `id_` varchar(19) NOT NULL COMMENT '节假日编号',
  `year_` varchar(4) DEFAULT NULL COMMENT '年份',
  `date_` varchar(10) DEFAULT NULL COMMENT '节假日日期，格式为 yyyy-mm-dd',
  `enable_` int(1) NOT NULL DEFAULT '1' COMMENT '状态(0为禁用，1为正常，-1为删除)',
  `remark_` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(19) NOT NULL COMMENT '创建用户编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(19) NOT NULL COMMENT '更新用户编号',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version_` int(3) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统节假日信息';

-- ----------------------------
-- Records of admin_holiday
-- ----------------------------

-- ----------------------------
-- Table structure for admin_logs
-- ----------------------------
DROP TABLE IF EXISTS `admin_logs`;
CREATE TABLE `admin_logs` (
  `id_` varchar(19) NOT NULL COMMENT '系统日志id',
  `notes_` varchar(255) DEFAULT NULL COMMENT '执行方法说明',
  `method_` varchar(255) DEFAULT NULL COMMENT '执行的类跟方法',
  `request_url` varchar(255) DEFAULT NULL COMMENT '请求url',
  `user_id` varchar(19) DEFAULT NULL COMMENT '用户id',
  `params_` longtext COMMENT '请求参数',
  `time_` bigint(32) DEFAULT NULL COMMENT '消耗时间  毫秒',
  `ip_` varchar(20) DEFAULT NULL COMMENT '请求者ip地址',
  `enable_` int(1) DEFAULT '1' COMMENT '状态(0为禁用，1为正常，-1为删除)',
  `remark_` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(19) NOT NULL COMMENT '创建用户编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(19) NOT NULL COMMENT '更新用户编号',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version_` int(3) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志信息';

-- ----------------------------
-- Records of admin_logs
-- ----------------------------

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu` (
  `id_` varchar(19) NOT NULL COMMENT '菜单编号',
  `name_` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `type_` int(1) DEFAULT '2' COMMENT '菜单类型(0:CURD;1:系统菜单;2:业务菜单;)',
  `parent_id` varchar(19) DEFAULT NULL COMMENT '上级菜单编号',
  `tree_position` varchar(255) DEFAULT NULL COMMENT '树结构中的索引位置',
  `iconcls_` varchar(50) DEFAULT NULL COMMENT '节点图标CSS类名',
  `request_` varchar(100) DEFAULT NULL COMMENT '请求地址',
  `sort_no` int(2) DEFAULT NULL COMMENT '排序号',
  `permission_` varchar(500) DEFAULT NULL COMMENT '权限标识',
  `remark_` varchar(500) DEFAULT NULL COMMENT '备注',
  `enable_` int(1) DEFAULT '1' COMMENT '状态(0为禁用，1为正常，-1为删除)',
  `create_by` varchar(19) NOT NULL COMMENT '创建用户编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(19) NOT NULL COMMENT '更新用户编号',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version_` int(3) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单信息';

-- ----------------------------
-- Records of admin_menu
-- ----------------------------

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `id_` varchar(19) NOT NULL COMMENT '角色编号',
  `name_` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `type_` int(1) NOT NULL DEFAULT '1' COMMENT '角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)',
  `enable_` int(1) NOT NULL DEFAULT '1' COMMENT '状态(0为禁用，1为正常，-1为删除)',
  `remark_` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(19) NOT NULL COMMENT '创建用户编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(19) NOT NULL COMMENT '更新用户编号',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version_` int(3) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色信息';

-- ----------------------------
-- Records of admin_role
-- ----------------------------

-- ----------------------------
-- Table structure for admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_menu`;
CREATE TABLE `admin_role_menu` (
  `id_` varchar(19) NOT NULL COMMENT '角色菜单关联编号',
  `role_id` varchar(19) NOT NULL COMMENT '角色id',
  `menu_id` varchar(19) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色菜单关联信息';

-- ----------------------------
-- Records of admin_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id_` varchar(19) NOT NULL COMMENT '用户编号',
  `dept_id` varchar(19) NOT NULL COMMENT '部门编号',
  `name_` varchar(30) DEFAULT NULL COMMENT '姓名',
  `gender_` int(1) DEFAULT '0' COMMENT '性别(2:未知;1:男;0:女)',
  `phone_` varchar(50) DEFAULT NULL COMMENT '电话',
  `avatar_` varchar(500) DEFAULT NULL COMMENT '头像',
  `account_` varchar(64) DEFAULT NULL COMMENT '登陆帐户',
  `password_` varchar(100) DEFAULT NULL COMMENT '密码',
  `enable_` int(1) DEFAULT '1' COMMENT '状态(0为禁用，1为正常，-1为删除)',
  `remark_` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(19) NOT NULL COMMENT '创建用户编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(19) NOT NULL COMMENT '更新用户编号',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version_` int(3) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id_`),
  KEY `account` (`account_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户信息';

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('1', '2', '系统管理员', '1', '15398090571', 'http://new.zwfw.itl.gov.cn:8080/image/M00/00/0F/Chl_ElpewlSAYb9XABDDmShEUnY371.jpg', 'admin', '$2a$12$Wi7GxhVyblcIVXQKFc6NiueDzxo7ekIFConrzMpDDvDb/7H6i3HIC', '1', null, '1', '2018-12-19 16:18:03', '1', '2018-12-19 16:18:03', '0');

-- ----------------------------
-- Table structure for admin_role_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_user`;
CREATE TABLE `admin_role_user` (
  `id_` varchar(19) NOT NULL COMMENT '角色用户关联编号',
  `user_id` varchar(19) NOT NULL COMMENT '用户id',
  `role_id` varchar(19) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户角色关联信息';

-- ----------------------------
-- Records of admin_role_user
-- ----------------------------
