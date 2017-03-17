package com.yipeng.bill.bms.vo;

/**
 * Created by song on 17/3/12.
 */
public enum Roles {

    DISTRIBUTOR("渠道商"),
    AGENT("代理商"),
    CUSTOMER("客户"),
    COMMISSIONER("专员"),
    SUPER_ADMIN("超级管理员"),
    ADMIN("管理员");

    private String cnName;

    Roles(String cnName) {
        this.cnName = cnName;
    }

    public String getValue() {
        return this.name();
    }

    public String getLabel() {
        return this.cnName;
    }

    public static String getCnName(String t) {
        for (Roles s : values()) {
            if (s.name().equalsIgnoreCase(t)) {
                return s.getLabel();
            }
        }
        return t;
    }

}
