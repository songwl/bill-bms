package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    Role selectByRoleCode(String roleCode);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    List<Role> selectAllRole();
}