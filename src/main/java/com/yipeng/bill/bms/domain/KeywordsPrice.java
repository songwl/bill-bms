package com.yipeng.bill.bms.domain;

import java.io.Serializable;
import java.util.Date;

public class KeywordsPrice implements Serializable {
    private Long id;

    private Integer taskid;

    private Integer indexbaiduall;

    private Integer indexbaiduwap;

    private Integer indexsoall;

    private Integer baiducollectioncount;

    private Integer baiduhomepagecount;

    private Integer degree;

    private Double pricebaidupc;

    private Double pricebaiduwap;

    private Double pricesopc;

    private Double pricesowap;

    private Double pricesogoupc;

    private Double pricesogouwap;

    private Double pricesm;

    private Date updatetime;

    private String keywords;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskid() {
        return taskid;
    }

    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    public Integer getIndexbaiduall() {
        return indexbaiduall;
    }

    public void setIndexbaiduall(Integer indexbaiduall) {
        this.indexbaiduall = indexbaiduall;
    }

    public Integer getIndexbaiduwap() {
        return indexbaiduwap;
    }

    public void setIndexbaiduwap(Integer indexbaiduwap) {
        this.indexbaiduwap = indexbaiduwap;
    }

    public Integer getIndexsoall() {
        return indexsoall;
    }

    public void setIndexsoall(Integer indexsoall) {
        this.indexsoall = indexsoall;
    }

    public Integer getBaiducollectioncount() {
        return baiducollectioncount;
    }

    public void setBaiducollectioncount(Integer baiducollectioncount) {
        this.baiducollectioncount = baiducollectioncount;
    }

    public Integer getBaiduhomepagecount() {
        return baiduhomepagecount;
    }

    public void setBaiduhomepagecount(Integer baiduhomepagecount) {
        this.baiduhomepagecount = baiduhomepagecount;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
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

    public Double getPricesowap() {
        return pricesowap;
    }

    public void setPricesowap(Double pricesowap) {
        this.pricesowap = pricesowap;
    }

    public Double getPricesogoupc() {
        return pricesogoupc;
    }

    public void setPricesogoupc(Double pricesogoupc) {
        this.pricesogoupc = pricesogoupc;
    }

    public Double getPricesogouwap() {
        return pricesogouwap;
    }

    public void setPricesogouwap(Double pricesogouwap) {
        this.pricesogouwap = pricesogouwap;
    }

    public Double getPricesm() {
        return pricesm;
    }

    public void setPricesm(Double pricesm) {
        this.pricesm = pricesm;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }
}