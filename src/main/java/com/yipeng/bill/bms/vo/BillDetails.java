package com.yipeng.bill.bms.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/11.
 */
public class BillDetails {
    private Long id;
    private String userName;
    private String website;
    private String searchName;
    private String createTime;
    private String keywords;
    private Integer firstRanking;
    private Integer newRanking;
    private BigDecimal priceOne;
    private BigDecimal priceTwo;
    private Integer webAppId;
    private Integer dayOptimization;
    private Integer allOptimization;
    private BigDecimal   dayConsumption;
    private Integer   standardDays;
    private Integer state;

    public  Long  getId(){return  id;}
    public Long setId(Long id){return  this.id=id;}

    public  String  getUserName(){return  userName;}
    public String setUserName(String  userName){return  this.userName=userName;}

    public  String  getWebsite(){return  website;}
    public String setWebsite(String  website){return  this.website=website;}

    public  String  getSearchName(){return  searchName;}
    public String setSearchName(String  searchName){return  this.searchName=searchName;}
    public  String  getCreateTime(){return  createTime;}
    public String setCreateTime(String  createTime){return  this.createTime=createTime;}
    public  String  getKeywords(){return  keywords;}
    public String setKeywords(String  keywords){return  this.keywords=keywords;}
    public  Integer  getFirstRanking(){return  firstRanking;}
    public  Integer  setFirstRanking(Integer  firstRanking){return  this.firstRanking=firstRanking;}
    public  Integer  getNewRanking(){return  newRanking;}
    public  Integer  setNewRanking(Integer  newRanking){return  this.newRanking=newRanking;}
    public  BigDecimal  getPriceOne(){return  priceOne;}
    public  BigDecimal  setPriceOne(BigDecimal  priceOne){return  this.priceOne=priceOne;}
    public  BigDecimal  getPriceTwo(){return  priceTwo;}
    public  BigDecimal  setPriceTwo(BigDecimal  priceTwo){return  this.priceTwo=priceTwo;}
    public  Integer  getWebAppId(){return  webAppId;}
    public  Integer  setWebAppId(Integer  webAppId){return  this.webAppId=webAppId;}
    public  Integer  getDayOptimization(){return  dayOptimization;}
    public  Integer  setDayOptimization(Integer  dayOptimization){return  this.dayOptimization=dayOptimization;}
    public  Integer  getAllOptimization(){return  allOptimization;}
    public  Integer  setAllOptimization(Integer  allOptimization){return  this.allOptimization=allOptimization;}
    public  BigDecimal  getDayConsumption (){return  dayConsumption;}
    public  BigDecimal  setDayConsumption(BigDecimal  dayConsumption){return  this.dayConsumption=dayConsumption;}
    public  Integer  getStandardDays (){return  standardDays;}
    public  Integer  setStandardDays(Integer  standardDays){return  this.standardDays=standardDays;}
    public  Integer  getState(){return  state;}
    public  Integer  setState(Integer  state){return  this.state=state;}
}
