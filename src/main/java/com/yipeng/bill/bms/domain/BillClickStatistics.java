package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class BillClickStatistics implements Serializable {
    private Long id;

    private Integer weekClick;

    private Integer monthClick;

    private Integer allClick;

    private Long userid;

    private Date createTime;

    private Long createUserId;

    private Date updateTime;

    private Long updateUserId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeekClick() {
        return weekClick;
    }

    public void setWeekClick(Integer weekClick) {
        this.weekClick = weekClick;
    }

    public Integer getMonthClick() {
        return monthClick;
    }

    public void setMonthClick(Integer monthClick) {
        this.monthClick = monthClick;
    }

    public Integer getAllClick() {
        return allClick;
    }

    public void setAllClick(Integer allClick) {
        this.allClick = allClick;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }
}