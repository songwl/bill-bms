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
  `balance` DECIMAL NULL COMMENT '余额',
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
  `change_amount` DECIMAL NULL COMMENT '变动金额',
  `change_time` DATETIME NULL COMMENT '变动时间',
  `item_type` VARCHAR(64) NULL COMMENT '消费形式',
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
  `web_app_id` INT NULL COMMENT '订单接口调用查询ID',
  `standard_days` INT NULL COMMENT '达标天数',
  `day_optimization` INT NULL COMMENT '日优化（初始值为1）可以调整数值，调整参数时，调用接口传入参数',
  `all_optimization` INT NULL COMMENT '总优化（计算总优化数）',
  `state` INT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`)  COMMENT '')
ENGINE = InnoDB
COMMENT = '计费单表';

CREATE TABLE IF NOT EXISTS `t_bill_search_support` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
  `bill_id` BIGINT NOT NULL COMMENT '',
  `search_support` VARCHAR(20) NULL COMMENT '搜索引擎名',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_t_bill_search_support_t_bill1_idx` (`bill_id` ASC)  COMMENT '',
  CONSTRAINT `fk_t_bill_search_support_t_bill1`
    FOREIGN KEY (`bill_id`)
    REFERENCES `t_bill` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '搜索引擎支持对象表';

CREATE TABLE IF NOT EXISTS `t_bill_price` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '',
  `bill_id` BIGINT NOT NULL COMMENT '',
  `price` DECIMAL NULL COMMENT '',
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
  `cost_amount` DECIMAL NULL COMMENT '消费金额',
  `cost_date` DATE NULL COMMENT '',
  `ranking` INT NULL COMMENT '排名',
  PRIMARY KEY (`id`)  COMMENT '',
  INDEX `fk_t_bill_cost_t_bill1_idx` (`t_bill_id` ASC)  COMMENT '',
  INDEX `fk_t_bill_cost_t_bill_price1_idx` (`t_bill_price_id` ASC)  COMMENT '',
  CONSTRAINT `fk_t_bill_cost_t_bill1`
    FOREIGN KEY (`t_bill_id`)
    REFERENCES `t_bill` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_bill_cost_t_bill_price1`
    FOREIGN KEY (`t_bill_price_id`)
    REFERENCES `t_bill_price` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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