package com.yipeng.bill.bms.vo;


import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/20.
 */
public class CustomerOptimizationResult {

    private int agid;
    private String stamp;
    private String action;
    private String sign;
    private JSONObject args;


    public  int  getAgid(){return  agid;}
    public int setAgid(int  agid){return  this.agid=agid;}

    public  String  getStamp(){return  stamp;}
    public String setStamp(String  stamp){return  this.stamp=stamp;}

    public  String  getAction(){return  action;}
    public String setAction(String  action){return  this.action=action;}

    public  String  getSign(){return  sign;}
    public String setSign(String  sign){return  this.sign=sign;}

    public  JSONObject  getArgs(){return  args;}
    public JSONObject setArgs(JSONObject args){return  this.args=args;}
}
