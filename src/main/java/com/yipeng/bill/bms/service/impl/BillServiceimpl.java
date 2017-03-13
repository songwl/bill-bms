package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.dao.BillPriceMapper;
import com.yipeng.bill.bms.dao.BillSearchSupportMapper;
import com.yipeng.bill.bms.dao.DictMapper;
import com.yipeng.bill.bms.domain.*;
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
    @Autowired
    private BillSearchSupportMapper billSearchSupportMapper;
    /**
     * 实现新增订单(判断都未做)
     * @param bill
     * @return
     */
    @Override
    public int saveBill(User user , String search,String url,String keyword,Long rankend,Long price,Long rankend1,Long price1,Long rankend2,Long price2,Long rankend3,Long price3) {

        String[] urls=url.split("\n");
        String[] keywords=keyword.split("\n");
        if(urls.length==keywords.length)
        {

             for(int i=0;i<urls.length;i++)
             {
                //订单主表
                 Bill bill=new Bill();
                 bill.setWebsite(urls[i]);
                 bill.setKeywords(keywords[i]);
                 bill.setCreateUserId(user.getId());
                 bill.setCreateTime(new Date());
                 bill.setFirstRanking(51);
                 bill.setNewRanking(51);
                 bill.setWebAppId(123456);
                 bill.setDayOptimization(1);
                 bill.setAllOptimization(1);
                 bill.setState(1);
                 Long billId=billMapper.insert(bill);
                 //订单引擎表
                 BillSearchSupport billSearchSupport=new BillSearchSupport();
                 billSearchSupport.setBillId(bill.getId());
                 billSearchSupport.setSearchSupport(search);
                 billSearchSupportMapper.insert(billSearchSupport);
                 //订单单价表
                 BillPrice billPrice=new BillPrice();
                 billPrice.setBillId(bill.getId());
                 billPrice.setPrice(price);
                 billPrice.setBillRankingStandard(rankend);
                 billPrice.setInMemberId(user.getId());
                 billPrice.setCreateTime(new Date());
                 billPriceMapper.insert(billPrice);

             }

        }



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
