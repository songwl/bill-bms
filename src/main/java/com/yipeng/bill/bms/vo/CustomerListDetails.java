package com.yipeng.bill.bms.vo;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/3/22.
 */
public class CustomerListDetails {
    private int id;
    private Long customerId;
    private String userName;
    private String realName;
    private String contact;
    private String qq;
    private String phone;
    private Boolean status;
    private String createTime;
    private String lastLoginTime;
    private int loginCount;
    private int missionCount;
    private BigDecimal balance;


    public  int  getId(){return  id;}
    public int setId(int id){return  this.id=id;}
    public Long getCustomerId(){return customerId;}
    public  Long  setCustomerId(Long customerId){return this.customerId= customerId;}
    public String getUserName(){return userName;}
    public  String  setUserName(String userName){return this.userName= userName;}
    public String getRealName(){return realName;}
    public  String  setRealName(String realName){return this.realName= realName;}
    public String getContact(){return contact;}
    public  String  setContact(String contact){return this.contact= contact;}
    public String getQq(){return qq;}
    public  String  setQq(String qq){return this.qq= qq;}
    public String getPhone(){return phone;}
    public  String  setPhone(String phone){return this.phone= phone;}
    public Boolean getStatus(){return status;}
    public  Boolean  setStatus(Boolean status){return this.status= status;}
    public String getCreateTime(){return createTime;}
    public  String  setCreateTime(String createTime){return this.createTime= createTime;}
    public String getLastLoginTime(){return lastLoginTime;}
    public  String  setLastLoginTime(String lastLoginTime){return this.lastLoginTime= lastLoginTime;}
    public int getLoginCount(){return loginCount;}
    public  int  setLoginCount(int loginCount){return this.loginCount= loginCount;}
    public int getMissionCount(){return missionCount;}
    public  int  setMissionCount(int missionCount){return this.missionCount= missionCount;}
    public BigDecimal getBalance(){return balance;}
    public  BigDecimal  setBalance(BigDecimal balance){return this.balance= balance;}
}
