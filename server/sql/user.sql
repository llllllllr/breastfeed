/*
Navicat MySQL Data Transfer

Source Server         : MRWY
Source Server Version : 80018
Source Host           : 47.101.204.119:3306
Source Database       : breastfeed

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-02-11 22:00:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `credit_id` varchar(255) NOT NULL,
  `pregnant_type` int(11) NOT NULL,
  `pregnant_week` varchar(255) NOT NULL,
  `job` varchar(45) DEFAULT NULL,
  `confinement_date` date NOT NULL,
  `confinement_week` int(11) NOT NULL,
  `confinement_type` int(11) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `user_password` varchar(45) NOT NULL,
  `user_token` char(32) DEFAULT NULL COMMENT '用户登录token',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `credit_id_UNIQUE` (`credit_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
