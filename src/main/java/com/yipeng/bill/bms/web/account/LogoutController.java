package com.yipeng.bill.bms.web.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yipeng.bill.bms.core.utils.ServletUtil;
import com.yipeng.bill.bms.web.BaseController;

/**
 * 
 * Created by song on 2016/6/2.
 */
@Controller
@RequestMapping(value = "/logout")
public class LogoutController extends BaseController {
	//视图地址映射

	@RequestMapping(value = "")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		ServletUtil.removeSession(request, response, ServletUtil.SESSION_USER);
		return "/user/login";
	}

}
