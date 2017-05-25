package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.service.DataStatisticsService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/25.
 */
public class CallBillCommissionerStatisticsTask {
    @Autowired
    private DataStatisticsService dataStatisticsService;
    public void execute(){
        Map<String,Object> params=new HashMap<>();
        dataStatisticsService.commissionerData(params);
    }

}
