package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
public interface BillService {
    /**
     * 新增订单主表(相同价)
     * @param bill
     * @return
     */
    String saveBill(User user , String search, String url, String keyword, Long rankend, BigDecimal price, Long rankend1, BigDecimal price1, Long rankend2, BigDecimal price2, Long rankend3, BigDecimal price3);

     String savaDiffrentBill(User user,String dfsearch, String dfurl, String dfkeyword, Long dfrankend, String dfprice);
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
    Map<String, Object> findBillList(Map<String,Object> params);

    int updateBillPrice(Map<String,Object> params);


}
