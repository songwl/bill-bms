package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.UserCompany;

public interface UserCompanyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCompany record);

    int insertSelective(UserCompany record);

    UserCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCompany record);

    int updateByPrimaryKey(UserCompany record);
    UserCompany selectByUserId(Long UserId);
    UserCompany selectByWebsite(String website);
}