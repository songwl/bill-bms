package com.yipeng.bill.bms.vo;

import org.json.JSONArray;

/**
 * Created by song on 17/4/11.
 * 客户排名请求接口的结果定义
 */
public class CustomerRankingResult {

    private int code;
    private String message;
    private JSONArray value;


    public  int  getCode(){return  code;}
    public int setCode(int  code){return  this.code=code;}

    public  String  getMessage(){return  message;}
    public String setMessage(String  message){return  this.message=message;}

    public  JSONArray  getValue(){return  value;}
    public JSONArray setValue(JSONArray value){return  this.value=value;}

}
