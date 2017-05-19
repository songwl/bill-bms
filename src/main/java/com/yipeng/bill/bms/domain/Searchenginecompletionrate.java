package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class Searchenginecompletionrate implements Serializable {
    private Long id;

    private Long userid;

    private Double allcompleteness;

    private Double baiducompleteness;

    private Double baiduwapcompleteness;

    private Double sanliulingcompleteness;

    private Double sougoucompleteness;

    private Double shenmacompleteness;

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

    public Double getAllcompleteness() {
        return allcompleteness;
    }

    public void setAllcompleteness(Double allcompleteness) {
        this.allcompleteness = allcompleteness;
    }

    public Double getBaiducompleteness() {
        return baiducompleteness;
    }

    public void setBaiducompleteness(Double baiducompleteness) {
        this.baiducompleteness = baiducompleteness;
    }

    public Double getBaiduwapcompleteness() {
        return baiduwapcompleteness;
    }

    public void setBaiduwapcompleteness(Double baiduwapcompleteness) {
        this.baiduwapcompleteness = baiduwapcompleteness;
    }

    public Double getSanliulingcompleteness() {
        return sanliulingcompleteness;
    }

    public void setSanliulingcompleteness(Double sanliulingcompleteness) {
        this.sanliulingcompleteness = sanliulingcompleteness;
    }

    public Double getSougoucompleteness() {
        return sougoucompleteness;
    }

    public void setSougoucompleteness(Double sougoucompleteness) {
        this.sougoucompleteness = sougoucompleteness;
    }

    public Double getShenmacompleteness() {
        return shenmacompleteness;
    }

    public void setShenmacompleteness(Double shenmacompleteness) {
        this.shenmacompleteness = shenmacompleteness;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}