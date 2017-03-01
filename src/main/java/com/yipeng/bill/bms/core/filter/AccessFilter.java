package com.yipeng.bill.bms.core.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;

import com.yipeng.bill.bms.core.utils.ServletUtil;

public class AccessFilter implements Filter {

	//用户管理
	private static Logger log = Logger.getLogger(AccessFilter.class);
	private static String[] urls;
	private static String[] excludeUrls;

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1;
		ServletUtil.initMaidouSessionId(req, res);
		String path = req.getRequestURI().toLowerCase();
		if (isInCheck(path)) {
			if (!isLogon(req, res)) {
				String reqUrl = req.getRequestURL().toString();
				Map<String, String[]> paras = req.getParameterMap();
				if (paras != null) {
					Set<String> set = paras.keySet();
					int i = 0;
					for (String key : set) {
						if (!key.equalsIgnoreCase("ticket")) {
							if (i == 0) {
								reqUrl = reqUrl + "?" + key + "=" + paras.get(key)[0];
							} else {
								reqUrl = reqUrl + "&" + key + "=" + paras.get(key)[0];
							}
							i++;
						}
					}
				}
				//ServletUtil.addCookie(res, "unm", "", 0);
				String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
				res.sendRedirect(basePath + "/login?service=" + URLEncoder.encode(reqUrl, "UTF-8"));
				return;
			}
		}
		long beginTime = System.currentTimeMillis();
		arg2.doFilter(arg0, arg1);
		long count = System.currentTimeMillis() - beginTime;
		if (log.isDebugEnabled()) {
			log.debug(req.getRequestURI() + " spent " + count + " ms.");
		}
	}

	private boolean isLogon(HttpServletRequest request, HttpServletResponse response) {
		Object o = ServletUtil.getSession(request, response, ServletUtil.SESSION_ID);
		if (o == null) {
			return false;
		}
		return true;
	}

	/**
	 * 解析urlrewrite path.
	 * @param path
	 * @return String 解析后的url.
	 */
	private boolean isInCheck(final String path) {
		if (checkExclude(path)) {
			return false;
		}
		if (checkUrl(path)) {
			if (urls == null)
				return false;
			if (urls.length == 0)
				return false;
			if (path.lastIndexOf(".do") > 0) {
				for (int i = 0; i < urls.length; i++) {
					if (path.matches(urls[i] + ".*")) {
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

	/**
	 * 不需要检查的地址列表
	 * @param path
	 * @return
	 */
	private boolean checkExclude(final String path) {
		if (ArrayUtils.isNotEmpty(excludeUrls)) {
			for (String exclude : excludeUrls) {
				if (pathMatcher.match(exclude, path)) {
					return true;
				}
			}
		}
		return false;
	}

	private AntPathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 以下后缀的不需要过滤 
	 * @param str
	 * @return
	 */
	private boolean checkUrl(String str) {
		String[] s = new String[] { ".jpg", ".js", ".gif", ".css" };
		for (int i = 0; i < s.length; i++) {
			if (str.toLowerCase().lastIndexOf(s[i]) > 0) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		String temp = arg0.getInitParameter("url");
		if (log.isDebugEnabled())
			log.debug("get init parameter");
		if (temp != null) {
			urls = temp.split(",");
		}
		String excludeUrl = arg0.getInitParameter("excludeUrls");
		if (StringUtils.isNotEmpty(excludeUrl)) {
			excludeUrls = excludeUrl.split(",");
		}
	}

}
