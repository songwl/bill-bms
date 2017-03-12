package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.dao.BillPriceMapper;
import com.yipeng.bill.bms.dao.DictMapper;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.domain.BillPrice;
import com.yipeng.bill.bms.domain.Dict;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
@Service
public class BillServiceimpl implements BillService {
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private BillPriceMapper billPriceMapper;
    /**
     * 实现新增订单
     * @param bill
     * @return
     */
    @Override
    public int saveBill(User user , String search,String url,String keyword,float rankend,float price,float rankend1,float price1,float rankend2,float price2,float rankend3,float price3) {
        Dict dict=dictMapper.selectByDictCode(search);
        Bill bill=new Bill();
        bill.setWebsite(url);
        bill.setKeywords(keyword);
        bill.setCreateUserId(user.getId());
        bill.setCreateTime(new Date());
        bill.setFirstRanking(51);
        bill.setNewRanking(51);
        bill.setWebAppId(123456);
        bill.setDayOptimization(1);
        bill.setAllOptimization(1);
        Long billId=billMapper.insert(bill);
        BillPrice billPrice=new BillPrice();
        billPrice.setBillId(billId);
        billPrice.setOutMemberId(user.getId());
        billPrice.setCreateTime(new Date());
        billPriceMapper.insert(billPrice);


        return  1;
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
