package com.yipeng.bill.bms.core.utils;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServletUtil {

	private static String DEFAULT_LOCATION = "";
	public static final String SESSION_ID = "venuessessionid";
	public static final String SESSION_USER = "VENUES_SESSION_USER";
	private static Log log = LogFactory.getLog(ServletUtil.class);
	public static final Integer LOGIN_SESSION_TIME = 1800;

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					return cookies[i];
				}
			}
		}
		return null;
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie cookie = ServletUtil.getCookie(request, cookieName);
		return cookie == null ? null : cookie.getValue();
	}

	/**
	 * 添加指定有效期的COOKIE
	 * @param res
	 * @param key
	 * @param value
	 * @param validDays
	 */
	public static void addCookie(HttpServletResponse res, String key, String value, int validDays) {
		addCookie(res, key, value, false);
	}

	public static void addCookie(HttpServletResponse res, String key, String value, int validDays, boolean use_maidou_host) {
		Cookie cookie = new Cookie(key, value);
		if (use_maidou_host) {
			cookie.setDomain(".maidou.com");
		}
		cookie.setMaxAge(validDays * 3600 * 24);
		cookie.setPath("/");
		res.addCookie(cookie);
	}

	/**
	 * 添加一个生命周期是当前浏览器的COOKIE
	 * @param res
	 * @param key
	 * @param value
	 */
	public static void addCookie(HttpServletResponse res, String key, String value) {
		addCookie(res, key, value, false);
	}

	public static void addCookie(HttpServletResponse res, String key, String value, boolean use_maidou_host) {
		Cookie cookie = new Cookie(key, value);
		if (use_maidou_host) {
			cookie.setDomain(".maidou.com");
		}
		cookie.setPath("/");
		res.addCookie(cookie);
	}

	public static String getRequestHost(HttpServletRequest request) {
		String host = request.getHeader("Host");
		if (host == null) {
			return ServletUtil.DEFAULT_LOCATION;
		}
		if (host.indexOf(".") != -1) {
			return host.substring(0, host.indexOf("."));
		} else {
			return host;
		}
	}

	public static String getRequestUrlWithDomain(HttpServletRequest request, String uri, String domain) {
		int port = request.getLocalPort();
		if (port == 80) {
			return "http://" + domain + "/" + uri;
		} else {
			return "http://" + domain + ":" + port + "/" + uri;
		}
	}

	public static String getRequestUrlWithDomain(HttpServletRequest request, String domain) {
		String url = request.getRequestURL().toString();
		int port = request.getLocalPort();
		if (port == 80) {
			return "http://" + domain + url.substring(url.indexOf("/", 8));
		} else {
			return "http://" + domain + ":" + port + "/" + url.substring(url.indexOf("/", 8));
		}
	}

	public static String getRefererUrlWithDomain(HttpServletRequest request, String domain, boolean isStationDomain) {
		String url = request.getHeader("Referer");
		if (url == null || !isStationDomain) {
			return "http://" + domain;
		} else {
			return "http://" + domain + url.substring(url.indexOf("/", 8));
		}
	}

	private static HashMap<String, Object> getSessionMap(HttpServletRequest request, String maidouSessionid) {
		HashMap<String, Object> map = null;
		if (maidouSessionid != null) {
			HttpSession session = request.getSession();
			map = (HashMap<String, Object>) session.getAttribute(maidouSessionid);
			//map = (HashMap<String, Object>) RedisIO.getInstance().getObject(maidouSessionid);
		}
		boolean result = false;
		if (map != null) {
			result = true;
		}
		if (log.isDebugEnabled()) {
			log.info("get object from redis, session:" + maidouSessionid + " result: " + result);
		}
		if (map == null) {
			map = new HashMap<String, Object>();
			map.put("LAST_UPDATE_TIME", System.currentTimeMillis());
		}
		if (map.get("ver") == null) {
			map.put("ver", 0);
		}
		return map;
	}

	/**
	 * 把对象放置到Session
	 * @param request
	 * @param key
	 * @param obj
	 */
	public static void putSession(HttpServletRequest request, HttpServletResponse response, String key, Object obj) {
		String maidouSessionid = getMaidouSessionId(request, response);
		HashMap<String, Object> map = getSessionMap(request, maidouSessionid);
		map.put("LAST_UPDATE_TIME", System.currentTimeMillis());
		map.put(key, obj);
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(ServletUtil.LOGIN_SESSION_TIME);
		session.setAttribute(maidouSessionid, map);
		//RedisIO.getInstance().set(maidouSessionid, map, ServletUtil.LOGIN_SESSION_TIME);
		if (log.isDebugEnabled()) {
			log.info("put object to memcached, session:" + maidouSessionid + " session_time: " + ServletUtil.LOGIN_SESSION_TIME);
		}
	}

	/**
	 * 从Session中取对象
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object getSession(HttpServletRequest request, HttpServletResponse response, String key) {
		String maidouSessionid = getMaidouSessionId(request, response);
		return getSessionBySessionId(request, key, maidouSessionid);
	}

	public static Object getSessionBySessionId(HttpServletRequest request, String key, String maidouSessionid) {
		try {
			HashMap<String, Object> map = getSessionMap(request, maidouSessionid);
			Object obj = map.get("LAST_UPDATE_TIME");
			if (obj != null) {
				long updateTime = Long.parseLong(obj.toString());
				long period = System.currentTimeMillis() - updateTime;
				if (period > 300000) {
					int ver = Integer.parseInt(map.get("ver").toString());
					map.put("ver", ++ver);
					HttpSession session = request.getSession(true);
					session.setMaxInactiveInterval(ServletUtil.LOGIN_SESSION_TIME);
					session.setAttribute(maidouSessionid, map);
					//RedisIO.getInstance().set(maidouSessionid, map, ServletUtil.LOGIN_SESSION_TIME);
				}
			}
			return map.get(key);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从Session中删除对象
	 * @param request
	 * @param key
	 */
	public static void removeSession(HttpServletRequest request, HttpServletResponse response, String key) {
		String maidouSessionid = getMaidouSessionId(request, response);
		if (StringUtils.isNotEmpty(maidouSessionid)) {
			HashMap<String, Object> map = getSessionMap(request, maidouSessionid);
			Object obj = map.get(key);
			if (log.isDebugEnabled()) {
				log.debug("REMOVE SESSION : key: " + key + " value : " + obj + " from " + maidouSessionid);
			}
			map.remove(key);
			int ver = Integer.parseInt(map.get("ver").toString());
			map.put("ver", ++ver);
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(ServletUtil.LOGIN_SESSION_TIME);
			session.setAttribute(maidouSessionid, map);
			/*
			RedisIO.getInstance().set(maidouSessionid, map, ServletUtil.LOGIN_SESSION_TIME);
			Object obj1 = RedisIO.getInstance().getObject(maidouSessionid);*/
		}
	}

	public static String getMaidouSessionId(HttpServletRequest request, HttpServletResponse response) {
		//	return request.getRequestedSessionId();
		if (request.getAttribute(ServletUtil.SESSION_ID) != null) {
			return request.getAttribute(ServletUtil.SESSION_ID).toString();
		} else {
			return getCookieValue(request, ServletUtil.SESSION_ID);
		}
	}

	/**
	 * 初始化maidouSessionid
	 * @param request
	 * @param response
	 */
	public static void initMaidouSessionId(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = getCookie(request, ServletUtil.SESSION_ID);
		String maidouSessionid = cookie == null ? null : cookie.getValue();
		if (maidouSessionid == null) {
			if (!StringUtils.isEmpty(request.getParameter(ServletUtil.SESSION_ID))) {
				maidouSessionid = request.getParameter(ServletUtil.SESSION_ID);
			} else {
				maidouSessionid = UUID.randomUUID().toString();
			}
			String host = request.getServerName();
			boolean use_lvmama_host = host.contains(".maidou.com");
			addCookie(response, ServletUtil.SESSION_ID, maidouSessionid, use_lvmama_host);
			request.setAttribute(ServletUtil.SESSION_ID, maidouSessionid);
		}
	}

	public static String getMobileLoginChannel(HttpServletRequest request) {
		String mobileChannel = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("mobileChannel")) {
					mobileChannel = cookies[i].getValue();
				}
			}
		}
		return mobileChannel;
	}

}
