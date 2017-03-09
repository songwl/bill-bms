package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    private Long id;

    private String website;

    private String keywords;

    private Date createTime;

    private Date updateTime;

    private Long createUserId;

    private Long updateUserId;

    private Integer firstRanking;

    private Integer newRanking;

    private Integer webAppId;

    private Integer dayOptimization;

    private Integer allOptimization;

    private Integer state;

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getFirstRanking() {
        return firstRanking;
    }

    public void setFirstRanking(Integer firstRanking) {
        this.firstRanking = firstRanking;
    }

    public Integer getNewRanking() {
        return newRanking;
    }

    public void setNewRanking(Integer newRanking) {
        this.newRanking = newRanking;
    }

    public Integer getWebAppId() {
        return webAppId;
    }

    public void setWebAppId(Integer webAppId) {
        this.webAppId = webAppId;
    }

    public Integer getDayOptimization() {
        return dayOptimization;
    }

    public void setDayOptimization(Integer dayOptimization) {
        this.dayOptimization = dayOptimization;
    }

    public Integer getAllOptimization() {
        return allOptimization;
    }

    public void setAllOptimization(Integer allOptimization) {
        this.allOptimization = allOptimization;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}