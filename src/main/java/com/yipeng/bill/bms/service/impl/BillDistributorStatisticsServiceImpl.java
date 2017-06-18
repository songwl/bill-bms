package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.DistributorData;
import com.yipeng.bill.bms.service.BillDistributorStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/5/23.
 */
@Service
public class BillDistributorStatisticsServiceImpl implements BillDistributorStatisticsService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BillPriceMapper billPriceMapper;
    @Autowired
    private  BillCostMapper billCostMapper;
    @Autowired
    private  BillDistributorStatisticsMapper billDistributorStatisticsMapper;

    @Override
    public int insertBillDistributorStatistics() {
        Calendar now = Calendar.getInstance();
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

                    //循环所有订单 统计数据
                    Map<String,Object> map1=new HashMap<>();
                    map1.put("userId",item.getUserId());
                    map1.put("roleId",item.getRoleId());
                    week=billCostMapper.selectByBillCostOfWeek(map1);
                    month=billCostMapper.selectByBillCostOfMonthNow(map1);
                    all=billCostMapper.selectByBillCostOfAll(map1);
                    //达标数量
                    Map<String,Object> dabiaoParams=new HashMap<>();
                    dabiaoParams.put("userId",item.getUserId());
                    int keywordsCount=billMapper.selectBillDabiaoCount(dabiaoParams);

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
                    //判断今日的数据
                    Map<String,Object> dayMap=new HashMap<>();
                    dayMap.put("year",now.get(Calendar.YEAR));
                    dayMap.put("month",now.get(Calendar.MONTH)+1);
                    dayMap.put("day",now.get(Calendar.DATE));
                    dayMap.put("userId",item.getUserId());
                    BillDistributorStatistics billDistributorStatisticsExsits=billDistributorStatisticsMapper.selectByDayExsits(dayMap);
                    if(billDistributorStatisticsExsits==null)  //插入到数据库
                    {
                        BillDistributorStatistics billDistributorStatistics=new BillDistributorStatistics();
                        billDistributorStatistics.setUserid(item.getUserId());
                        billDistributorStatistics.setBillMonthAddCount(monthAddCount);
                        billDistributorStatistics.setWeekCost(new BigDecimal(week));
                        billDistributorStatistics.setMonthCost(new BigDecimal(month));
                        billDistributorStatistics.setAllCost(new BigDecimal(all));
                        billDistributorStatistics.setBillCount(count);
                        billDistributorStatistics.setBillApprovalRate(new BigDecimal(billStandard));
                        billDistributorStatistics.setKeywordsApprovalRate(new BigDecimal(keywordStandard));
                        billDistributorStatistics.setBillMonthAddCount(monthAddCount);
                        billDistributorStatistics.setCreateTime(new Date());
                        billDistributorStatistics.setCreateUserId(item.getUserId());
                        try {
                            billDistributorStatisticsMapper.insert(billDistributorStatistics);
                        }
                        catch (Exception e)
                        {
                            throw  e;
                        }


                    }
                    else
                    {
                        BillDistributorStatistics billDistributorStatisticsNew=new BillDistributorStatistics();
                        billDistributorStatisticsNew.setId(billDistributorStatisticsExsits.getId());
                        billDistributorStatisticsNew.setUserid(item.getUserId());
                        billDistributorStatisticsNew.setBillMonthAddCount(monthAddCount);
                        billDistributorStatisticsNew.setWeekCost(new BigDecimal(week));
                        billDistributorStatisticsNew.setMonthCost(new BigDecimal(month));
                        billDistributorStatisticsNew.setAllCost(new BigDecimal(all));
                        billDistributorStatisticsNew.setBillCount(count);
                        billDistributorStatisticsNew.setBillApprovalRate(new BigDecimal(billStandard));
                        billDistributorStatisticsNew.setKeywordsApprovalRate(new BigDecimal(keywordStandard));
                        billDistributorStatisticsNew.setBillMonthAddCount(monthAddCount);
                        billDistributorStatisticsNew.setCreateTime(new Date());
                        billDistributorStatisticsNew.setCreateUserId(item.getUserId());
                        billDistributorStatisticsMapper.updateByPrimaryKey(billDistributorStatisticsNew);
                    }




                }
            }
        }
        return  0;
    }
}
