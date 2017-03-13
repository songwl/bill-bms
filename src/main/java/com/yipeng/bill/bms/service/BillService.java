package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.User;

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
    int saveBill(User user , String search, String url, String keyword, Long rankend, Long price, Long rankend1, Long price1, Long rankend2, Long price2, Long rankend3, Long price3);

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
