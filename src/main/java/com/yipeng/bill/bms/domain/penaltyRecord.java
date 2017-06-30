package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class penaltyRecord implements Serializable {
    private Long id;

    private Long fundaccountid;

    private Double initialmoney;

    private Double happenmoney;

    private Double balancemoney;

    private Integer itemtype;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFundaccountid() {
        return fundaccountid;
    }

    public void setFundaccountid(Long fundaccountid) {
        this.fundaccountid = fundaccountid;
    }

    public Double getInitialmoney() {
        return initialmoney;
    }

    public void setInitialmoney(Double initialmoney) {
        this.initialmoney = initialmoney;
    }

    public Double getHappenmoney() {
        return happenmoney;
    }

    public void setHappenmoney(Double happenmoney) {
        this.happenmoney = happenmoney;
    }

    public Double getBalancemoney() {
        return balancemoney;
    }

    public void setBalancemoney(Double balancemoney) {
        this.balancemoney = balancemoney;
    }

    public Integer getItemtype() {
        return itemtype;
    }

    public void setItemtype(Integer itemtype) {
        this.itemtype = itemtype;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}