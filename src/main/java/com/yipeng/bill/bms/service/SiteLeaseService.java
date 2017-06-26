package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.orderLease;
import com.yipeng.bill.bms.model.HallDetails;
import com.yipeng.bill.bms.vo.LoginUser;

import java.util.List;
import java.util.Map;

public interface SiteLeaseService {
    Map<String, Object> AdminGetMission(Map<String, Object> params, LoginUser loginUser);

    Map<String, Object> GetMission(Map<String, Object> params, LoginUser loginUser);

    Map<String, Object> GetReceiveIdMission(Map<String, Object> params, LoginUser loginUser);

    Map<String, Object> GetAgentIdMission(Map<String, Object> params, LoginUser loginUser);

    List<Map<String, Object>> GetDetails(String website, LoginUser loginUser);

    ResultMessage DivideOrder(Map<String, Object> map);

    ResultMessage ReserveOrder(String website,int type,LoginUser loginUser);
}
