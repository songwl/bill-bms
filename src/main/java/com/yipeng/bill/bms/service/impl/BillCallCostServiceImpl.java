package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.BillCostMapper;
import com.yipeng.bill.bms.dao.BillPriceMapper;
import com.yipeng.bill.bms.dao.FundAccountMapper;
import com.yipeng.bill.bms.dao.FundItemMapper;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.BillCallCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by song on 17/3/26.
 */
@Service("BillCallCostService")
public class BillCallCostServiceImpl implements BillCallCostService {

    @Autowired
    private BillPriceMapper billPriceMapper;

    @Autowired
    private BillCostMapper billCostMapper;

    @Autowired
    private FundAccountMapper fundAccountMapper;

    @Autowired
    private FundItemMapper fundItemMapper;


    @Override
    public int updateCallCost(Bill bill) {
        //2.根据计算单Bill查询单价BillPrice
        List<BillPrice> prices = billPriceMapper.selectByBillId(bill.getId());

        Map<Long,List<BillPrice>> userPriceMap = new HashMap<>();
        for (BillPrice billPrice : prices) {
            Long userId = billPrice.getOutMemberId(); //付款人
            if (!userPriceMap.containsKey(userId)) {
                userPriceMap.put(userId,new ArrayList<BillPrice>());
            }
            userPriceMap.get(userId).add(billPrice);
        }

        //3.根据最新的排名计算价钱,产生消费
        int newRanking = bill.getNewRanking().intValue();
        for (Long userId : userPriceMap.keySet()) { //对每个付款人进行计算
            List<BillPrice> billPriceList = userPriceMap.get(userId);
            //根据设置排名的数据进行排序,排名数据越小在前面
            Collections.sort(billPriceList, new Comparator<BillPrice>() {
                @Override
                public int compare(BillPrice o1, BillPrice o2) {
                    Long r1 = o1.getBillRankingStandard();
                    Long r2 = o2.getBillRankingStandard();
                    return r1.compareTo(r2);
                }
            });

            //判断排名
            for (BillPrice billPrice : billPriceList) {
                if (billPrice.getBillRankingStandard().intValue()>newRanking){ //最新排名在次设置的排名,即达到排名优化标准
                    BillCost billCost = new BillCost();
                    billCost.settBillId(bill.getId());
                    billCost.settBillPriceId(billPrice.getId());
                    billCost.setCostAmount(billPrice.getPrice()); //消费金额
                    billCost.setCostDate(new Date()); //消费日期
                    billCost.setRanking(newRanking);
                    billCostMapper.insert(billCost);

                    //4.从资金账号扣减余额
                    FundAccount fundAccount = fundAccountMapper.selectByUserId(userId);
                    fundAccountMapper.reduceBalance(fundAccount.getId(), billPrice.getPrice()); //扣减余额
                    //创建资金消费流水
                    FundItem fundItem = new FundItem();
                    fundItem.setFundAccountId(fundAccount.getId());
                    fundItem.setChangeAmount(billCost.getCostAmount());
                    fundItem.setChangeTime(new Date());
                    fundItem.setItemType("cost"); //消费
                    fundItemMapper.insert(fundItem);

                    break; //不需要再往后
                }
            }
        }

        return 1;
    }
}
