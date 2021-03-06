package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class inBox implements Serializable {
    private Long id;

    private Long sendid;

    private String senduserid;

    private String inuserid;

    private Integer mailtype;

    private String title;

    private String content;

    private Integer dealtstate;

    private Integer affairstate;

    private Date intime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSendid() {
        return sendid;
    }

    public void setSendid(Long sendid) {
        this.sendid = sendid;
    }

    public String getSenduserid() {
        return senduserid;
    }

    public void setSenduserid(String senduserid) {
        this.senduserid = senduserid == null ? null : senduserid.trim();
    }

    public String getInuserid() {
        return inuserid;
    }

    public void setInuserid(String inuserid) {
        this.inuserid = inuserid == null ? null : inuserid.trim();
    }

    public Integer getMailtype() {
        return mailtype;
    }

    public void setMailtype(Integer mailtype) {
        this.mailtype = mailtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getDealtstate() {
        return dealtstate;
    }

    public void setDealtstate(Integer dealtstate) {
        this.dealtstate = dealtstate;
    }

    public Integer getAffairstate() {
        return affairstate;
    }

    public void setAffairstate(Integer affairstate) {
        this.affairstate = affairstate;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }
}