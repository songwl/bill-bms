package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class offerset implements Serializable {
    private Long id;

    private Long userid;

    private String tokenid;

    private Double rate;

    private Date updatetime;

    private Date createtime;

    private Integer requestsecond;

    private Integer surplussecond;

    private Integer state;

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

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid == null ? null : tokenid.trim();
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getRequestsecond() {
        return requestsecond;
    }

    public void setRequestsecond(Integer requestsecond) {
        this.requestsecond = requestsecond;
    }

    public Integer getSurplussecond() {
        return surplussecond;
    }

    public void setSurplussecond(Integer surplussecond) {
        this.surplussecond = surplussecond;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}