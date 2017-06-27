package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillGroupRole;

import java.util.Map;

public interface BillGroupRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillGroupRole record);

    int insertSelective(BillGroupRole record);

    BillGroupRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillGroupRole record);

    int updateByPrimaryKey(BillGroupRole record);

    BillGroupRole selectByGroupRoleExsits(Map<String,Object> params);

    int deleteByGroupId(Long groupId);
}