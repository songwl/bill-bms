package com.yipeng.bill.bms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yipeng.bill.bms.core.utils.ServletUtil;

public class PermissionFilter implements Filter {
	private String[] arr;

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/** 
	 * 
	*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI().toLowerCase();
		ServletUtil.initMaidouSessionId(req, res);
		if (uri.contains(";jsessionid=")) {
			uri = uri.substring(0, uri.indexOf(";jsessionid="));
		}
		boolean isNeedCheck = true;
		for (String str : arr) {
			if (uri.toLowerCase().matches(str.toLowerCase().trim())) {
				isNeedCheck = false;
				break;
			}
		}
		if (isNeedCheck) {
			//验证是否登录
			Object user = ServletUtil.getSession(req, res, ServletUtil.SESSION_USER);
			if (user == null || uri.equalsIgnoreCase(req.getContextPath()) || uri.equalsIgnoreCase(req.getContextPath() + "/")) {
				res.sendRedirect(req.getContextPath() + "/login");
				return;
			}
			//验证权限
			if (isPermRefused(req, user)) {
				//res.sendRedirect("/back/permError.html?from=" + URLEncoder.encode(req.getRequestURI()) + "&userId=" + URLEncoder.encode(String.valueOf(user.getUserId())));
				return;
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String urls = fConfig.getInitParameter("excludeUrl");
		arr = urls.split(",");
	}

	//验证权限
	private boolean isPermRefused(HttpServletRequest request, Object user) {

		return false;
	}
}
