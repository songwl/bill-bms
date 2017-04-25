package com.yipeng.bill.bms.model;
import java.math.BigDecimal;

/**
 * Created by 鱼在我这里。 on 2017/4/26.
 */
public class BillOptimization {

    private  Integer id;
    private String webstie;
    private  String keyword;
    private  String userName;
    private int  dayOptimization;
    private  int monthOptimization;
    private  int allOptimization;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWebstie() {
        return webstie;
    }

    public void setWebstie(String webstie) {
        this.webstie = webstie;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public  int getDayOptimization(){return  dayOptimization;}
    public void setDayOptimization(int dayOptimization) {
        this.dayOptimization = dayOptimization;
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
