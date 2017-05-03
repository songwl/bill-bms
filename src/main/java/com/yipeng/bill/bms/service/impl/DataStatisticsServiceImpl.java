package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.BillOptimizations;
import com.yipeng.bill.bms.model.DistributorData;
import com.yipeng.bill.bms.service.DataStatisticsService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by 鱼在我这里。 on 2017/4/26.
 */
@Service
public class DataStatisticsServiceImpl implements DataStatisticsService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private  UserMapper userMapper;
    @Autowired
    private BillOptimizationMapper billOptimizationMapper;
    @Autowired
    private  BillPriceMapper billPriceMapper;
    @Autowired
    private  BillCostMapper billCostMapper;
    /**
     * 调点击
     * @param params
     * @param user
     * @return
     */
    @Override
    public List<BillOptimizations> getBillOptimization(Map<String, Object> params, LoginUser user) {
        if (user.hasRole("SUPER_ADMIN"))
        {
            List<BillOptimizations> billOptimizationsList=new ArrayList<BillOptimizations>();

            //先获取所有的操作员
           Role role=roleMapper.selectByRoleCode("COMMISSIONER");
            UserRole userRole=new UserRole();
            userRole.setRoleId(role.getId());
           //操作员集合
            List<UserRole> userRoleList=userRoleMapper.selectByUserRole(userRole);
            //判断
            if(!CollectionUtils.isEmpty(userRoleList))
            {
                for (UserRole item:userRoleList
                     ) {
                    User user1=userMapper.selectByPrimaryKey(item.getUserId());
                     //获取操作员对应的所有订单
                    Map<String,Object> map=new HashMap<>();
                    map.put("userId",user1.getCreateUserId());
                    map.put("state",2);
                    map.put("billAscription",item.getUserId());
                    List<Bill> billList=billMapper.selectByInMemberId(map);
                    //判断订单是否为空
                    if (!CollectionUtils.isEmpty(billList))
                    {
                        int week=0;
                        int month=0;
                        int all=0;

                        //循环所有订单 统计数据
                        for (Bill bill:billList
                             ) {
                              Map<String,Object> opMap=new HashMap<>();
                              opMap.put("billId",bill.getId());

                              int  week1=billOptimizationMapper.selectByBillIdOfWeek(opMap);


                              week += billOptimizationMapper.selectByBillIdOfWeek(opMap);
                              month+= billOptimizationMapper.selectByBillIdOfMonth(opMap);
                              all+=billOptimizationMapper.selectByBillIdOfAll(opMap);
                        }

                        //视图对象
                        BillOptimizations billOptimizations=new BillOptimizations();
                        billOptimizations.setId(user1.getId());
                        billOptimizations.setUserName(user1.getUserName());
                        billOptimizations.setWeekOptimization(week);
                        billOptimizations.setMonthOptimization(month);
                        billOptimizations.setAllOptimization(all);
                        //加入视图集合
                        billOptimizationsList.add(billOptimizations);
                    }
                }
            }
            return  billOptimizationsList;
        }

        return null;
    }

    /**
     * 渠道商数据
     * @param params
     * @param user
     * @return
     */
    @Override
    public List<DistributorData> distributorData(Map<String, Object> params, LoginUser user) {
        if (user.hasRole("SUPER_ADMIN"))
        {
            List<DistributorData> distributorDataList=new ArrayList<DistributorData>();

            //先获取所有的渠道商
            Role role=roleMapper.selectByRoleCode("DISTRIBUTOR");
            UserRole userRole=new UserRole();
            userRole.setRoleId(role.getId());
            //渠道商集合
            List<UserRole> userRoleList=userRoleMapper.selectByUserRole(userRole);
            //判断
            if(!CollectionUtils.isEmpty(userRoleList))
            {
                for (UserRole item:userRoleList
                        ) {
                    User user1=userMapper.selectByPrimaryKey(item.getUserId());
                    //获取渠道商对应的所有订单
                    Map<String,Object> map=new HashMap<>();
                    map.put("userId",user1.getId());
                    map.put("state",2);
                    map.put("state2",3);
                    List<Bill> billList=billMapper.selectByInMemberId(map);
                    //判断订单是否为空
                    if (!CollectionUtils.isEmpty(billList))
                    {
                        Double week=0.0;
                        Double month=0.0;
                        Double all=0.0;
                         int keywordsCount=0;//关键词达标
                        //循环所有订单 统计数据
                        for (Bill bill:billList
                                ) {
                            //查询数据（单价ID）
                            BillPrice billPrice=new BillPrice();
                            billPrice.setBillId(bill.getId());
                            billPrice.setInMemberId(user.getId());
                            List<BillPrice>  billPrices=billPriceMapper.selectByBillPriceSingle(billPrice);
                            //通过订单ID和单价ID 统计数据
                            for (BillPrice billpriceitem:billPrices
                                 ) {
                                Map<String,Object> map1=new HashMap<>();
                                map1.put("billId",bill.getId());
                                map1.put("billPriceId",billpriceitem.getId());
                                week+=billCostMapper.selectByBillCostOfWeek(map1);
                                month+=billCostMapper.selectByBillCostOfMonth(map1);
                                all+=billCostMapper.selectByBillCostOfAll(map1);
                            }

                             //统计关键词达标
                            List<BillPrice> billPriceList=billPriceMapper.selectByBillId(bill.getId());
                            if (!CollectionUtils.isEmpty(billPriceList))
                            {
                                for (BillPrice priceItem:billPriceList
                                     ) {
                                    if(bill.getNewRanking()<=priceItem.getBillRankingStandard())
                                    {
                                        keywordsCount+=1;
                                        break;
                                    }
                                }
                            }

                        }
                        //订单数
                        Long count=billMapper.getBillListCount(map);
                        //统计订单达标率
                        //按网址分组的个数  对应的渠道商
                        List<Bill> billList1=billMapper.getBillGroupByWebsite(map);
                          //订单达标率
                         double billStandard=0;
                        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                         if(!CollectionUtils.isEmpty(billList1))
                         {
                             //达标订单个数
                             int billCount=0;
                             for (Bill billItem:billList1
                                  ) {
                                 map.put("website",billItem.getWebsite());
                                 map.put("searchStandard",1);
                                 List<Bill> billListStandardCount=billMapper.selectByInMemberId(map);
                                 if (!CollectionUtils.isEmpty(billListStandardCount))
                                 {
                                     billCount+=1;
                                 }
                             }
                             billStandard=((double)billCount/billList1.size())*100;
                         }
                        //统计关键词达标率
                        double keywordStandard=0;
                        keywordStandard=((double)keywordsCount/billList.size())*100;// 关键词达标个数/订单总数
                        //本月新增订单数
                         Map<String,Object> monthMap=new HashMap<>();
                        monthMap.put("userId",user1.getId());
                         monthMap.put("state",2);
                         monthMap.put("state2",3);
                         int monthAddCount=billMapper.getBillMonthAdd(monthMap);

                        //视图对象
                        DistributorData distributorData=new DistributorData();
                        distributorData.setId(user1.getId());
                        distributorData.setUserName(user1.getUserName());
                        distributorData.setWeekCost(week);
                        distributorData.setMonthCost(month);
                        distributorData.setAllCost(all);
                        distributorData.setBillCount(count);
                        distributorData.setBillStandard(df.format(billStandard));
                        distributorData.setKeywordsStandard(df.format(keywordStandard));
                        distributorData.setMonthAddBill(monthAddCount);
                        //加入到视图集合
                        distributorDataList.add(distributorData);



                    }
                }
            }
            return  distributorDataList;
        }
        return null;
    }
    /**
     * 操作员数据
     * @param params
     * @param user
     * @return
     */
    @Override
    public List<DistributorData> commissionerData(Map<String, Object> params, LoginUser user) {

        if (user.hasRole("SUPER_ADMIN"))
        {
            List<DistributorData> distributorDataList=new ArrayList<DistributorData>();

            //先获取所有的操作员
            Role role=roleMapper.selectByRoleCode("COMMISSIONER");
            UserRole userRole=new UserRole();
            userRole.setRoleId(role.getId());
            //操作员集合
            List<UserRole> userRoleList=userRoleMapper.selectByUserRole(userRole);
            //判断
            if(!CollectionUtils.isEmpty(userRoleList))
            {
                for (UserRole item:userRoleList
                        ) {
                    User user1=userMapper.selectByPrimaryKey(item.getUserId());
                    //获取操作员对应的所有订单
                    Map<String,Object> map=new HashMap<>();
                    map.put("userId",user1.getCreateUserId());
                    map.put("state",2);
                    map.put("state2",3);
                    map.put("billAscription",user1.getId());
                    List<Bill> billList=billMapper.selectByInMemberId(map);
                    //判断订单是否为空
                    if (!CollectionUtils.isEmpty(billList))
                    {
                        Double week=0.0;
                        Double month=0.0;
                        Double all=0.0;
                        int keywordsCount=0;//关键词达标
                        //循环所有订单 统计数据
                        for (Bill bill:billList
                                ) {
                            //查询数据（单价ID）
                            BillPrice billPrice=new BillPrice();
                            billPrice.setBillId(bill.getId());
                            billPrice.setInMemberId(user.getCreateUserId());
                          List<BillPrice>    billPrices=billPriceMapper.selectByBillPriceSingle(billPrice);
                            //通过订单ID和单价ID 统计数据
                            for (BillPrice billpriceitem:billPrices
                                    ) {
                                Map<String,Object> map1=new HashMap<>();
                                map1.put("billId",bill.getId());
                                map1.put("billPriceId",billpriceitem.getId());
                                week+=billCostMapper.selectByBillCostOfWeek(map1);
                                month+=billCostMapper.selectByBillCostOfMonth(map1);
                                all+=billCostMapper.selectByBillCostOfAll(map1);
                            }
                            ;
                            //统计关键词达标
                            if(bill.getState()==2)
                            {
                                List<BillPrice> billPriceList=billPriceMapper.selectByBillId(bill.getId());
                                if (!CollectionUtils.isEmpty(billPriceList))
                                {
                                    for (BillPrice priceItem:billPriceList
                                            ) {
                                        if(bill.getNewRanking()<=priceItem.getBillRankingStandard())
                                        {
                                            keywordsCount+=1;
                                            break;
                                        }
                                    }
                                }
                            }


                        }
                        //订单数
                        Long count=billMapper.getBillListCount(map);
                        //统计订单达标率
                        //按网址分组的个数  对应的操作员
                        List<Bill> billList1=billMapper.getBillGroupByWebsite(map);
                        //订单达标率
                        double billStandard=0;
                        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                        if(!CollectionUtils.isEmpty(billList1))
                        {
                            //达标订单个数
                            int billCount=0;
                            for (Bill billItem:billList1
                                    ) {

                                map.put("website",billItem.getWebsite());
                                map.put("searchStandard",1);
                                List<Bill> billListStandardCount=billMapper.selectByInMemberId(map);
                                if (!CollectionUtils.isEmpty(billListStandardCount))
                                {
                                    billCount+=1;
                                }
                            }
                            billStandard=((double)billCount/billList1.size())*100;
                        }
                        //统计关键词达标率
                        double keywordStandard=0;
                        keywordStandard=((double)keywordsCount/billList.size())*100;// 关键词达标个数/订单总数
                        //本月新增订单数
                        Map<String,Object> monthMap=new HashMap<>();
                        monthMap.put("userId",user1.getCreateUserId());
                        monthMap.put("state",2);
                        monthMap.put("state2",3);
                        monthMap.put("billAscription",user1.getId());
                        int monthAddCount=billMapper.getBillMonthAdd(monthMap);

                        //预计业绩

                        Calendar a = Calendar.getInstance();
                        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
                        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
                        int maxDate = a.get(Calendar.DATE);//本月总天数
                        Calendar b = Calendar.getInstance();
                        int nowDay=b.get(Calendar.DAY_OF_MONTH);//当前天数
                        double expect=(month/nowDay)*maxDate;//预计业绩

                        //视图对象
                        DistributorData distributorData=new DistributorData();
                        distributorData.setId(user1.getId());
                        distributorData.setUserName(user1.getUserName());
                        distributorData.setWeekCost(week);
                        distributorData.setMonthCost(month);
                        distributorData.setAllCost(all);
                        distributorData.setBillCount(count);
                        distributorData.setBillStandard(df.format(billStandard));
                        distributorData.setKeywordsStandard(df.format(keywordStandard));
                        distributorData.setMonthAddBill(monthAddCount);
                        distributorData.setExpectedPerformance(df.format(expect));
                        //加入到视图集合
                        distributorDataList.add(distributorData);



                    }
                }
            }
            return  distributorDataList;
        }
        return null;
    }
}
