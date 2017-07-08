package com.yipeng.bill.bms.vo;

/**
 * Created by 鱼在我这里。 on 2017/7/8.
 */
public class ZhuanYuanDetails {
    private int countNoCostByWebsite;
    public int getCountNoCostByWebsite() {
        return countNoCostByWebsite;
    }
    public void setCountNoCostByWebsite(int countNoCostByWebsite) {
        this.countNoCostByWebsite = countNoCostByWebsite;
    }

    private double billStandardRate;
    public double getBillStandardRate() {
        return billStandardRate;
    }
    public void setBillStandardRate(double billStandardRate) {
        this.billStandardRate = billStandardRate;
    }

    private  int keywordsCount;
    public int getKeywordsCount() {
        return keywordsCount;
    }
    public void setKeywordsCount(int keywordsCount) {
        this.keywordsCount = keywordsCount;
    }

    private  int billCount;
    public int getBillCount() {
        return billCount;
    }
    public void setBillCount(int billCount) {
        this.billCount = billCount;
    }

    private  double cost;
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    private  double allCost;
    public double getAllCost() {
        return allCost;
    }
    public void setAllCost(double allCost) {
        this.allCost = allCost;
    }
    private  String userName;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    private int weekCount;
    public int getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(int weekCount) {
        this.weekCount = weekCount;
    }



    private int monthCount;
    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }


}
