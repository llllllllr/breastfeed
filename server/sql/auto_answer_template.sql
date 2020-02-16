/*
Navicat MySQL Data Transfer

Source Server         : MRWY
Source Server Version : 80018
Source Host           : 47.101.204.119:3306
Source Database       : breastfeed

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-02-11 22:00:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auto_answer_template
-- ----------------------------
DROP TABLE IF EXISTS `auto_answer_template`;
CREATE TABLE `auto_answer_template` (
  `consult_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_key` varchar(25) NOT NULL DEFAULT '问题',
  `answer_template` varchar(256) NOT NULL DEFAULT '答案',
  PRIMARY KEY (`consult_id`),
  UNIQUE KEY `question_key_UNIQUE` (`question_key`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='用户提问小程序客服答案模板';
