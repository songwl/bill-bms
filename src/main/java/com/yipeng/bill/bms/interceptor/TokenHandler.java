package com.yipeng.bill.bms.interceptor;

import com.yipeng.bill.bms.core.spring.interceptor.HttpLocalThread;
import com.yipeng.bill.bms.core.utils.ServletUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Random;

public class TokenHandler {

	private final static Logger logger = LoggerFactory.getLogger(TokenHandler.class);

	// 生成一个唯一值的token
	public synchronized static String generateGUID() {
		try {
			String token = new BigInteger(165, new Random()).toString(36).toUpperCase();
			ServletUtil.putSession(HttpLocalThread.getRequest(), HttpLocalThread.getResponse(), ConstantToken.SPRINGMVC_TOKEN, token);

			return token;
		} catch (IllegalStateException e) {
			logger.error("generateGUID() mothod find bug,by token session...");
		}
		return null;
	}

	// 验证表单token值和session中的token值是否一致
	public static boolean validToken(HttpServletRequest request) {
		String inputToken = getInputToken(HttpLocalThread.getRequest());

		if (inputToken == null) {
			logger.warn("token is not valid!inputToken is NULL");
			return false;
		}

		String sessionToken = (String) ServletUtil.getSession(HttpLocalThread.getRequest(), HttpLocalThread.getResponse(), ConstantToken.SPRINGMVC_TOKEN);
		if (StringUtils.isEmpty(sessionToken) || !StringUtils.equals(sessionToken, inputToken)) {
			logger.warn("token is not valid!inputToken='" + inputToken + "',sessionToken = '" + sessionToken + "'");
			return false;
		}
		ServletUtil.putSession(HttpLocalThread.getRequest(), HttpLocalThread.getResponse(), ConstantToken.SPRINGMVC_TOKEN, "");
		return true;
	}

	// 获取表单中token值
	public static String getInputToken(HttpServletRequest request) {
		String token = request.getParameter(ConstantToken.SPRINGMVC_TOKEN);
		return token;
	}
}
