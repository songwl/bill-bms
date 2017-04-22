package com.yipeng.bill.bms.model;

/**
 * Created by Administrator on 2017/4/22.
 */
public class OpYby {
    /// <summary>
    /// 关键词
    /// </summary>
    private String kw;
    /// <summary>
    /// 网址
    /// </summary>
    private String url;
    /// <summary>
    /// 搜索引擎
    /// </summary>
    private int se ;
    /// <summary>
    /// 日优化
    /// </summary>
    private int mcpd ;

    public  String getKw(){return  kw;}
    public String setKw(String kw){return  this.kw=kw;}


    public  String getUrl(){return  url;}
    public String setUrl(String url){return  this.url=url;}

    public  int getSe(){return  se;}
    public  int setSe(int se){return  this.se=se;}

    public  int getMcpd(){return  mcpd;}
    public  int setMcpd(int mcpd){return  this.mcpd=mcpd;}
}
