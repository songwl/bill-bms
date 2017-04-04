package com.yipeng.bill.bms.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/4.
 */
public class FundAccountSumMp {
    private Long id;

    private Long fundAccountId;

    private BigDecimal changeAmount;

    private BigDecimal balance;

    private Date changeTime;

    private String itemType;

    private BigDecimal fundItemSum;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(Long fundAccountId) {
        this.fundAccountId = fundAccountId;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    public BigDecimal getfundItemSum() {
        return fundItemSum;
    }

    public void setfundItemSum(BigDecimal fundItemSum) {
        this.fundItemSum = fundItemSum;
    }
}
