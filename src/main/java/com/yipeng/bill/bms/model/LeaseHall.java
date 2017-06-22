package com.yipeng.bill.bms.model;

import java.util.Date;

public class LeaseHall {
    private int id;
    private String website;
    private int KeywordNum;
    private int HomePageNum;
    private int orderstate;
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

    public int getKeywordNum() {
        return KeywordNum;
    }

    public void setKeywordNum(int keywordNum) {
        KeywordNum = keywordNum;
    }

    public int getHomePageNum() {
        return HomePageNum;
    }

    public void setHomePageNum(int homePageNum) {
        HomePageNum = homePageNum;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(int orderstate) {
        this.orderstate = orderstate;
    }
}
