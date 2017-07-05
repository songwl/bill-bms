package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class leaseOverdueTb implements Serializable {
    private Long id;

    private String website;

    private String keyword;

    private Long orderid;

    private Long allotid;

    private Long receiveid;

    private Long reserveid;

    private Integer keywordstate;

    private Integer orderstate;

    private Date reservetime;

    private Date updatetime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Long getAllotid() {
        return allotid;
    }

    public void setAllotid(Long allotid) {
        this.allotid = allotid;
    }

    public Long getReceiveid() {
        return receiveid;
    }

    public void setReceiveid(Long receiveid) {
        this.receiveid = receiveid;
    }

    public Long getReserveid() {
        return reserveid;
    }

    public void setReserveid(Long reserveid) {
        this.reserveid = reserveid;
    }

    public Integer getKeywordstate() {
        return keywordstate;
    }

    public void setKeywordstate(Integer keywordstate) {
        this.keywordstate = keywordstate;
    }

    public Integer getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(Integer orderstate) {
        this.orderstate = orderstate;
    }

    public Date getReservetime() {
        return reservetime;
    }

    public void setReservetime(Date reservetime) {
        this.reservetime = reservetime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}