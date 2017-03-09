package com.yipeng.bill.bms.domain;

import java.io.Serializable;

public class BillSearchSupport implements Serializable {
    private Long id;

    private Long billId;

    private String searchSupport;

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

    public String getSearchSupport() {
        return searchSupport;
    }

    public void setSearchSupport(String searchSupport) {
        this.searchSupport = searchSupport == null ? null : searchSupport.trim();
    }
}