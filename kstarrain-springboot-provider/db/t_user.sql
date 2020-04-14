CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `deposit` decimal(10,2) DEFAULT NULL COMMENT '存款',
  `create_user` varchar(16) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(16) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `alive_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据存活标记',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `INDEX_ALIVE_FLAG` (`alive_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';


