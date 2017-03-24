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

    Long getBillListCount(Map<String,Object> params);

    List<Bill> selectAll(Map<String,Object> params);

    Long  selectAllCount(int state);

    List<Bill> selectAgentBill(Map<String,Object> params);

    Long  selectAgentBillCount(Map<String,Object> params);

    List<Bill> selectAllSelective(Map<String,Object> params);

    List<Bill> selectBill(Map<String,Object> params);
    /**
     * 通过收款方来查询对应的订单
     * @param params
     * @return
     */
    List<Bill>  selectByInMemberId(Map<String,Object> params);
    /**
     * 通过付款方来查询对应的订单
     * @param params
     * @return
     */
    List<Bill>  selectByOutMemberId(Map<String,Object> params);
}