package com.yipeng.bill.bms.core.model;

/**
 * 用户 基类
 * 建议系统的用户先继承该类
 * created by song 2016/6/2.
 *
 */
public abstract class LoginAccount {

	public abstract Integer getLoginUserId();

	public abstract String getLoginUsername();

	public abstract String getLoginPassword();

	public abstract String getLoginUserType();
}
