package com.yipeng.bill.bms.web.account;

import com.yipeng.bill.bms.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户修改自己资料的Controller.
 * 
 * created by song on 2016/6/2.
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileController extends BaseController {
	public static final String V_PATH_PROFILE = "/account/profile";

	/*@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String updateForm(Model model) {
		Integer id = getCurrentAccountId();
		model.addAttribute("user", userService.getSysUserByUserId(id));
		return V_PATH_PROFILE;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String update(SysUser user) {
		userService.updateSysUser(user);
		return "redirect:/index";
	}*/

}
