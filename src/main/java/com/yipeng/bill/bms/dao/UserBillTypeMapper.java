package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.UserBillType;

import java.util.List;
import java.util.Map;

public interface UserBillTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserBillType record);

    int insertSelective(UserBillType record);

    UserBillType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBillType record);

    int updateByPrimaryKey(UserBillType record);

    List<UserBillType> selectByUserId(Long userId);

    UserBillType selectByUserIdAndTypeId(Map<String ,Object> params);

    int deleteByUserIdAndTypeId(Map<String ,Object> params);
}