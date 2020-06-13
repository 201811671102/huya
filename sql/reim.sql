/*
Navicat MySQL Data Transfer

Source Server         : ff
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : reim

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-06-13 17:43:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_reim_apply`
-- ----------------------------
DROP TABLE IF EXISTS `t_reim_apply`;
CREATE TABLE `t_reim_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `hospital` varchar(255) NOT NULL COMMENT '就诊医院',
  `referral_prove` varchar(255) NOT NULL COMMENT '转诊证明',
  `invoice` varchar(255) NOT NULL COMMENT '发票',
  `see_doctor_time` date NOT NULL COMMENT '看病具体时间',
  `student_id` int(11) NOT NULL COMMENT '学生外键',
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `t_reim_apply_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `t_student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_reim_apply
-- ----------------------------
INSERT INTO `t_reim_apply` VALUES ('1', '第一医院', ' fa', ' fafs', '2020-06-04', '2');
INSERT INTO `t_reim_apply` VALUES ('2', '第四医院', 'fasd', 'fasd', '2020-06-01', '3');
INSERT INTO `t_reim_apply` VALUES ('3', '第七医院', 'fasd', 'fsad', '2020-06-07', '1');

-- ----------------------------
-- Table structure for `t_reim_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_reim_record`;
CREATE TABLE `t_reim_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `hospital` varchar(255) NOT NULL COMMENT '就诊医院',
  `referral_prove` varchar(255) NOT NULL COMMENT '转诊证明',
  `invoice` varchar(255) NOT NULL COMMENT '发票',
  `see_doctor_time` date NOT NULL COMMENT '看病时间',
  `is_success` int(11) NOT NULL COMMENT '报销结果',
  `student_id` int(11) NOT NULL COMMENT '学生外键',
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `t_reim_record_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `t_student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_reim_record
-- ----------------------------
INSERT INTO `t_reim_record` VALUES ('1', '第一医院', 'java.text.SimpleDateFormat@5069d960e760a2ebf1b04674931e1d98e51df7a0.jpg', 'java.text.SimpleDateFormat@5069d96091a8880ea2d54d60a27b821a5ef794db.jpg', '2020-06-06', '0', '3');
INSERT INTO `t_reim_record` VALUES ('2', '中医', 'java.text.SimpleDateFormat@5069d960fb7d016bc1594656bb53fcea887ac5f1.jpg', 'java.text.SimpleDateFormat@5069d960ae0fb85860c94a7f9e44ed7c2b24e1ca.jpg', '2020-06-07', '1', '4');
INSERT INTO `t_reim_record` VALUES ('3', '西医', 'java.text.SimpleDateFormat@5069d96000c300b6c3c44143a1fa8350a27d1303.png', 'java.text.SimpleDateFormat@5069d960b1e4012fef314bd8bbbfa5fab835033d.webp', '2020-05-11', '1', '6');
INSERT INTO `t_reim_record` VALUES ('4', '第八医院', 'dsaf', 'df', '2020-05-11', '1', '1');

-- ----------------------------
-- Table structure for `t_student`
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `student_number` int(11) NOT NULL COMMENT '学号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `college` varchar(255) NOT NULL COMMENT '学院',
  `stu_class` varchar(255) NOT NULL COMMENT '班级',
  `body_number` bigint(20) NOT NULL COMMENT '身份证',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('1', 'wo', '111', '111', 'beida', '111', '111');
INSERT INTO `t_student` VALUES ('2', '兰卿', '12345', '12345', '北大', '1', '1111');
INSERT INTO `t_student` VALUES ('3', '张三', '1234567', '1234567', '南大', '7班', '8888');
INSERT INTO `t_student` VALUES ('4', '李四', '9999', '9999', '西大', '5班', '99999');
INSERT INTO `t_student` VALUES ('5', '迪丽热巴', '1456', 'dlrb123', '北电', '4', '4456');
INSERT INTO `t_student` VALUES ('6', '古力娜扎', '43997', '43997', '西大', '5班', '43997');

-- ----------------------------
-- Table structure for `t_worker`
-- ----------------------------
DROP TABLE IF EXISTS `t_worker`;
CREATE TABLE `t_worker` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `worker_number` int(11) NOT NULL COMMENT '职工号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `position` varchar(255) NOT NULL COMMENT '职务',
  `body_number` int(11) NOT NULL COMMENT '身份证',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_worker
-- ----------------------------
INSERT INTO `t_worker` VALUES ('1', '赵六', '1111', '1111', '老师', '11111');
INSERT INTO `t_worker` VALUES ('2', '李志', '1015', '2389', '老师', '5879');
