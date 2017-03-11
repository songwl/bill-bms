package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.Bill;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
public interface BillService {
    /**
     * 新增订单主表
     * @param bill
     * @return
     */
    int saveBill(Bill bill);

    /**
     * 通过ID查询订单
     * @param id
     * @return
     */
    Bill getBillById(Long id);

    /**
     * 根据参数查询订单列表
     * @param params
     * @return
     */
    List<Bill> findBillList(Map<String,Object> params);

}
