package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class BillPrice implements Serializable {
    private Long id;

    private Long billId;

    private Long price;

    private Long inMemberId;

    private Long outMemberId;

    private Date createTime;

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getInMemberId() {
        return inMemberId;
    }

    public void setInMemberId(Long inMemberId) {
        this.inMemberId = inMemberId;
    }

    public Long getOutMemberId() {
        return outMemberId;
    }

    public void setOutMemberId(Long outMemberId) {
        this.outMemberId = outMemberId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}