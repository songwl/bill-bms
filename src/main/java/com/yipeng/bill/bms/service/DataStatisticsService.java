package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.vo.LoginUser;

import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/4/26.
 */
public interface DataStatisticsService {
    /**
     * 统计调点击
     * @param params
     * @param user
     * @return
     */
    Map<String, Object> getBillOptimization(Map<String,Object> params, LoginUser user);

}
