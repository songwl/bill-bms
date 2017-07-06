package com.yipeng.bill.bms.web.account;

import com.yipeng.bill.bms.core.crypto.CryptoUtils;
import com.yipeng.bill.bms.core.utils.ServletUtil;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.RoleService;
import com.yipeng.bill.bms.service.UserRoleService;
import com.yipeng.bill.bms.service.UserService;
import com.yipeng.bill.bms.vo.LoginUser;
import com.yipeng.bill.bms.web.BaseController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * Created by song on 2016/6/2.
 */
@Controller
@RequestMapping(value = "")
public class LoginController extends BaseController {

	public static final String V_PATH_AJAX_LOGIN = "/unauthorized";

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRoleService userRoleService;
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
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request,ModelMap map) {
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
		return  "/user/login";
	}

	@RequestMapping(value="/login",method = RequestMethod.POST)
	public  String dologin(HttpServletRequest request,HttpServletResponse response,HttpSession session,
						   @RequestParam String userName, @RequestParam String password,@RequestParam String code,ModelMap modelMap)
	{
		String website= request.getServerName();
		UserCompany userCompany=userCompanyMapper.selectByWebsite(website);
		Map<String, Object> bms=new HashMap<>();
		modelMap.addAttribute("bmsModel", userCompany);
		User user = userService.getUserByName(userName);
		String codeSession = (String) session.getAttribute("code");

			if(user!=null)
			{
				if(user.getUserName().equals(userName)&&user.getPassword().equals(CryptoUtils.md5(password)))
				{
					if(codeSession.toLowerCase().equals(code.toLowerCase()))
					{
						if(user.getStatus())
						{
							int loginCount=user.getLoginCount();
							user.setLastLoginTime(new Date());
							user.setLoginCount(loginCount+1);
							userMapper.updateByPrimaryKeySelective(user);
							boolean flag = executeLogin(request, response, user);
							return "redirect:/index";
						}
						else
						{
							modelMap.addAttribute("loginFailureMessage", "您无权限登录系统,请联系管理!");
							return "/user/login";

						}

			    	}
					else
					{
						modelMap.addAttribute("loginFailureMessage", "验证码错误");
						return "/user/login";
					}
			}
		}
			modelMap.addAttribute("loginFailureMessage", "用户名密码错误");
			return "/user/login";



	}


	/*
	@RequestMapping(value = "ajax", method = RequestMethod.POST)
	public ResultMessage ajaxlogin(@RequestParam String loginName, @RequestParam String password, @RequestParam(required = false, defaultValue = "false") boolean rememberMe,
			HttpServletRequest request, HttpServletResponse response) {
		String loginFailureMessage = "";
		if (StringUtils.isNotEmpty(loginName) && StringUtils.isNotEmpty(password)) {
			LoginAccount account = userService.getSysUserByLoginname(loginName);
			if (account != null && account.getLoginPassword().equals(CryptoUtils.md5(password))) {
				boolean flag = executeLogin(request, response, account);
				if (flag) {
					return this.ajaxDoneSuccess("登录成功");
				} else {
					loginFailureMessage = "系统异常";
				}
			} else {
				loginFailureMessage = "用户名密码错误";
			}
		} else {
			loginFailureMessage = "用户名密码不能为空";
		}
		return this.ajaxDoneError("登录失败", loginFailureMessage);

	}

	@RequestMapping(value = "unauthorized", method = RequestMethod.GET)
	public String unauthorized(HttpServletRequest request) {
		return V_PATH_LOGIN;
	}

	*/

	/**
	 * 重置密码
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/loginSon",method =RequestMethod.GET)
	public String loginSon(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId)
	{

		User user1=userMapper.selectByPrimaryKey(Long.parseLong(userId));
		Boolean  flag= executeLogin(request, response, user1);
		return "redirect:/index";
	}


	private boolean executeLogin(HttpServletRequest request, HttpServletResponse response, User user) {
		try {
			LoginUser loginUser = new LoginUser();
			BeanUtils.copyProperties(user,loginUser);

			List<UserRole> userRoleList = userRoleService.findUserRolesByUserId(user.getId());
			if (!CollectionUtils.isEmpty(userRoleList)) {
				for (UserRole userRole : userRoleList) {
					Role role = roleService.getRoleById(userRole.getRoleId());
					loginUser.getRoles().add(role.getRoleCode());
				}
			}
			ServletUtil.initMaidouSessionId(request, response);
			ServletUtil.putSession(request, response, ServletUtil.SESSION_USER, loginUser);
		} catch (Exception e) {
			//登陆失败
			return false;
		}
		return true;
	}

}
