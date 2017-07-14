package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.vo.LoginUser;

import java.util.Map;

public interface StatisticsDataService {
    Map<String, Object> GetWebsiteMonitor(Map<String, Object> map, LoginUser loginUser);
    Map<String, Object> LoadFa(LoginUser loginUser);
}
