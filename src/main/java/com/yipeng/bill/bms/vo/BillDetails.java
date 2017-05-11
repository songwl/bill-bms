package com.yipeng.bill.bms.vo;

import com.yipeng.bill.bms.domain.BillPrice;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 */
public class BillDetails {
    private int displayId;
    private Long id;
    private String userName;
    private String userNameByKehu;
    private String website;
    private String searchName;
    private String createTime;
    private String keywords;
    private Integer firstRanking;
    private Integer newRanking;
    private Integer changeRanking;
    private Long createUserId;
    private Long updateUserId;
    private Integer webAppId;
    private Integer dayOptimization;
    private Integer allOptimization;
    private BigDecimal   dayConsumption;
    private Double   monthConsumption;
    private Integer   standardDays;
    private Integer state;
    private Integer opstate;
    private List<BillPrice> billPriceList;

    public  int  getdisplayId(){return  displayId;}
    public int setdisplayId(int displayId){return  this.displayId=displayId;}

    public  Long  getId(){return  id;}
    public Long setId(Long id){return  this.id=id;}

    public  String  getUserName(){return  userName;}
    public String setUserName(String  userName){return  this.userName=userName;}

    public  String  getUserNameByKehu(){return  userNameByKehu;}
    public String setUserNameByKehu(String  userNameByKehu){return  this.userNameByKehu=userNameByKehu;}

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

    public  Integer  getChangeRanking(){return  changeRanking;}
    public  Integer  setChangeRanking(Integer  changeRanking){return  this.changeRanking=changeRanking;}

    public  Integer  getWebAppId(){return  webAppId;}
    public  Integer  setWebAppId(Integer  webAppId){return  this.webAppId=webAppId;}
    public  Integer  getDayOptimization(){return  dayOptimization;}
    public  Integer  setDayOptimization(Integer  dayOptimization){return  this.dayOptimization=dayOptimization;}
    public  Integer  getAllOptimization(){return  allOptimization;}
    public  Integer  setAllOptimization(Integer  allOptimization){return  this.allOptimization=allOptimization;}
    public  BigDecimal  getDayConsumption (){return  dayConsumption;}
    public  BigDecimal  setDayConsumption(BigDecimal  dayConsumption){return  this.dayConsumption=dayConsumption;}

    public  Double  getMonthConsumption (){return  monthConsumption;}
    public  Double  setMonthConsumption(Double  monthConsumption){return  this.monthConsumption=monthConsumption;}
    public  Integer  getStandardDays (){return  standardDays;}
    public  Integer  setStandardDays(Integer  standardDays){return  this.standardDays=standardDays;}
    public  Integer  getState(){return  state;}
    public  Integer  setState(Integer  state){return  this.state=state;}
    public  Integer  getOpstate(){return  opstate;}
    public  Integer  setOpstate(Integer  opstate){return  this.opstate=opstate;}
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

    public List<BillPrice> getBillPriceList() {
        return billPriceList;
    }

    public void setBillPriceList(List<BillPrice> billPriceList)  {
        this.billPriceList = billPriceList;
    }
}
