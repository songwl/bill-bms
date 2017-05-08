package com.yipeng.bill.bms.web.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yipeng.bill.bms.dao.UserCompanyMapper;
import com.yipeng.bill.bms.domain.UserCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yipeng.bill.bms.core.utils.ServletUtil;
import com.yipeng.bill.bms.web.BaseController;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Created by song on 2016/6/2.
 */
@Controller
@RequestMapping(value = "/logout")
public class LogoutController extends BaseController {
	//视图地址映射
	@Autowired
	UserCompanyMapper userCompanyMapper;
	@RequestMapping(value = "")
	public String logout(HttpServletRequest request, HttpServletResponse response,ModelMap map) {
		ServletUtil.removeSession(request, response, ServletUtil.SESSION_USER);
		String website= request.getServerName();
		UserCompany userCompany=userCompanyMapper.selectByWebsite(website);
		Map<String, Object> bms=new HashMap<>();
		map.addAttribute("bmsModel", userCompany);
		return "/user/login";
	}

}
