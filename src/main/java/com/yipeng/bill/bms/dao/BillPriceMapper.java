package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.BillPrice;

import java.util.List;
import java.util.Map;

public interface BillPriceMapper {
    int deleteByPrimaryKey(Long id);
    int deleteByBillId(Long id);
    int insert(BillPrice record);

    int insertSelective(BillPrice record);

    BillPrice selectByPrimaryKey(Long id);
    List<BillPrice> selectByBillId(Long BillId);
    List<BillPrice> selectByBillPrice(BillPrice billPrice);
    int updateByPrimaryKeySelective(BillPrice record);

    int updateByPrimaryKey(BillPrice record);
   int selectBillCount(Long UserId);
   Long selectByBillPriceOutMemberId(BillPrice billPrice);
    List<BillPrice>  selectByBillPriceSingle(BillPrice record);
    List<BillPrice>  selectByBillPriceList(Map<String,Object> params);

    List<BillPrice> selectByOutmemberList();
}