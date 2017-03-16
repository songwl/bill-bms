package com.yipeng.bill.bms.model;

import com.yipeng.bill.bms.vo.BillDetails;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 */
public class BillPriceDetails {
    private  Integer rankend;
    private BigDecimal price;
    private  Integer rankend1;
    private BigDecimal price1;
    private  Integer rankend2;
    private BigDecimal price2;
    private  Integer rankend3;
    private BigDecimal price3;
    private List<BillDetails> selectContent;

    public  Integer  getRankend(){return  rankend;}
    public Integer setRankend(Integer rankend){return  this.rankend=rankend;}

    public  Integer  getRankend1(){return  rankend1;}
    public Integer setRankend1(Integer rankend1){return  this.rankend1=rankend1;}

    public  Integer  getRankend2(){return  rankend2;}
    public Integer setRankend2(Integer rankend2){return  this.rankend=rankend2;}

    public  Integer  getRankend3(){return  rankend3;}
    public Integer setRankend3(Integer rankend3){return  this.rankend3=rankend3;}
    public List<BillDetails> getSelectContent(){ return  selectContent;}
    public List<BillDetails> setSelectContent(List<BillDetails> selectContent){ return  this.selectContent=selectContent;}

}
