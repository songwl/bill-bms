package com.yipeng.bill.bms.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by song on 2016/6/2.
 */
public class ResultMessage implements Serializable {

	public static final int SUCCESS_CODE = 200;
	public static final int ERROR_CODE = 500;
	private Map<String, Object> attr;
	private int code;
	private String message;
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void put(String key, Object obj) {
		attr.put(key, obj);
	}

	public Map<String, Object> getAttr() {
		return attr;
	}

	private ResultMessage() {
		attr = new HashMap<>();
		code = 0;
	}

	public static ResultMessage create() {
		ResultMessage result = new ResultMessage();
		return result;
	}

	public boolean isSuccess() {
		return code == SUCCESS_CODE;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void raise(Exception ex) {
		this.code = ERROR_CODE;
		this.message = ex.getMessage();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
