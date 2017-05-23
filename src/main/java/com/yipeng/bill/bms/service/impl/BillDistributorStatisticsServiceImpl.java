package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.DistributorData;
import com.yipeng.bill.bms.service.BillDistributorStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public int insertBillDistributorStatistics() {

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
                    Map<String,Object> map1=new HashMap<>();
                    map1.put("userId",item.getUserId());
                    map1.put("roleId",item.getRoleId());
                    week+=billCostMapper.selectByBillCostOfWeek(map1);
                    month+=billCostMapper.selectByBillCostOfMonthNow(map1);
                    all+=billCostMapper.selectByBillCostOfAll(map1);


                    for (Bill bill:billList
                            ) {
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
}
