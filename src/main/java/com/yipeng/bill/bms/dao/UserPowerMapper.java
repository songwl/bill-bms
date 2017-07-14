package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.UserPower;

public interface UserPowerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPower record);

    int insertSelective(UserPower record);

    UserPower selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPower record);

    int updateByPrimaryKey(UserPower record);

    UserPower selectByUserId(Long UserId);

    int updateByPowerId();
}