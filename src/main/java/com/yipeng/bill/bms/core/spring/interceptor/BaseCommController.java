package com.yipeng.bill.bms.core.spring.interceptor;

import com.yipeng.bill.bms.core.utils.ServletUtil;

/**
 * 提供给子类使用session
 * Created by lancey on 15/8/3.
 */
public abstract class BaseCommController {

	protected Object getSession(String key) {
		return ServletUtil.getSession(HttpLocalThread.getRequest(), HttpLocalThread.getResponse(), key);
	}

	protected void putSession(String key, Object obj) {
		ServletUtil.putSession(HttpLocalThread.getRequest(), HttpLocalThread.getResponse(), key, obj);
	}

	protected void removeSession(String key) {
		ServletUtil.removeSession(HttpLocalThread.getRequest(), HttpLocalThread.getResponse(), key);
	}

	/**
	 * 读取cookie值
	 * @param key
	 */
	protected String getCookieValue(final String key) {
		return ServletUtil.getCookieValue(HttpLocalThread.getRequest(), key);
	}

	protected void putCookie(final String key, String value) {
		ServletUtil.addCookie(HttpLocalThread.getResponse(), key, value);
	}

	protected void putCookie(final String key, String value, int timeout) {
		ServletUtil.addCookie(HttpLocalThread.getResponse(), key, value, timeout);
	}
}
