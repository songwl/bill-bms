package com.yipeng.bill.bms.web.account;

import com.yipeng.bill.bms.core.utils.ServletUtil;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.UserCompany;
import com.yipeng.bill.bms.domain.UserFootMessage;
import com.yipeng.bill.bms.domain.UserHyperlink;
import com.yipeng.bill.bms.domain.UserImgurl;
import com.yipeng.bill.bms.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
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
	private UserMapper userMapper;
	@Autowired
    UserCompanyMapper userCompanyMapper;
	@Autowired
    UserImgurlMapper userImgurlMapper;
	@Autowired
    UserHyperlinkMapper userHyperlinkMapper;
	@Autowired
    UserFootMessageMapper userFootMessageMapper;
	@RequestMapping(value = "")
	public String logout(HttpServletRequest request, HttpServletResponse response,ModelMap map) {
		ServletUtil.removeSession(request, response, ServletUtil.SESSION_USER);
		//获取当前访问的网址
		String website= request.getServerName();
		//获取当前网址对应的公司信息数据库
		UserCompany userCompany=userCompanyMapper.selectByWebsite(website);
		//获取当前网址对应的滚动图片数据库
		List<UserImgurl> userImgurlList=userImgurlMapper.selectByWebsite(website);
		//获取当前网址对应的超链接
		List<UserHyperlink> userHyperlinkList=userHyperlinkMapper.selectByWebsite(website);
		//底部信息
		UserFootMessage userFootMessage=userFootMessageMapper.selectByWebsite(website);
		Map<String, Object> bms=new HashMap<>();
		if(!CollectionUtils.isEmpty(userImgurlList))
		{
			map.put("userImgurlList",userImgurlList);
		}
		if(!CollectionUtils.isEmpty(userHyperlinkList))
		{
			map.put("userHyperlinkList",userHyperlinkList);
		}
		if(userFootMessage!=null)
		{
			map.addAttribute("userFootMessage",userFootMessage);
		}
		map.addAttribute("bmsModel", userCompany);
		return "/user/login";
	}

}
