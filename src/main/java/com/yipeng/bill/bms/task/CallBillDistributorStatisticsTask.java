package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.service.BillClickStatisticsService;
import com.yipeng.bill.bms.service.BillDistributorStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/5/23.
 */
public class CallBillDistributorStatisticsTask {
    @Autowired
    private BillDistributorStatisticsService billDistributorStatisticsService;
    public void execute(){
        billDistributorStatisticsService.insertBillDistributorStatistics();
    }
}
