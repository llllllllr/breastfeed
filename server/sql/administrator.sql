/*
Navicat MySQL Data Transfer

Source Server         : MRWY
Source Server Version : 80018
Source Host           : 47.101.204.119:3306
Source Database       : breastfeed

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-02-11 21:59:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `administrator_id` int(11) NOT NULL AUTO_INCREMENT,
  `administrator_name` varchar(45) NOT NULL,
  `administrator_password` varchar(45) NOT NULL,
  `administrator_right` int(11) NOT NULL,
  `administrator_token` char(32) DEFAULT NULL,
  PRIMARY KEY (`administrator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('3', '1', '1', '1', 'df7455abf2eb44119c48a5b732263e05');
INSERT INTO `administrator` VALUES ('4', '2', '2', '2', 'df7455abf2eb44119c48a7b7a2263e05');
