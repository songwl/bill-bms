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
    private Integer changeRanking;

    private Integer webAppId;
    private Integer webAppId1;
    private Integer standardDays;
    private Integer dayOptimization;
    private Integer allOptimization;
    private Integer state;
    private Integer opstate;
    private Integer billType;
    private Long billAscription;

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

    public Integer getChangeRanking() {
        return changeRanking;
    }

    public void setChangeRanking(Integer changeRanking) {
        this.changeRanking = changeRanking;
    }

    public Integer getWebAppId() {
        return webAppId;
    }

    public void setWebAppId(Integer webAppId) {
        this.webAppId = webAppId;
    }

    public Integer getWebAppId1() {
        return webAppId1;
    }

    public void setWebAppId1(Integer webAppId1) {
        this.webAppId1 = webAppId1;
    }

    public Integer getStandardDays() {
        return standardDays;
    }

    public void setStandardDays(Integer standardDays) {
        this.standardDays = standardDays;
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

    public Integer getOpstate() {
        return opstate;
    }

    public void setOpstate(Integer opstate) {
        this.opstate = opstate;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }


    public Long getBillAscription() {
        return billAscription;
    }

    public void setBillAscription(Long billAscription) {
        this.billAscription = billAscription;
    }
}
