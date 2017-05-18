package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class Logs implements Serializable {
    private Long id;

    private Long userid;

    private Integer optype;

    private String opobj;

    private String opremake;

    private Date createtime;

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

    public Integer getOptype() {
        return optype;
    }

    public void setOptype(Integer optype) {
        this.optype = optype;
    }

    public String getOpobj() {
        return opobj;
    }

    public void setOpobj(String opobj) {
        this.opobj = opobj == null ? null : opobj.trim();
    }

    public String getOpremake() {
        return opremake;
    }

    public void setOpremake(String opremake) {
        this.opremake = opremake == null ? null : opremake.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}