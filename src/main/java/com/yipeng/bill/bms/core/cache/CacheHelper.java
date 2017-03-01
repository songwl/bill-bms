package com.yipeng.bill.bms.core.cache;

/**
 * 缓存接口定义
 * created by song on 2016/6/6.
 *
 */
public interface CacheHelper {

	void put(final String name, final Object obj);

	void put(final String name, final Object obj, final long expireTime);

	Object get(final String name);

	<R extends Object> R get(final String name, final Class<R> type);

	boolean has(final String name);
	
	void remove(final String name);
}
