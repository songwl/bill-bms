package com.yipeng.bill.bms.web.account;

import com.yipeng.bill.bms.core.crypto.CryptoUtils;
import com.yipeng.bill.bms.core.utils.ServletUtil;
import com.yipeng.bill.bms.domain.Role;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.UserRole;
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
import java.util.List;

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

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		return  "/user/login";
	}

	@RequestMapping(value="/login",method = RequestMethod.POST)
	public  String dologin(HttpServletRequest request,HttpServletResponse response,
						   @RequestParam String userName, @RequestParam String password,ModelMap modelMap)
	{
		User user = userService.getUserByName(userName);

		if(user!=null)
		{
			if(user.getUserName().equals(userName)&&user.getPassword().equals(CryptoUtils.md5(password)))
			{
				boolean flag = executeLogin(request, response, user);
				return "redirect:/index";
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
