  CREATE TABLE IF NOT EXISTS `t_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `user_name` VARCHAR(64) NOT NULL COMMENT '用户名',
    `real_name` VARCHAR(45) NULL COMMENT '真实姓名',
    `password` VARCHAR(64) NOT NULL COMMENT '密码',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1：正常状态；0：被屏蔽 （待审核）',
    `create_user_id` BIGINT NULL COMMENT '创建人id',
    `create_time` DATETIME NULL COMMENT '创建时间',
    `update_user_id` BIGINT NULL COMMENT '修改人id',
    `update_time` DATETIME NULL COMMENT '修改时间',
    `contact` VARCHAR(45) NULL COMMENT '联系人',
    `qq` VARCHAR(20) NULL COMMENT 'QQ号',
    `phone` VARCHAR(20) NULL COMMENT '手机号',
    `last_login_time` DATETIME NULL COMMENT '最后登录时间',
    `login_count` INT NULL COMMENT '登录次数',
     `daili_Role` INT NULL COMMENT '是否有代理权限',
    PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB;


  CREATE TABLE IF NOT EXISTS `t_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `role_name` VARCHAR(64) NOT NULL COMMENT '角色名',
    `role_code` VARCHAR(45) NULL COMMENT '角色编码',
    `role_desc` VARCHAR(64) NOT NULL COMMENT '角色描述',
    PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  COMMENT = '角色表';

  CREATE TABLE IF NOT EXISTS `t_user_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `user_id` BIGINT NOT NULL COMMENT '',
    `role_id` BIGINT NOT NULL COMMENT '',
    PRIMARY KEY (`id`)  COMMENT '',
    INDEX `fk_t_user_role_t_user_idx` (`user_id` ASC)  COMMENT '',
    INDEX `fk_t_user_role_t_role1_idx` (`role_id` ASC)  COMMENT '',
    CONSTRAINT `fk_t_user_role_t_user`
      FOREIGN KEY (`user_id`)
      REFERENCES `t_user` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
    CONSTRAINT `fk_t_user_role_t_role1`
      FOREIGN KEY (`role_id`)
      REFERENCES `t_role` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)
  ENGINE = InnoDB
  COMMENT = '用户角色关联表';

  CREATE TABLE IF NOT EXISTS `t_fund_account` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `user_id` BIGINT NOT NULL COMMENT '',
    `balance` DECIMAL(10,2) NULL COMMENT '余额',
    `create_time` DATETIME NULL COMMENT '',
    `create_user_id` BIGINT NULL COMMENT '',
    `update_time` DATETIME NULL COMMENT '',
    `update_user_id` BIGINT NULL COMMENT '',
    PRIMARY KEY (`id`)  COMMENT '',
    INDEX `fk_t_customer_fund_t_user1_idx` (`user_id` ASC)  COMMENT '',
    CONSTRAINT `fk_t_customer_fund_t_user1`
      FOREIGN KEY (`user_id`)
      REFERENCES `t_user` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)
  ENGINE = InnoDB
  COMMENT = '客户资金帐户表';

  CREATE TABLE IF NOT EXISTS `t_fund_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `fund_account_id` BIGINT NOT NULL COMMENT '',
    `change_amount` DECIMAL(10,2) NULL COMMENT '变动金额',
    `balance` DECIMAL(10,2) NULL COMMENT '余额',
    `change_time` DATE NULL COMMENT '变动时间',
    `item_type` VARCHAR(64) NULL COMMENT '消费形式',
    `priceId` BIGINT  NULL COMMENT '单价Id',
    PRIMARY KEY (`id`)  COMMENT '',
    INDEX `fk_t_fund_item_t_fund_account1_idx` (`fund_account_id` ASC)  COMMENT '',
    CONSTRAINT `fk_t_fund_item_t_fund_account1`
      FOREIGN KEY (`fund_account_id`)
      REFERENCES `t_fund_account` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)
  ENGINE = InnoDB
  COMMENT = '客户资金帐户表';

  CREATE TABLE IF NOT EXISTS `t_bill` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `website` VARCHAR(256) NULL COMMENT '网址',
    `keywords` VARCHAR(128) NULL COMMENT '关键字',
    `create_time` DATETIME NULL COMMENT '',
    `update_time` DATETIME NULL COMMENT '',
    `create_user_id` BIGINT NULL COMMENT '',
    `update_user_id` BIGINT NULL COMMENT '',
    `first_ranking` INT NULL COMMENT '初始排名',
    `new_ranking` INT NULL COMMENT '新的排名',
     `change_ranking` INT NULL COMMENT '今昨日排名变化',
    `web_app_id` INT NULL COMMENT '订单接口调用查询ID',
    `web_app_id1` INT NULL COMMENT '调点击接口调用查询ID',
    `standard_days` INT NULL COMMENT '达标天数',
    `day_optimization` INT NULL COMMENT '日优化（初始值为1）可以调整数值，调整参数时，调用接口传入参数',
    `all_optimization` INT NULL COMMENT '总优化（计算总优化数）',
    `state` INT NULL COMMENT '订单状态',
    `opstate` INT NULL COMMENT '订单状态1（调点击状态(0:未优化状态 1：优化中 2：离线)）',
     `bill_type` INT NULL COMMENT '订单属性（1,正常单，2包年单，3，快排单）',
     `bill_ascription` BIGINT NULL COMMENT '订单专员归属',
     `apply_state` BIGINT  NULL COMMENT '申请订单状态（1,代理商 2,渠道商）',
    PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  COMMENT = '计费单表';

  CREATE TABLE IF NOT EXISTS `t_bill_search_support` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `bill_id` BIGINT NOT NULL COMMENT '',
    `search_support` VARCHAR(50) NULL COMMENT '搜索引擎名',
    PRIMARY KEY (`id`)  COMMENT '',
    INDEX `fk_t_bill_search_support_t_bill1_idx` (`bill_id` ASC)  COMMENT '',
    CONSTRAINT `fk_t_bill_search_support_t_bill1`
      FOREIGN KEY (`bill_id`)
      REFERENCES `t_bill` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)
  ENGINE = InnoDB
  COMMENT = '搜索引擎支持对象表';

  CREATE TABLE IF NOT EXISTS `t_bill_feedback` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `bill_id` BIGINT NOT NULL COMMENT '',
    `bill_feedback_title` VARCHAR(200) NULL COMMENT '反馈标题',
    `bill_feedback_content` TEXT NULL COMMENT '反馈内容',
     `create_user_id` BIGINT NULL COMMENT '创建人id',
    `create_time` DATETIME NULL COMMENT '创建时间',
    `update_user_id` BIGINT NULL COMMENT '修改人id',
    `update_time` DATETIME NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)  COMMENT '',
    INDEX `fk_t_bill_feedback_t_bill1_idx` (`bill_id` ASC)  COMMENT '',
    CONSTRAINT `fk_t_bill_feedback_t_bill1`
      FOREIGN KEY (`bill_id`)
      REFERENCES `t_bill` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)
  ENGINE = InnoDB
  COMMENT = '订单反馈信息表';


  CREATE TABLE IF NOT EXISTS `t_bill_price` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `bill_id` BIGINT NOT NULL COMMENT '',
    `price` DECIMAL(10,2) NULL COMMENT '',
    `bill_ranking_standard` BIGINT NULL COMMENT '',
    `in_member_id` BIGINT NULL COMMENT '收款方id',
    `out_member_id` BIGINT NULL COMMENT '付款方id',
    `create_time` DATETIME NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)  COMMENT '',
    INDEX `fk_t_bill_price_t_bill1_idx` (`bill_id` ASC)  COMMENT '',
    CONSTRAINT `fk_t_bill_price_t_bill1`
      FOREIGN KEY (`bill_id`)
      REFERENCES `t_bill` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)
  ENGINE = InnoDB
  COMMENT = '计费价格表';

  CREATE TABLE IF NOT EXISTS `t_bill_cost` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `t_bill_id` BIGINT NOT NULL COMMENT '',
    `t_bill_price_id` BIGINT NOT NULL COMMENT '',
    `cost_amount` DECIMAL(10,2) NULL COMMENT '消费金额',
    `cost_date` DATETIME NULL COMMENT '',
    `ranking` INT NULL COMMENT '排名',
    PRIMARY KEY (`id`)  COMMENT '',
    INDEX `fk_t_bill_cost_t_bill1_idx` (`t_bill_id` ASC)  COMMENT '',
    INDEX `fk_t_bill_cost_t_bill_price1_idx` (`t_bill_price_id` ASC)  COMMENT '',
    CONSTRAINT `fk_t_bill_cost_t_bill1`
      FOREIGN KEY (`t_bill_id`)
      REFERENCES `t_bill` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,

  ENGINE = InnoDB
  COMMENT = '计费消费表';

  CREATE TABLE IF NOT EXISTS `t_dict` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `dict_group_code` VARCHAR(64) NOT NULL COMMENT '字典分组code',
    `dict_code` VARCHAR(64) NOT NULL COMMENT '字典code',
    `dict_name` VARCHAR(64) NOT NULL COMMENT '字典名称',
    `valid` CHAR(1) NOT NULL DEFAULT 'Y' COMMENT '是否有效',
    `seq` INT NOT NULL DEFAULT 0 COMMENT '排序',
    `dict_desc` VARCHAR(256) NULL COMMENT '',
    PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  COMMENT = '数据字典表';

 CREATE TABLE IF NOT EXISTS `t_notice` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `title` VARCHAR(256) NULL COMMENT '标题',
    `content` VARCHAR(128) NULL COMMENT '内容',
    `create_time` DATETIME NULL COMMENT '发布时间',
    `update_time` DATETIME NULL COMMENT '',
    `create_user_id` BIGINT NULL COMMENT '发布对象',
    `update_user_id` BIGINT NULL COMMENT '',
    PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  COMMENT = '通知表';


 CREATE TABLE IF NOT EXISTS `t_notice_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `t_notice_id`  BIGINT NOT NULL COMMENT '通知表ID',
    `user_id`  BIGINT NOT NULL COMMENT '用户编号',
    `status` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '1：已阅读；0：未阅读 ',
    `create_time` DATETIME NULL COMMENT '发布时间',
    `update_time` DATETIME NULL COMMENT '',
    `create_user_id` BIGINT NULL COMMENT '发布对象',
    `update_user_id` BIGINT NULL COMMENT '',
    PRIMARY KEY (`id`)  COMMENT '')
  ENGINE = InnoDB
  COMMENT = '通知表';


  CREATE TABLE IF NOT EXISTS `t_bill_optimization` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
    `bill_id` BIGINT NOT NULL COMMENT '',
    `optimization_count` INT  NULL COMMENT '优化次数',
    `optimization_date`  DATETIME  NULL COMMENT '优化日期',
    PRIMARY KEY (`id`)  COMMENT '',
    INDEX `fk_t_bill_optimization_t_bill1_idx` (`bill_id` ASC)  COMMENT '',
    CONSTRAINT `fk_t_bill_optimization_t_bill1`
      FOREIGN KEY (`bill_id`)
      REFERENCES `t_bill` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION)
  ENGINE = InnoDB
  COMMENT = '优化指数统计表';

CREATE TABLE `t_user_hyperlink` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `web_site` varchar(200) NOT NULL COMMENT '绑定域名',
  `title` varchar(20) NOT NULL,
  `hyperlink` varchar(100) NOT NULL,
     `create_time` DATETIME NULL COMMENT '发布时间',
    `update_time` DATETIME NULL COMMENT '',
    `create_user_id` BIGINT NULL COMMENT '发布对象',
    `update_user_id` BIGINT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB
 COMMENT = '用户超链接表';

CREATE TABLE `t_user_imgurl` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `userId` BIGINT NOT NULL,
  `web_site` varchar(200) NOT NULL COMMENT '绑定域名',
  `img_url` varchar(200) NOT NULL COMMENT '图片路径',
     `create_time` DATETIME NULL COMMENT '发布时间',
    `update_time` DATETIME NULL COMMENT '',
    `create_user_id` BIGINT NULL COMMENT '发布对象',
    `update_user_id` BIGINT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB
 COMMENT = '用户图片表';

CREATE TABLE `t_user_company` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_Id` BIGINT NOT NULL,
  `web_site` varchar(200) NOT NULL COMMENT '绑定域名',
  `user_company_name` varchar(200) DEFAULT NULL,
  `user_logoImg_url` varchar(200) DEFAULT NULL,
     `create_time` DATETIME NULL COMMENT '发布时间',
    `update_time` DATETIME NULL COMMENT '',
    `create_user_id` BIGINT NULL COMMENT '发布对象',
    `update_user_id` BIGINT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB
