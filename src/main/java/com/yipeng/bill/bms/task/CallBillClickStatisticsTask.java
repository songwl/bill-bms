package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.service.BillClickStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2017/5/23.
 */
public class CallBillClickStatisticsTask {

   @Autowired
   private BillClickStatisticsService billClickStatisticsService;
    public void execute(){
        billClickStatisticsService.insertBillClickStatistics();
    }

}
