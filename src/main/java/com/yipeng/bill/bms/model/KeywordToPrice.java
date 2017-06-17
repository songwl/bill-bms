package com.yipeng.bill.bms.model;

import java.util.Date;

public class KeywordToPrice {

    private Integer taskid;


    private Double pricebaidupc;

    private Double pricebaiduwap;

    private Double pricesopc;

    private Double pricesogoupc;

    private Double pricesm;

    private String keywords;

    private static final long serialVersionUID = 1L;

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public Double getPricebaidupc() {
        return pricebaidupc;
    }

    public void setPricebaidupc(Double pricebaidupc) {
        this.pricebaidupc = pricebaidupc;
    }

    public Double getPricebaiduwap() {
        return pricebaiduwap;
    }

    public void setPricebaiduwap(Double pricebaiduwap) {
        this.pricebaiduwap = pricebaiduwap;
    }

    public Double getPricesopc() {
        return pricesopc;
    }

    public void setPricesopc(Double pricesopc) {
        this.pricesopc = pricesopc;
    }

    public Double getPricesogoupc() {
        return pricesogoupc;
    }

    public void setPricesogoupc(Double pricesogoupc) {
        this.pricesogoupc = pricesogoupc;
    }

    public Double getPricesm() {
        return pricesm;
    }

    public void setPricesm(Double pricesm) {
        this.pricesm = pricesm;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }
}