COMMENT = '用户公司信息表';



CREATE TABLE `t_forbidden_words` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `words` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB
COMMENT = '违禁词库.';

CREATE TABLE `logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `optype` int(11) NOT NULL,
  `opobj` varchar(255) CHARACTER SET latin1 NOT NULL,
  `opremake` varchar(255) CHARACTER SET latin1 NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

CREATE TABLE `searchenginecompletionrate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `allCompleteness` double DEFAULT NULL,
  `baiduCompleteness` double DEFAULT NULL,
  `baiduWapCompleteness` double DEFAULT NULL,
  `sanliulingCompleteness` double DEFAULT NULL,
  `sougouCompleteness` double DEFAULT NULL,
  `shenmaCompleteness` double DEFAULT NULL,
  `createTime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fk_searchEngine_idx` (`userId`,`createTime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_bill_click_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `week_click` int(11) DEFAULT NULL,
  `month_click` int(11) DEFAULT NULL,
  `all_click` int(11) DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成本统计表';

CREATE TABLE `t_bill_commissioner_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `week_cost` decimal(10,0) DEFAULT NULL,
  `month_cost` decimal(10,0) DEFAULT NULL,
  `all_cost` decimal(10,0) DEFAULT NULL,
  `bill_count` bigint(20) DEFAULT NULL,
  `bill_approval_rate` decimal(10,0) DEFAULT NULL,
  `keywords_approval_rate` decimal(10,0) DEFAULT NULL,
  `bill_month_add_count` int(11) DEFAULT NULL,
  `user_expect_achievement` decimal(10,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专员统计表';

CREATE TABLE `t_bill_distributor_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `week_cost` decimal(10,0) DEFAULT NULL,
  `month_cost` decimal(10,0) DEFAULT NULL,
  `all_cost` decimal(10,0) DEFAULT NULL,
  `bill_count` bigint(20) DEFAULT NULL,
  `bill_approval_rate` decimal(10,0) DEFAULT NULL,
  `keywords_approval_rate` decimal(10,0) DEFAULT NULL,
  `bill_month_add_count` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道商统计表';

CREATE TABLE `keywords_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `keywords` varchar(255) NOT NULL,
  `baidu` varchar(255) DEFAULT NULL,
  `sougou` varchar(255) DEFAULT NULL,
  `sanliuling` varchar(255) DEFAULT NULL,
  `sjbaidu` varchar(255) DEFAULT NULL,
  `shenma` varchar(255) DEFAULT NULL,
  `apiid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `weiyi` (`keywords`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;

CREATE TABLE `inbox` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sendId` bigint(20) DEFAULT NULL,
  `sendUserId` varchar(255) DEFAULT NULL COMMENT '接收者编号',
  `InUserId` varchar(255) DEFAULT NULL,
  `mailType` int(11) DEFAULT NULL,
  `Title` varchar(500) DEFAULT NULL,
  `Content` varchar(5000) DEFAULT NULL,
  `dealtState` int(11) DEFAULT NULL,
  `affairState` int(11) DEFAULT NULL,
  `InTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

CREATE TABLE `messagereply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `MessageId` bigint(20) NOT NULL,
  `SendId` varchar(255) NOT NULL,
  `InId` varchar(255) NOT NULL,
  `ReplyContent` varchar(255) NOT NULL,
  `ReplyTime` datetime NOT NULL,
  `MessageType` int(11) NOT NULL,
  `DealtState` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8;

CREATE TABLE `noticepublish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `SendId` varchar(255) DEFAULT NULL,
  `InRole` varchar(255) DEFAULT NULL,
  `MailType` int(11) DEFAULT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `Content` varchar(255) DEFAULT NULL,
  `dealtState` int(11) DEFAULT NULL,
  `SendTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

CREATE TABLE `offerset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `tokenId` varchar(255) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `requestSecond` int(11) DEFAULT NULL,
  `surplusSecond` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `sendbox` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sendUserId` varchar(255) DEFAULT NULL,
  `InUserId` varchar(255) DEFAULT NULL,
  `mailType` int(11) DEFAULT NULL,
  `Title` varchar(500) DEFAULT NULL,
  `Content` varchar(5000) DEFAULT NULL,
  `dealtState` int(11) DEFAULT NULL,
  `affairState` int(11) DEFAULT NULL,
  `SendTime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;