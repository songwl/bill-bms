package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
@Service
public class BillServiceimpl implements BillService {
    @Autowired
    private BillMapper billMapper;

    /**
     * 实现新增订单
     * @param bill
     * @return
     */
    @Override
    public int saveBill(Bill bill) {
        return billMapper.insert(bill);
    }

    /**
     * 实现通过主键ID查订单
     * @param id
     * @return
     */
    @Override
    public Bill getBillById(Long id) {
        return billMapper.selectByPrimaryKey(id);
    }

    /**
     * 实现通过参数来查询集合
     * @param params
     * @return
     */
    @Override
    public List<Bill> findBillList(Map<String, Object> params) {
        return billMapper.selectList(params);
    }

}
