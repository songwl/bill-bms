package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.vo.LoginUser;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/2.
 */
public interface BillManageService {
    Map<String, Object> manageListByAdminTable(Map<String, Object> params, LoginUser user);
    Map<String, Object> manageListByOtherTable(Map<String, Object> params, LoginUser user);
    Map<String, Object> getNewRankingTable(Map<String, Object> params, LoginUser user);
    Map<String,Object> performanceStatisticsTable(LoginUser loginUser, String searchTime);
    Map<String,Object> getDeclineBillTable(LoginUser loginUser);
}
