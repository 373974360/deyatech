/*
Navicat MySQL Data Transfer

Source Server         : 本机虚拟机
Source Server Version : 50720
Source Host           : 192.168.78.133:3306
Source Database       : land

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-12-14 19:27:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `id_` varchar(32) NOT NULL COMMENT '用户编号',
  `account_` varchar(64) DEFAULT NULL COMMENT '登陆帐户',
  `password_` varchar(100) DEFAULT NULL COMMENT '密码',
  `emp_no` varchar(50) DEFAULT NULL COMMENT '工号',
  `phone_` varchar(50) DEFAULT NULL COMMENT '电话',
  `gender_` int(1) DEFAULT '0' COMMENT '性别(0:男;1:女)',
  `name_` varchar(30) DEFAULT NULL COMMENT '姓名',
  `avatar_` varchar(500) DEFAULT NULL COMMENT '头像',
  `dept_id` varchar(32) NOT NULL COMMENT '部门编号',
  `version_` int(4) DEFAULT '0' COMMENT '版本号',
  `enable_` int(1) DEFAULT '1' COMMENT '状态(0为禁用，1为正常，-1为删除)',
  `remark_` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建用户编号',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新用户编号',
  PRIMARY KEY (`id_`),
  KEY `account` (`account_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户信息';

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('1073539038679416834', 'aosiodjaoisd', '12345633', null, null, '1', null, null, '111', '1', '1', null, '2018-12-14 11:23:47', null, '2018-12-14 11:25:02', null);
