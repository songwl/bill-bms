package com.yipeng.bill.bms.core.cache.impl;


import com.yipeng.bill.bms.core.cache.CacheHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 基于Map的简单缓存实现
 * created by song on 2016/6/6.
 *
 */
public class MapCache implements CacheHelper {
	private final Map<String, Object> m_objects; //记录对象数据
	private final Map<String, Long> m_expiredObjects; //记录对象有效期，精确到毫秒
	private final long m_lExpireTime; //缓存到期时间，精确到秒
	private final ExecutorService m_executor;

	private static MapCache instance = null;

	public static MapCache getInstance() {
		if (instance == null) {
			synchronized (MapCache.class) {
				if (instance == null) {
					instance = new MapCache();
				}
			}
		}
		return instance;
	}

	private MapCache() {
		this(7 * 24 * 60 * 60); //默认缓存有效期为一周，防止垃圾缓存太多
	}

	private MapCache(final int nExpireTime) {
		m_objects = Collections.synchronizedMap(new HashMap<String, Object>());
		m_expiredObjects = Collections.synchronizedMap(new HashMap<String, Long>());
		m_lExpireTime = nExpireTime;
		m_executor = Executors.newFixedThreadPool(256);
		Executors.newScheduledThreadPool(5).scheduleWithFixedDelay(removeExpiredObjects(), m_lExpireTime / 2, m_lExpireTime, TimeUnit.SECONDS);
	}

	private final Runnable removeExpiredObjects() {
		return new Runnable() {
			public void run() {
				for (final String name : m_expiredObjects.keySet()) {
					if (System.currentTimeMillis() > m_expiredObjects.get(name)) {
						m_executor.execute(createRemoveRunnable(name));
					}
				}
			}
		};
	}

	private final Runnable createRemoveRunnable(final String name) {
		return new Runnable() {
			public void run() {
				m_objects.remove(name);
				m_expiredObjects.remove(name);
			}
		};
	}

	public long getExpireTime() {
		return m_lExpireTime;
	}

	public void put(final String name, final Object obj) {
		put(name, obj, m_lExpireTime);
	}

	public void put(final String name, final Object obj, final long expireTime) {
		m_objects.put(name, obj);
		m_expiredObjects.put(name, System.currentTimeMillis() + expireTime * 1000);
	}

	public Object get(final String name) {
		final Long expireTime = m_expiredObjects.get(name);
		if (expireTime == null)
			return null;
		if (System.currentTimeMillis() > expireTime) {
			m_executor.execute(createRemoveRunnable(name));
			return null;
		}
		return m_objects.get(name);
	}

	@SuppressWarnings("unchecked")
	public <R extends Object> R get(final String name, final Class<R> type) {
		return (R) get(name);
	}

	@Override
	public boolean has(String name) {
		return m_expiredObjects.containsKey(name);
	}

	@Override
	public void remove(String name) {
		m_executor.execute(createRemoveRunnable(name));
	}
}
