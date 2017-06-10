package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.inBox;
import com.yipeng.bill.bms.domain.noticepublish;
import com.yipeng.bill.bms.vo.LoginUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/10.
 */
public interface HomeService {
    Map<String, Object> homeDetails(LoginUser loginUser);
   //客户数
    Map<String, Object> userCount(LoginUser loginUser);
    //账户余额balance
    Map<String, Object> balance(LoginUser loginUser);
    //本月消费
    Map<String, Object> MonthConsumption(LoginUser loginUser);
    //当日消费
    Map<String, Object> DayConsumption(LoginUser loginUser);
    //当前任务数
    Map<String, Object> billCount(LoginUser loginUser);
    //累计任务数
    Map<String, Object> AllbillCount(LoginUser loginUser);
    //今日达标数
    Map<String, Object> standardSum(LoginUser loginUser);

    //百度完成率
    Map<String, Object> baiduCompleteness(LoginUser loginUser);
    //百度wap完成率
    Map<String, Object> baiduWapCompleteness(LoginUser loginUser);
    //360完成率
    Map<String, Object> sanliulingCompleteness(LoginUser loginUser);
    //搜狗完成率
    Map<String, Object> sougouCompleteness(LoginUser loginUser);
    //神马
    Map<String, Object> shenmaCompleteness(LoginUser loginUser);

    List<noticepublish> getInBox(LoginUser loginUser);
}
