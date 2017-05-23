package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class BillDistributorStatistics implements Serializable {
    private Long id;

    private Long userid;

    private Long weekCost;

    private Long monthCost;

    private Long allCost;

    private Long billCount;

    private Long billApprovalRate;

    private Long keywordsApprovalRate;

    private Integer billMonthAddCount;

    private Date createTime;

    private Long createUserId;

    private Date updateTime;

    private Long updateUserId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getWeekCost() {
        return weekCost;
    }

    public void setWeekCost(Long weekCost) {
        this.weekCost = weekCost;
    }

    public Long getMonthCost() {
        return monthCost;
    }

    public void setMonthCost(Long monthCost) {
        this.monthCost = monthCost;
    }

    public Long getAllCost() {
        return allCost;
    }

    public void setAllCost(Long allCost) {
        this.allCost = allCost;
    }

    public Long getBillCount() {
        return billCount;
    }

    public void setBillCount(Long billCount) {
        this.billCount = billCount;
    }

    public Long getBillApprovalRate() {
        return billApprovalRate;
    }

    public void setBillApprovalRate(Long billApprovalRate) {
        this.billApprovalRate = billApprovalRate;
    }

    public Long getKeywordsApprovalRate() {
        return keywordsApprovalRate;
    }

    public void setKeywordsApprovalRate(Long keywordsApprovalRate) {
        this.keywordsApprovalRate = keywordsApprovalRate;
    }

    public Integer getBillMonthAddCount() {
        return billMonthAddCount;
    }

    public void setBillMonthAddCount(Integer billMonthAddCount) {
        this.billMonthAddCount = billMonthAddCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }
}