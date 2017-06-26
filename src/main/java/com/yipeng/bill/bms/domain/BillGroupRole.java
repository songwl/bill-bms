package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class BillGroupRole implements Serializable {
    private Long id;

    private Long tBillId;

    private Long tBillGroupId;

    private Long createUserId;

    private Long updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long gettBillId() {
        return tBillId;
    }

    public void settBillId(Long tBillId) {
        this.tBillId = tBillId;
    }

    public Long gettBillGroupId() {
        return tBillGroupId;
    }

    public void settBillGroupId(Long tBillGroupId) {
        this.tBillGroupId = tBillGroupId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}