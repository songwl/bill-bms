package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    List<UserRole> selectByUserRole(UserRole userRole);

    List<UserRole> selectByRoleId(Long roleId);
    UserRole selectByUserId(Long userId);
    int getCount(Long roleId);


}
