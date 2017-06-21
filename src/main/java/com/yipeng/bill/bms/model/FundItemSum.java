package com.yipeng.bill.bms.model;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/4/14.
 */
public class FundItemSum {
    private Long id;

    private Long fundAccountId;

    private String  userName;

    private BigDecimal changeAmount;

    private BigDecimal balance;

    private String changeTime;

    private String itemType;

    private BigDecimal dayAccountSum;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public BigDecimal getdayAccountSum() {
        return dayAccountSum;
    }

    public void setdayAccountSum(BigDecimal DayAccountSum) {
        this.dayAccountSum = DayAccountSum;
    }
}
