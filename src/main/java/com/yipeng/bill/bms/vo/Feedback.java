package com.yipeng.bill.bms.vo;


import java.util.Date;

public class Feedback {

    private int id;

    public  int  getId(){return  id;}
    public int setId(int  agid){return  this.id=id;}
    public String getSendiUserId() {
        return sendiUserId;
    }

    public void setSendiUserId(String sendiUserId) {
        this.sendiUserId = sendiUserId;
    }

    public String getInUserId() {
        return InUserId;
    }

    public void setInUserId(String inUserId) {
        InUserId = inUserId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    private String sendiUserId;
    private String InUserId;
    private String Title;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    private String Content;

    public String getDealtState() {
        return dealtState;
    }

    public void setDealtState(String dealtState) {
        this.dealtState = dealtState;
    }

    private String dealtState;

    public String getAffairState() {
        return affairState;
    }

    public void setAffairState(String affairState) {
        this.affairState = affairState;
    }

    private String affairState;

    public Date getSendTime() {
        return SendTime;
    }

    public void setSendTime(Date sendTime) {
        SendTime = sendTime;
    }

    private Date SendTime;






}
