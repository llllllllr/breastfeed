/*
Navicat MySQL Data Transfer

Source Server         : MRWY
Source Server Version : 80018
Source Host           : 47.101.204.119:3306
Source Database       : breastfeed

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-02-11 22:00:12
*/

SET FOREIGN_KEY_CHECKS=0;

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
