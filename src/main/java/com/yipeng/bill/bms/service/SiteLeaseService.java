package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.orderLease;
import com.yipeng.bill.bms.model.HallDetails;
import com.yipeng.bill.bms.vo.LoginUser;

import java.util.List;
import java.util.Map;

public interface SiteLeaseService {
    Map<String, Object> GetMission(Map<String, Object> params, LoginUser loginUser);
    List<HallDetails> GetDetails(String website, LoginUser loginUser);
}
