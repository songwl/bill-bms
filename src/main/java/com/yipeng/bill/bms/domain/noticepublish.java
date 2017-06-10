package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class noticepublish implements Serializable {
    private Long id;

    private String sendid;

    private String inrole;

    private Integer mailtype;

    private String title;

    private String content;

    private Integer dealtstate;

    private Date sendtime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid == null ? null : sendid.trim();
    }

    public String getInrole() {
        return inrole;
    }

    public void setInrole(String inrole) {
        this.inrole = inrole == null ? null : inrole.trim();
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

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }
}