package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class BillFeedback implements Serializable {
    private Long id;

    private Long billId;

    private String billFeedbackTitle;

    private Long createUserId;

    private Date createTime;

    private Long updateUserId;

    private Date updateTime;

    private String billFeedbackContent;

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

    public String getBillFeedbackTitle() {
        return billFeedbackTitle;
    }

    public void setBillFeedbackTitle(String billFeedbackTitle) {
        this.billFeedbackTitle = billFeedbackTitle == null ? null : billFeedbackTitle.trim();
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBillFeedbackContent() {
        return billFeedbackContent;
    }

    public void setBillFeedbackContent(String billFeedbackContent) {
        this.billFeedbackContent = billFeedbackContent == null ? null : billFeedbackContent.trim();
    }
}