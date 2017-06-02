package com.yipeng.bill.bms.domain;

import java.io.Serializable;

public class KeywordsPrice implements Serializable {
    private Long id;

    private String keywords;

    private String baidu;

    private String sougou;

    private String sanliuling;

    private String sjbaidu;

    private String shenma;

    private Integer apiid;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getBaidu() {
        return baidu;
    }

    public void setBaidu(String baidu) {
        this.baidu = baidu == null ? null : baidu.trim();
    }

    public String getSougou() {
        return sougou;
    }

    public void setSougou(String sougou) {
        this.sougou = sougou == null ? null : sougou.trim();
    }

    public String getSanliuling() {
        return sanliuling;
    }

    public void setSanliuling(String sanliuling) {
        this.sanliuling = sanliuling == null ? null : sanliuling.trim();
    }

    public String getSjbaidu() {
        return sjbaidu;
    }

    public void setSjbaidu(String sjbaidu) {
        this.sjbaidu = sjbaidu == null ? null : sjbaidu.trim();
    }

    public String getShenma() {
        return shenma;
    }

    public void setShenma(String shenma) {
        this.shenma = shenma == null ? null : shenma.trim();
    }

    public Integer getApiid() {
        return apiid;
    }

    public void setApiid(Integer apiid) {
        this.apiid = apiid;
    }
}