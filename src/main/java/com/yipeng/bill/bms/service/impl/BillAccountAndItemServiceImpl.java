package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.FundAccount;
import com.yipeng.bill.bms.domain.FundItem;
import com.yipeng.bill.bms.domain.Logs;
import com.yipeng.bill.bms.service.BillAccountAndItemService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/5/17.
 */
@Service
public class BillAccountAndItemServiceImpl implements BillAccountAndItemService {
    @Autowired
    private BillPriceMapper billPriceMapper;
    @Autowired
    private BillCostMapper billCostMapper;
    @Autowired
    private FundItemMapper fundItemMapper;
    @Autowired
    private FundAccountMapper fundAccountMapper;
    @Autowired
    private LogsMapper logsMapper;

    private volatile  ReentrantLock lock = new ReentrantLock();
    @Override
    public int BillAccountAndItem(Map<String, Object> params) {
        Calendar now =Calendar.getInstance();
        params.put("year",now.get(Calendar.YEAR));
        params.put("month",now.get(Calendar.MONTH)+1);
        params.put("day",now.get(Calendar.DATE));
        Double cost=billCostMapper.selectByUseDayCost(params);
        if (cost!=0)
        {

                //获取当前用户的FUNDACCOUNT
                FundAccount fundAccount = fundAccountMapper.selectByUserId(Long.parseLong(params.get("userId").toString()));
                if (fundAccount == null) {
                    //创建用户资金表
                    FundAccount fundAccount1 = new FundAccount();
                    fundAccount1.setUserId(Long.parseLong(params.get("userId").toString()));
                    BigDecimal balance = new BigDecimal(0);
                    fundAccount1.setBalance(balance.subtract(new BigDecimal(cost)));
                    fundAccount1.setCreateTime(new Date());
                    fundAccount1.setCreateUserId(Long.parseLong(params.get("userId").toString()));
                    fundAccountMapper.insert(fundAccount1);
                    //创建流水明细表
                    FundItem fundItem = new FundItem();
                    fundItem.setChangeAmount(new BigDecimal(cost));
                    fundItem.setBalance(balance.subtract(new BigDecimal(cost)));
                    fundItem.setChangeTime(new Date());
                    fundItem.setFundAccountId(fundAccount1.getId());
                    fundItem.setItemType("cost");
                    fundItemMapper.insert(fundItem);
                    return 1;
                }
                else {
                    //获取今日的FUNDITEM
                    params.put("fundAccountId", fundAccount.getId());
                    FundItem fundItem = fundItemMapper.selectByDayFunItem(params);//判断今日是否存在今日消费

                    if (fundItem == null) {

                            synchronized(this) {
                            //先扣余额 
                            BigDecimal lastAccount = fundAccount.getBalance();
                            Logs logs = new Logs();
                            logs.setOpobj(params.get("userId").toString());
                            logs.setOpremake("今日第一次。" + "期初余额：" + Double.parseDouble(lastAccount.toString()) + "发生金额：" +
                                    cost.toString() + "结余金额：" + Double.parseDouble(lastAccount.subtract(new BigDecimal(cost)).toString()));
                            logs.setOptype(1);
                            logs.setUserid(new Long(1));
                            logs.setCreatetime(new Date());
                            int a = logsMapper.insert(logs);
                            fundAccount.setBalance(fundAccount.getBalance().subtract(new BigDecimal(cost)));

                            fundAccountMapper.updateByPrimaryKey(fundAccount);


                            //产生资金明细
                            FundAccount fundAccountNow = fundAccountMapper.selectByUserId(Long.parseLong(params.get("userId").toString()));//客户当前余额
                            FundItem fundItemNow = new FundItem();
                            fundItemNow.setBalance(fundAccountNow.getBalance());
                            fundItemNow.setChangeAmount(new BigDecimal(cost));
                            fundItemNow.setItemType("cost");
                            fundItemNow.setFundAccountId(fundAccount.getId());
                            fundItemNow.setChangeTime(new Date());

                            try {

                                int aa = fundItemMapper.insert(fundItemNow);
                            } catch (Exception e) {
                                int aa = fundItemMapper.updateByPrimaryKeySelective(fundItemNow);
                            }
                        }

                        return 1;

                    }
                    else {


                        //修改余额
                        BigDecimal lastAccount1 = fundAccount.getBalance();
                        fundAccount.setBalance((fundAccount.getBalance().add(fundItem.getChangeAmount()).subtract(new BigDecimal(cost))));
                        fundAccountMapper.updateByPrimaryKeySelective(fundAccount);

                        Logs logs = new Logs();
                        logs.setOpobj(params.get("userId").toString());
                        logs.setOpremake("今天第二次扣费" + "期初余额：" + Double.parseDouble(lastAccount1.toString()) + "流水历史：:" + fundItem.getChangeAmount() + "发生金额：" + cost + "结余金额：" + Double.parseDouble((lastAccount1.add(fundItem.getChangeAmount()).subtract(new BigDecimal(cost))).toString()));
                        logs.setOptype(1);
                        logs.setUserid(new Long(1));
                        logs.setCreatetime(new Date());
                        logsMapper.insert(logs);
                        //更改资金流水
                        fundItem.setChangeAmount(new BigDecimal(cost));
                        fundItemMapper.updateByPrimaryKeySelective(fundItem);

                        return 1;
                    }

                }

        }
        //判断fundItem今日是否存在
        else {
            synchronized (this) {
                //获取当前用户的FUNDACCOUNT
                FundAccount fundAccount = fundAccountMapper.selectByUserId(Long.parseLong(params.get("userId").toString()));
                //获取今日的FUNDITEM
                params.put("fundAccountId", fundAccount.getId());
                FundItem fundItem = fundItemMapper.selectByDayFunItem(params);
                if (fundItem == null) {
                    return 1;
                } else {
                    //修改客户余额
                    fundAccount.setBalance(fundAccount.getBalance().add(fundItem.getChangeAmount()));
                    fundAccountMapper.updateByPrimaryKeySelective(fundAccount);
                    fundItemMapper.deleteByPrimaryKey(fundItem.getId());
                    return 1;
                }

            }
        }
    }

}
