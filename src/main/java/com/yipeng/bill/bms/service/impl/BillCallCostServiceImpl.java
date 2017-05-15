package com.yipeng.bill.bms.service.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.BillCallCostService;
import org.apache.logging.log4j.spi.LoggerContextKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sun.misc.Lock;

import java.math.BigDecimal;
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
    private BillMapper billMapper;

    @Autowired
    private FundAccountMapper fundAccountMapper;
    @Autowired
    private FundItemMapper fundItemMapper;

    @Override
    public int updateCallCost(Bill bill) {

        //2.根据计算单Bill查询单价BillPrice
        List<BillPrice> prices = billPriceMapper.selectByBillId(bill.getId());
        //判断是否扣费
        Boolean bool=false;
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
        //今日消费总额
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
            Map<String, Object> params=new HashMap<>();
           //判断当前订单今天是否产生了消费记录
            //组包
            Calendar now =Calendar.getInstance();
            params.put("year",now.get(Calendar.YEAR));
            params.put("month",now.get(Calendar.MONTH)+1);
            params.put("day",now.get(Calendar.DATE));
            params.put("billId",bill.getId());
            params.put("userIdDayCost",userId);

            //查询
            List<BillCost> billCostList=billCostMapper.selectByDayCost(params);
            //进行判断
            if(!CollectionUtils.isEmpty(billCostList))//今日已消费
            {
                Boolean boolDaBiao=false;
                BillPrice billPriceNow=new BillPrice();//当前单价对象
              //判断这一次的排名是否达标（上升或者下降）
                for (BillPrice billPrice : billPriceList) {
                    if (billPrice.getBillRankingStandard().intValue()>=newRanking) { //最新排名在次设置的排名,即达到排名优化标准
                        boolDaBiao=true;
                        billPriceNow=billPrice;
                        break;
                    }
                    else
                    {
                        boolDaBiao=false;
                    }
                }
                if (boolDaBiao)//当前达标
                {
                      params.put("priceId",billPriceNow.getId());
                      BillCost billCost=billCostMapper.selectByDayCostPriceId(params);
                      //判断当前扣费表是否存在当前消费
                    if (billCost!=null)//存在(说明上午的达标标准和下午的标准在一个区间)
                    {
                         //什么也不做

                    }
                    else//不存在
                    {
                        //查询上一次的扣费是多少
                        params.put("outMemberId",billPriceNow.getOutMemberId());
                        BillCost billCostLast=billCostMapper.selectByCostByOutId(params);
                        // 修改客户约余额
                        FundAccount fundAccount=fundAccountMapper.selectByUserId(userId);
                        BigDecimal result=fundAccount.getBalance().add(billCostLast.getCostAmount());//余额加上上次扣费
                        result=result.subtract(billPriceNow.getPrice());//再减去本次扣费
                        fundAccount.setBalance(result);
                        fundAccountMapper.updateByPrimaryKeySelective(fundAccount);//更新数据库
                        //修改资金流水
                         params.put("priceId",billCostLast.gettBillPriceId());
                         FundItem fundItem =fundItemMapper.selectByItemPriceId(params);
                         fundItem.setPriceId(billPriceNow.getId());
                         fundItem.setChangeAmount(billPriceNow.getPrice());
                         fundItem.setBalance(fundAccount.getBalance());
                         fundItem.setChangeTime(new Date());
                         fundItemMapper.updateByPrimaryKeySelective(fundItem);
                        //修改扣费记录
                        billCostLast.setCostAmount(billPriceNow.getPrice());
                        billCostLast.setRanking(newRanking);
                        billCostLast.settBillPriceId(billPriceNow.getId());
                        billCostLast.setCostDate(new Date());
                        billCostMapper.updateByPrimaryKeySelective(billCostLast);

                    }

                }
                else//未达标（将消费记录消除）
                {
                    //先查出今日对应的消费记录
                    params.put("outMemberId",userId);
                    BillCost billCostLast=billCostMapper.selectByCostByOutId(params);
                    //查出资金流水记录
                    params.put("priceId",billCostLast.gettBillPriceId());
                    FundItem fundItem =fundItemMapper.selectByItemPriceId(params);
                     //查出当前用户的余额
                    FundAccount fundAccount = fundAccountMapper.selectByUserId(userId);
                    fundAccount.setBalance(fundAccount.getBalance().add(billCostLast.getCostAmount()));//客户追加扣费记录
                     fundAccountMapper.updateByPrimaryKeySelective(fundAccount);

                    billCostMapper.deleteByPrimaryKey(billCostLast.getId());//删除扣费记录
                    fundItemMapper.deleteByPrimaryKey(fundItem.getId());//删除流水记录

                    bill.setStandardDays(bill.getStandardDays()-1);//达标数减一天
                    billMapper.updateByPrimaryKeySelective(bill);


                }

            }
            else//今日未消费
            {
                //判断排名
                for (BillPrice billPrice : billPriceList) {

                    if (billPrice.getBillRankingStandard().intValue()>=newRanking){ //最新排名在次设置的排名,即达到排名优化标准

                        //判断今日消费记录是否已经存在
                        Map<String,Object> costParams=new HashMap<>();
                        costParams.put("billId",bill.getId());
                        costParams.put("priceId",billPrice.getId());
                        costParams.put("year",now.get(Calendar.YEAR));
                        costParams.put("month",now.get(Calendar.MONTH)+1);
                        costParams.put("day",now.get(Calendar.DATE));
                        BillCost billCostExsit=billCostMapper.selectByDayCostExisit(costParams);

                        if(billCostExsit==null)
                        {

                            BillCost billCost = new BillCost();
                            billCost.settBillId(bill.getId());
                            billCost.settBillPriceId(billPrice.getId());
                            billCost.setCostAmount(billPrice.getPrice()); //消费金额
                            billCost.setCostDate(new Date()); //消费日期
                            billCost.setRanking(newRanking);
                           int ab= billCostMapper.insert(billCost);
                            System.out.print("当前订单："+bill.getWebsite()+",插入了几条数据："+ab+"，时间："+new Date());
                            //4.从资金账号扣减余额
                            FundAccount fundAccount = fundAccountMapper.selectByUserId(userId);
                            if(fundAccount==null)
                            {
                                FundAccount fundAccount1=new FundAccount();
                                fundAccount1.setUserId(userId);
                                fundAccount1.setCreateUserId(userId);
                                fundAccount1.setCreateTime(new Date());
                                fundAccount1.setBalance(new BigDecimal(0));
                                int a= fundAccountMapper.insert(fundAccount1);

                                fundAccountMapper.reduceBalance(fundAccount1.getId(), billPrice.getPrice()); //扣减余额
                                //创建资金消费流水
                                FundItem fundItem = new FundItem();
                                fundItem.setFundAccountId(fundAccount1.getId());
                                fundItem.setChangeAmount(billCost.getCostAmount());


                                FundAccount fundAccount2 = fundAccountMapper.selectByPrimaryKey(fundAccount1.getId());
                                fundItem.setBalance(fundAccount2.getBalance());
                                fundItem.setPriceId(billPrice.getId());
                                fundItem.setChangeTime(new Date());
                                fundItem.setItemType("cost"); //消费
                                fundItemMapper.insert(fundItem);
                                //新增达标天数（已经扣费）
                                bool=true;
                                // bill.setStandardDays(bill.getStandardDays()+1);
                                //billMapper.updateByPrimaryKeySelective(bill);


                            }
                            else
                            {
                                fundAccountMapper.reduceBalance(fundAccount.getId(), billPrice.getPrice()); //扣减余额
                                //创建资金消费流水
                                FundItem fundItem = new FundItem();
                                fundItem.setFundAccountId(fundAccount.getId());
                                fundItem.setChangeAmount(billCost.getCostAmount());
                                FundAccount fundAccount2 = fundAccountMapper.selectByPrimaryKey(fundAccount.getId());
                                fundItem.setBalance(fundAccount2.getBalance());
                                fundItem.setPriceId(billPrice.getId());
                                fundItem.setChangeTime(new Date());
                                fundItem.setItemType("cost"); //消费
                                fundItemMapper.insert(fundItem);
                                //新增达标天数
                                bool=true;
                                //bill.setStandardDays(bill.getStandardDays()+1);
                                //billMapper.updateByPrimaryKeySelective(bill);
                                //不需要再往后
                            }
                            break;
                        }
                        else
                        {
                            break;
                        }
                    }
                    else {
                        bool=false;
                    }

                }


            }


        }
        //判断是否达标
        if(bool)
        {
            //达标天数加一天
            bill.setStandardDays(bill.getStandardDays()+1);
            bill.setUpdateTime(new Date());




                   /* if(bill.getChangeRanking()!=null)
                    {
                        bill.setChangeRanking(bill.getChangeRanking()-bill.getNewRanking());
                    }
                    else
                    {
                        bill.setChangeRanking(bill.getNewRanking());
                    }*/

            billMapper.updateByPrimaryKeySelective(bill);
        }
        return 1;
    }

}
