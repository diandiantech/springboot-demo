#SET MODE mysql;

DROP TABLE IF EXISTS user_info;
CREATE TABLE `user_info` (
  `id` bigint(20) unsigned AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `name` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_unique_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';


DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `ROLE_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `REMARK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8 COMMENT = '角色表';

