package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.FundAccount;
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
    @Autowired
    private FundAccountMapper fundAccountMapper;
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

            //本日消费
            Double DayConsumption=DayConsumption(params);
            map.put("DayConsumption",DayConsumption);

            //当前任务数
            Long billCount=billMapper.getBillListCount(params);
            map.put("billCount",billCount);
            //累计任务数
            params.put("state2",3);
            Long AllbillCount=billMapper.getBillListCount(params);
            map.put("AllbillCount",AllbillCount);
            return map;
        }
        else if(loginUser.hasRole("DISTRIBUTOR"))
        {
            //客户数
            //Role role=roleMapper.selectByRoleCode("AGENT");
            //params.put("roleId",role.getId());
            //Long count=UserCount(params);
           // Role role1=roleMapper.selectByRoleCode("CUSTOMER");
           // params.put("roleId",role1.getId());
            //Long count1=UserCount(params);
            //Long AllCount=count+count1;
            //.put("UserCount",AllCount);

            //账户余额
            FundAccount fundAccount=fundAccountMapper.selectByUserId(loginUser.getId());
            if (fundAccount==null)
            {

                map.put("balance",0);
            }
            else
            {
                map.put("balance",fundAccount.getBalance());

            }
             //月总消费
            Double MonthConsumption=MonthConsumption(params);
            map.put("MonthConsumption",MonthConsumption);
            //本日消费
            Double DayConsumption=DayConsumption(params);
            map.put("DayConsumption",DayConsumption);
            //当前任务数
            params.put("state",2);
            Long billCount=billMapper.getBillListCount(params);
            map.put("billCount",billCount);
            //累计任务数
            params.put("state2",3);
            Long AllbillCount=billMapper.getBillListCount(params);
            map.put("AllbillCount",AllbillCount);
            return map;
        }
        else if(loginUser.hasRole("AGENT"))
        {
            //客户数
           // Role role1=roleMapper.selectByRoleCode("CUSTOMER");
           // params.put("roleId",role1.getId());
           // Long count1=UserCount(params);
           // Long AllCount=count1;
           // map.put("UserCount",AllCount);

            //账户余额
            FundAccount fundAccount=fundAccountMapper.selectByUserId(loginUser.getId());
            if (fundAccount==null)
            {

                map.put("balance",0);
            }
            else
            {
                map.put("balance",fundAccount.getBalance());

            }
            //月总消费
            Double MonthConsumption=MonthConsumption(params);
            map.put("MonthConsumption",MonthConsumption);
            //本日消费
            Double DayConsumption=DayConsumption(params);
            map.put("DayConsumption",DayConsumption);
            //当前任务数
            params.put("state",2);
            Long billCount=billMapper.getBillListCount(params);
            map.put("billCount",billCount);
            //累计任务数
            params.put("state2",3);
            Long AllbillCount=billMapper.getBillListCount(params);
            map.put("AllbillCount",AllbillCount);
            return map;
        }
        else  if (loginUser.hasRole("COMMISSIONER"))
        {
            //客户数
            params.put("ascription",loginUser.getId());
            params.put("inMemberId",loginUser.getCreateUserId());
            Long UserCount=userMapper.getUserBillAscriptionCount(params);
            map.put("UserCount",UserCount);
            //当前任务数
            params.put("state",2);
            params.put("userId",loginUser.getCreateUserId());
            params.put("billAscription",loginUser.getId());
            Long billCount=billMapper.getBillListCount(params);
            map.put("billCount",billCount);
            //累计任务数
            params.put("state2",3);
            Long AllbillCount=billMapper.getBillListCount(params);
            map.put("AllbillCount",AllbillCount);

            //月总消费
            Calendar now =Calendar.getInstance();
            params.put("year",now.get(Calendar.YEAR));
            params.put("month",now.get(Calendar.MONTH)+1);
            params.put("createId",loginUser.getCreateUserId());
            Double sum=billCostMapper.MonthConsumptionCommissioner(params);
            map.put("MonthConsumption",sum);
            //今日消费
            params.put("day",now.get(Calendar.DATE));
            Double sum1=billCostMapper.MonthConsumptionCommissioner(params);
            map.put("DayConsumption",sum1);
            return map;
        }
        else if (loginUser.hasRole("CUSTOMER"))
        {
            //账户余额
            FundAccount fundAccount=fundAccountMapper.selectByUserId(loginUser.getId());
            if (fundAccount==null)
            {

                map.put("balance",0);
            }
            else
            {
                map.put("balance",fundAccount.getBalance());

            }
            //月总消费
            Calendar now =Calendar.getInstance();
            params.put("year",now.get(Calendar.YEAR));
            params.put("month",now.get(Calendar.MONTH)+1);
            Double sum=billCostMapper.MonthConsumptionCustomer(params);
            map.put("MonthConsumption",sum);
            //今日消费
            params.put("day",now.get(Calendar.DATE));
            Double sum1=billCostMapper.MonthConsumptionCustomer(params);
            map.put("DayConsumption",sum1);
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
    //今日消费
    public  Double DayConsumption(Map<String, Object> params)
    {
        Calendar now =Calendar.getInstance();
        params.put("year",now.get(Calendar.YEAR));
        params.put("month",now.get(Calendar.MONTH)+1);
        params.put("day",now.get(Calendar.DATE));
        Double sum=billCostMapper.MonthConsumption(params);
        return sum;
    }
}
