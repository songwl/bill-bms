package com.yipeng.bill.bms.model;

/**
 * Created by Administrator on 2017/5/2.
 */
public class BillManageList {
    private  int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private  Long sqlId;
    public Long getSqlId() {
        return sqlId;
    }
    public void setSqlId(Long sqlId) {
        this.sqlId = sqlId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
    private String commissioner;

    private  String webSite;

    private  int billCount;

    private  String keywordsCompletionRate;

    private  int optimizationDays;

    private  double dayConsumption;

    public double getMonthConsumption() {
        return monthConsumption;
    }

    public void setMonthConsumption(double monthConsumption) {
        this.monthConsumption = monthConsumption;
    }

    private  double monthConsumption;

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public int getBillCount() {
        return billCount;
    }

    public void setBillCount(int billCount) {
        this.billCount = billCount;
    }

    public String getKeywordsCompletionRate() {
        return keywordsCompletionRate;
    }

    public void setKeywordsCompletionRate(String keywordsCompletionRate) {
        this.keywordsCompletionRate = keywordsCompletionRate;
    }

    public int getOptimizationDays() {
        return optimizationDays;
    }

    public void setOptimizationDays(int optimizationDays) {
        this.optimizationDays = optimizationDays;
    }

    public double getDayConsumption() {
        return dayConsumption;
    }

    public void setDayConsumption(double dayConsumption) {
        this.dayConsumption = dayConsumption;
    }

    public String getCommissioner() {
        return commissioner;
    }

    public void setCommissioner(String commissioner) {
        this.commissioner = commissioner;
    }



}
