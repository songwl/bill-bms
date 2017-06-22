package com.yipeng.bill.bms.model;

import java.util.Date;

public class HallDetails {
    private int id;
    private String website;
    private String keywords;
    private Integer firstRanking;
    private Integer newRanking;
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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
}
