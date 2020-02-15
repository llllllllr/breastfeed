/*
Navicat MySQL Data Transfer

Source Server         : MRWY
Source Server Version : 80018
Source Host           : 47.101.204.119:3306
Source Database       : breastfeed

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-02-11 22:01:06
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
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `content` text,
  `img_url` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for audio
-- ----------------------------
DROP TABLE IF EXISTS `audio`;
CREATE TABLE `audio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `audioURL` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `cover_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1236822 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `coll_type` int(11) NOT NULL COMMENT '收藏的类型 1-文章 2-音频 3-视频',
  `coll_id` int(11) NOT NULL COMMENT '收藏id',
  PRIMARY KEY (`id`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for search_content
-- ----------------------------
DROP TABLE IF EXISTS `search_content`;
CREATE TABLE `search_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

-- ----------------------------
-- Table structure for vedio
-- ----------------------------
DROP TABLE IF EXISTS `vedio`;
CREATE TABLE `vedio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `vedioURL` varchar(255) NOT NULL,
  `cover_url` varchar(255) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for wechat_message_item
-- ----------------------------
DROP TABLE IF EXISTS `wechat_message_item`;
CREATE TABLE `wechat_message_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `from_user_id` varchar(100) NOT NULL COMMENT '''发送方''',
  `to_user_id` varchar(100) NOT NULL COMMENT '''接收方''',
  `message_type` int(11) NOT NULL COMMENT '消息类型 0表示文本 1表示图片 2表示视频 3表示可选择的文本',
  `message_content` varchar(255) NOT NULL COMMENT '消息类型为文本 -》 文本\n图片 视频 -》 存储路径',
  `time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '消息时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='聊天信息条目';
