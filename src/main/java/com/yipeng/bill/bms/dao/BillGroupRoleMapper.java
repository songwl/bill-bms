package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillGroupRole;

public interface BillGroupRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillGroupRole record);

    int insertSelective(BillGroupRole record);

    BillGroupRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillGroupRole record);

    int updateByPrimaryKey(BillGroupRole record);
}