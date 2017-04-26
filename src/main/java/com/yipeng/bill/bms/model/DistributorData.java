package com.yipeng.bill.bms.model;

/**
 * Created by Administrator on 2017/4/26.
 */
public class DistributorData {
    private Long id;
    private  String userName;
    private Double  weekCost;
    private  Double monthCost;
    private  Double allCost;
    private  Long billCount;
    private  Double billStandard;
    private  Double keywordsStandard;
    private  int monthAddBill;

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

    public  Double getWeekCost(){return  weekCost;}
    public void setWeekCost(Double weekCost) {
        this.weekCost = weekCost;
    }

    public  Double getMonthCost(){return  monthCost;}
    public void setMonthCost(Double monthCost) {
        this.monthCost = monthCost;
    }

    public  Double getAllCost(){return  allCost;}
    public void setAllCost(Double allCost) {
        this.allCost = allCost;
    }

    public  Long getBillCount(){return  billCount;}
    public void setBillCount(Long billCount) {
        this.billCount = billCount;
    }

    public  Double getBillStandard(){return  billStandard;}
    public void setBillStandard(Double billStandard) {
        this.billStandard = billStandard;
    }

    public  Double getKeywordsStandard(){return  billStandard;}
    public void setKeywordsStandard(Double keywordsStandard) {
        this.keywordsStandard = keywordsStandard;
    }

    public  int getMonthAddBill(){return  monthAddBill;}
    public void setMonthAddBill(int monthAddBill) {
        this.monthAddBill = monthAddBill;
    }
}
