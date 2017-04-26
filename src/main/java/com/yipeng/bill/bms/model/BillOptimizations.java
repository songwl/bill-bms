package com.yipeng.bill.bms.model;

import java.math.BigInteger;

/**
 * Created by Administrator on 2017/4/26.
 */
public class BillOptimizations {
    private Long id;
    private  String userName;
    private int  weekOptimization;
    private  int monthOptimization;
    private  int allOptimization;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public  int getWeekOptimization(){return  weekOptimization;}
    public void setWeekOptimization(int weekOptimization) {
        this.weekOptimization = weekOptimization;
    }

    public  int getMonthOptimization(){return  monthOptimization;}
    public void setMonthOptimization(int monthOptimization) {
        this.monthOptimization = monthOptimization;
    }

    public  int getAllOptimization(){return  allOptimization;}
    public void setAllOptimization(int allOptimization) {
        this.allOptimization = allOptimization;
    }
}
