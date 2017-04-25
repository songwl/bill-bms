package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class BillOptimization implements Serializable {
    private Long id;

    private Long billId;

    private Integer optimizationCount;

    private Date optimizationDate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Integer getOptimizationCount() {
        return optimizationCount;
    }

    public void setOptimizationCount(Integer optimizationCount) {
        this.optimizationCount = optimizationCount;
    }

    public Date getOptimizationDate() {
        return optimizationDate;
    }

    public void setOptimizationDate(Date optimizationDate) {
        this.optimizationDate = optimizationDate;
    }
}