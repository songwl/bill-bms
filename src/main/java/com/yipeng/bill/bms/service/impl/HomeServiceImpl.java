package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.BillCostMapper;
import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.dao.RoleMapper;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.domain.Role;
import com.yipeng.bill.bms.service.HomeService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/10.
 */
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BillCostMapper billCostMapper;
    @Autowired
    private BillMapper billMapper;

    /**
     * 首页详情
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> homeDetails(LoginUser loginUser) {
        Map<String, Object> map=new HashMap<>();
        Map<String, Object> params=new HashMap<>();
        params.put("userId",loginUser.getId());
        if(loginUser.hasRole("SUPER_ADMIN"))
        {

            //客户数
            Role role=roleMapper.selectByRoleCode("DISTRIBUTOR");
            params.put("roleId",role.getId());

            Long count=UserCount(params);
            map.put("UserCount",count);
            //本月消费
            Double MonthConsumption=MonthConsumption(params);
            map.put("MonthConsumption",MonthConsumption);
            params.put("state",2);
            Long billCount=billMapper.getBillListCount(params);
            map.put("billCount",billCount);
            return map;
        }
        else if(loginUser.hasRole("DISTRIBUTOR"))
        {
            //客户数
            Role role=roleMapper.selectByRoleCode("AGENT");
            params.put("roleId",role.getId());
            Long count=UserCount(params);
            Role role1=roleMapper.selectByRoleCode("CUSTOMER");
            params.put("roleId",role1.getId());
            Long count1=UserCount(params);
            Long AllCount=count+count1;
            map.put("UserCount",AllCount);
            Double MonthConsumption=MonthConsumption(params);



            return map;
        }
        else if(loginUser.hasRole("AGENT"))
        {
            //客户数
            Role role1=roleMapper.selectByRoleCode("CUSTOMER");
            params.put("roleId",role1.getId());
            Long count1=UserCount(params);
            Long AllCount=count1;
            map.put("UserCount",AllCount);
            Double MonthConsumption=MonthConsumption(params);
            map.put("MonthConsumption",MonthConsumption);
            return map;
        }
        else  if (loginUser.hasRole("COMMISSIONER"))
        {
            params.put("ascription",loginUser.getId());
            params.put("inMemberId",1);
            Long UserCount=userMapper.getUserBillAscriptionCount(params);
            map.put("UserCount",UserCount);

            Calendar now =Calendar.getInstance();
            params.put("year",now.get(Calendar.YEAR));
            params.put("month",now.get(Calendar.MONTH)+1);
            params.put("createId",1);
            Double sum=billCostMapper.MonthConsumptionCommissioner(params);
            map.put("MonthConsumption",sum);
            return map;
        }

         return null;
    }
    //客户数
    public Long UserCount(Map<String, Object> params)
    {
        Long Count=userMapper.getUserRoleByCreateIdCount(params);
        return  Count;
    }
    //本月总消费
    public  Double MonthConsumption(Map<String, Object> params)
    {
        Calendar now =Calendar.getInstance();
        params.put("year",now.get(Calendar.YEAR));
        params.put("month",now.get(Calendar.MONTH)+1);
        Double sum=billCostMapper.MonthConsumption(params);
         return sum;
    }
}
