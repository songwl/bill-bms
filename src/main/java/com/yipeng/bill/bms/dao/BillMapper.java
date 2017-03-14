package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.Bill;

import java.util.List;
import java.util.Map;

public interface BillMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(Bill record);

    int insertSelective(Bill record);

    Bill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Bill record);

    int updateByPrimaryKey(Bill record);
    List<Bill> selectList(Map<String,Object> params);
    Long getBillListCount();
    List<Bill> selectAll();
}