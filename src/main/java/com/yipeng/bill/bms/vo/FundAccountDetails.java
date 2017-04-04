package com.yipeng.bill.bms.vo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/28.
 */
public class FundAccountDetails {
    private   int Id;
    private  Long fundItemId;
    private  String userName;
    private  String itemType;
    private  BigDecimal changeAmount;
    private  String changeTime;
    private  BigDecimal balance;


    public  int  getId(){return  Id;}
    public int setId(int Id){return  this.Id=Id;}

    public Long  getFundItemId(){return  fundItemId;}
    public Long setFundItemId(Long fundItemId){return  this.fundItemId=fundItemId;}

    public String  getUserName(){return  userName;}
    public String setUserName(String userName){return  this.userName=userName;}

    public String  getitemType(){return  itemType;}
    public String setitemType(String itemType){return  this.itemType=itemType;}

    public BigDecimal  getChangeAmount(){return  changeAmount;}
    public BigDecimal setChangeAmount(BigDecimal changeAmount){return  this.changeAmount=changeAmount;}

    public String  getChangeTime(){return  changeTime;}
    public String setChangeTime(String changeTime){return  this.changeTime=changeTime;}

    public BigDecimal  getBalance(){return  balance;}
    public BigDecimal setBalance(BigDecimal balance){return  this.balance=balance;}
}
