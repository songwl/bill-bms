  INSERT INTO t_role(role_name,role_code,role_desc) VALUES ('超级管理员','SUPER_ADMIN','');
  INSERT INTO t_role(role_name,role_code,role_desc) VALUES ('管理员','ADMIN','');
  INSERT INTO t_role(role_name,role_code,role_desc) VALUES ('专员','COMMISSIONER','');
  INSERT INTO t_role(role_name,role_code,role_desc) VALUES ('渠道商','DISTRIBUTOR','');
  INSERT INTO t_role(role_name,role_code,role_desc) VALUES ('代理商','AGENT','');
  INSERT INTO t_role(role_name,role_code,role_desc) VALUES ('客户','CUSTOMER','');




  INSERT INTO t_dict(`dict_group_code`,`dict_code`,`dict_name`,`valid`,`seq`,`dict_desc`) VALUES ('search','baidu','百度','Y',1,'百度搜索引擎');
  INSERT INTO t_dict(`dict_group_code`,`dict_code`,`dict_name`,`valid`,`seq`,`dict_desc`) VALUES ('search','360','360','Y',1,'360搜索引擎');
  INSERT INTO t_dict(`dict_group_code`,`dict_code`,`dict_name`,`valid`,`seq`,`dict_desc`) VALUES ('search','sougou','搜狗','Y',1,'搜狗搜索引擎');

  INSERT INTO t_dict(`dict_group_code`,`dict_code`,`dict_name`,`valid`,`seq`,`dict_desc`) VALUES ('search','wapbaidu','手机百度','Y',1,'手机百度搜索引擎');

  INSERT INTO t_dict(`dict_group_code`,`dict_code`,`dict_name`,`valid`,`seq`,`dict_desc`) VALUES ('search','wap360','手机360','Y',1,'手机360搜索引擎');

  INSERT INTO t_dict(`dict_group_code`,`dict_code`,`dict_name`,`valid`,`seq`,`dict_desc`) VALUES ('search','wapsougou','手机搜狗','Y',1,'手机搜狗搜索引擎');