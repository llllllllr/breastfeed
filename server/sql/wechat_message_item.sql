/*
Navicat MySQL Data Transfer

Source Server         : MRWY
Source Server Version : 80018
Source Host           : 47.101.204.119:3306
Source Database       : breastfeed

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-02-11 22:00:42
*/

SET FOREIGN_KEY_CHECKS=0;

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
