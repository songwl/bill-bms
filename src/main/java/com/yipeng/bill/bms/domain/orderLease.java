package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class orderLease implements Serializable {
    private Long id;

    private Long orderid;

    private Long allotid;

    private Long receiveid;

    private String reserveid;

    private String customerid;

    private Integer keywordstate;

    private Integer orderstate;

    private String website;

    private String keywords;

    private Date createtime;

    private Date allottime;

    private Date reservetime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getReserveid() {
        return reserveid;
    }

    public void setReserveid(String reserveid) {
        this.reserveid = reserveid == null ? null : reserveid.trim();
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid == null ? null : customerid.trim();
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getAllottime() {
        return allottime;
    }

    public void setAllottime(Date allottime) {
        this.allottime = allottime;
    }

    public Date getReservetime() {
        return reservetime;
    }

    public void setReservetime(Date reservetime) {
        this.reservetime = reservetime;
    }
}