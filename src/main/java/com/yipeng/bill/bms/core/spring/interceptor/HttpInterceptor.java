package com.yipeng.bill.bms.core.spring.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * HttpServletRequest,HttpServletResponse对象提取
 * Created by lancey on 15/1/7.
 */
public final class HttpInterceptor extends HandlerInterceptorAdapter {
	@Override
	public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HttpLocalThread.clean();
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
		HttpLocalThread.set(request, response);
		return super.preHandle(request, response, handler);
	}
}
