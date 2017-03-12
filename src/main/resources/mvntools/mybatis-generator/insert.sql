-----角色初始化
INSERT INTO t_role(role_name,role_code) VALUES ('渠道商','DISTRIBUTOR');
INSERT INTO t_role(role_name,role_code) VALUES ('代理商','AGENT');
INSERT INTO t_role(role_name,role_code) VALUES ('客户','CUSTOMER');



-----数据字典初始化
INSERT INTO t_dict(`dict_group_code`,`dict_code`,`dict_name`,`valid`,`seq`,`dict_desc`) VALUES ('search','baidu','百度','Y',1,'百度搜索引擎');
INSERT INTO t_dict(`dict_group_code`,`dict_code`,`dict_name`,`valid`,`seq`,`dict_desc`) VALUES ('search','360','360','Y',1,'360搜索引擎');
INSERT INTO t_dict(`dict_group_code`,`dict_code`,`dict_name`,`valid`,`seq`,`dict_desc`) VALUES ('search','sougou','搜狗','Y',1,'搜狗搜索引擎');