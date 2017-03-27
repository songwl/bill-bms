package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.vo.CustomerListDetails;
import com.yipeng.bill.bms.vo.LoginUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 鱼在我这里。 on 2017/3/19.
 */
public interface CustomerService {
    int savaUser(User user, int addMemberId, Long userId,String realName,String contact,String phone,String qq, BigDecimal balance);

    /**
     * 获取客户列表
     * @param params
     * @return
     */
    Map<String, Object> getCustomerList(Map<String,Object> params,LoginUser user);

    /**
     * 获取待审核客户
     * @param params
     * @return
     */
    Map<String, Object> getCustomerReviewList(Map<String,Object> params);

    /**
     * 客户审核
     * @param customerId
     * @return
     */
    int customerAudit(Long customerId);

    int Recharge(Map<String,String[]> params,LoginUser user);
    /**
     * 获取资金明细列表
     * @param params
     * @return
     */
    Map<String, Object> fundAccountList(Map<String,Object> params,LoginUser user);
}
