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
