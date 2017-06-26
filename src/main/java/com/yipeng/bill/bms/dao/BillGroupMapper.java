package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillGroup;

import java.util.List;
import java.util.Map;

public interface BillGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BillGroup record);

    int insertSelective(BillGroup record);

    BillGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BillGroup record);

    int updateByPrimaryKey(BillGroup record);

    List<Map<String, Object>> selectBillGroupTable(Map<String, Object> params);
    int selectBillGroupTableCount(Map<String, Object> params);
    BillGroup selectByGroupNameExsits(Map<String, Object> params);
    //获取分组
    List<BillGroup> selectByUserId(Map<String, Object> params);
}