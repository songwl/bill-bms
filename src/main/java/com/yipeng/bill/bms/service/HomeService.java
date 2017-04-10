package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.vo.LoginUser;

import java.util.Map;

/**
 * Created by Administrator on 2017/4/10.
 */
public interface HomeService {
    Map<String, Object> homeDetails(LoginUser loginUser);
}
