package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.orderLease;
import com.yipeng.bill.bms.model.HallDetails;
import com.yipeng.bill.bms.vo.LoginUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SiteLeaseService {
    Map<String, Object> AdminGetMission(Map<String, Object> params, LoginUser loginUser);

    Map<String, Object> GetMission(Map<String, Object> params, LoginUser loginUser);

    Map<String, Object> GetReceiveIdMission(Map<String, Object> params, LoginUser loginUser);

    Map<String, Object> GetAgentIdMission(Map<String, Object> params, LoginUser loginUser);

    List<Map<String, Object>> GetDetails(String website, LoginUser loginUser);

    ResultMessage DivideOrder(Map<String, Object> map);

    ResultMessage ReserveOrder(String website, int type, LoginUser loginUser);

    List<User> GetReserve(String website, LoginUser loginUser);

    int Recharge(Map<String, Object> map, BigDecimal sumMoney, LoginUser loginUser);

    Map<String, Object> CustomerGetMission(Map<String, Object> params, LoginUser loginUser);

    Map<String, Object> OrderDetails(Map<String, Object> params, LoginUser loginUser);

    int Ordering(Long[] arr, String website, LoginUser loginUser);

    List<Map<String, Object>> GetCustomer(LoginUser loginUser);

    int ConfirmCustomer(String website, LoginUser loginUser, String customer);

    void websiteLeaseOverdue();

    Map<String, Object> GetLeaseOverdue(Map<String, Object> params, LoginUser loginUser);
}
