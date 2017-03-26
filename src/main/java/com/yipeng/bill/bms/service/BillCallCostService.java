package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.Bill;

/**
 * Created by song on 17/3/26.
 * 计算消费金额服务
 */
public interface BillCallCostService {

    int updateCallCost(Bill bill);
}
