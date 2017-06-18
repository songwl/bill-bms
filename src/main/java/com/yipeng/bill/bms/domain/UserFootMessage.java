package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class UserFootMessage implements Serializable {
    private Long id;

    private String website;

    private String footfont1;

    private String footfont2;

    private String copyrightinfo1;

    private String copyrightinfo2;

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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getFootfont1() {
        return footfont1;
    }

    public void setFootfont1(String footfont1) {
        this.footfont1 = footfont1 == null ? null : footfont1.trim();
    }

    public String getFootfont2() {
        return footfont2;
    }

    public void setFootfont2(String footfont2) {
        this.footfont2 = footfont2 == null ? null : footfont2.trim();
    }

    public String getCopyrightinfo1() {
        return copyrightinfo1;
    }

    public void setCopyrightinfo1(String copyrightinfo1) {
        this.copyrightinfo1 = copyrightinfo1 == null ? null : copyrightinfo1.trim();
    }

    public String getCopyrightinfo2() {
        return copyrightinfo2;
    }

    public void setCopyrightinfo2(String copyrightinfo2) {
        this.copyrightinfo2 = copyrightinfo2 == null ? null : copyrightinfo2.trim();
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