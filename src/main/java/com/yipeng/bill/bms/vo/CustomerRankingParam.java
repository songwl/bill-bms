package com.yipeng.bill.bms.vo;

/**
 * Created by song on 17/4/11.
 * 客户排名请求接口的参数定义
 */
public class CustomerRankingParam {
      private String wAction;
      private String wParam;
      private String wSign;


     public  String  getwAction(){return  wAction;}
     public String setwAction(String  wAction){return  this.wAction=wAction;}

     public  String  getwParam(){return  wParam;}
     public String setwParam(String  wParam){return  this.wParam=wParam;}

     public  String  getwSign(){return  wSign;}
     public String setwSign(String  wSign){return  this.wSign=wSign;}

 }
