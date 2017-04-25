package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.service.DataStatisticsService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/4/26.
 */
@Service
public class DataStatisticsServiceImpl implements DataStatisticsService {
    @Override
    public Map<String, Object> getBillOptimization(Map<String, Object> params, LoginUser user) {
        if (user.hasRole("SUPER_ADMIN"))
        {

        }

        return null;
    }
}
