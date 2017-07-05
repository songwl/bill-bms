package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.service.SiteLeaseService;
import org.springframework.beans.factory.annotation.Autowired;

public class LeaseOverdueTask {
    @Autowired
    private SiteLeaseService siteLeaseService;

    public void execute() {
        siteLeaseService.websiteLeaseOverdue();
    }
}
