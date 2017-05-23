package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.BillClickStatistics;
import com.yipeng.bill.bms.domain.Role;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.UserRole;
import com.yipeng.bill.bms.service.BillClickStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Administrator on 2017/5/23.
 */
@Service
public class BillClickStatisticsServiceImpl implements BillClickStatisticsService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BillOptimizationMapper billOptimizationMapper;

    @Autowired
    private BillClickStatisticsMapper billClickStatisticsMapper;
    @Override
    public int insertBillClickStatistics() {
        Map<String, Object> params=new HashMap<>();//搜索引擎完成率查询对象
        Calendar now = Calendar.getInstance();
        params.put("year",now.get(Calendar.YEAR));
        params.put("month",now.get(Calendar.MONTH)+1);
        params.put("day",now.get(Calendar.DATE));
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

                map.put("billAscription",item.getUserId());
                int week=0;
                int month=0;
                int all=0;

                week += billOptimizationMapper.selectByBillIdOfWeek(map);
                month+= billOptimizationMapper.selectByBillIdOfMonth(map);
                all+=billOptimizationMapper.selectByBillIdOfAll(map);

                //判断今日是否存在
                params.put("userId",item.getUserId());
                List<BillClickStatistics> billClickStatisticsList=  billClickStatisticsMapper.selectByDateNow(params);
                if (!CollectionUtils.isEmpty(billClickStatisticsList))
                {
                    //插入数据库
                    BillClickStatistics billClickStatistics=new BillClickStatistics();
                    billClickStatistics.setUserid(item.getUserId());
                    billClickStatistics.setWeekClick(week);
                    billClickStatistics.setMonthClick(month);
                    billClickStatistics.setAllClick(all);
                    billClickStatistics.setCreateTime(new Date());
                    billClickStatistics.setCreateUserId(item.getUserId());
                    billClickStatisticsMapper.insert(billClickStatistics);
                }
                else
                {
                    //修改数据库
                    BillClickStatistics billClickStatistics=new BillClickStatistics();
                    billClickStatistics.setId(billClickStatisticsList.get(0).getId());
                    billClickStatistics.setWeekClick(week);
                    billClickStatistics.setMonthClick(month);
                    billClickStatistics.setAllClick(all);
                    billClickStatistics.setCreateTime(new Date());
                    billClickStatistics.setCreateUserId(item.getUserId());
                    billClickStatisticsMapper.updateByPrimaryKey(billClickStatistics);

                }

            }
        }
        return  1;
    }
}
