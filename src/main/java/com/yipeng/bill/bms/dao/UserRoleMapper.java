package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.UserRole;
import org.springframework.stereotype.Repository;


public interface UserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}