package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BillCost implements Serializable {
    private Long id;

    private Long tBillId;

    private Long tBillPriceId;

    private BigDecimal costAmount;

    private Date costDate;

    private Integer ranking;

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

    public Long gettBillPriceId() {
        return tBillPriceId;
    }

    public void settBillPriceId(Long tBillPriceId) {
        this.tBillPriceId = tBillPriceId;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public Date getCostDate() {
        return costDate;
    }

    public void setCostDate(Date costDate) {
        this.costDate = costDate;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }


}