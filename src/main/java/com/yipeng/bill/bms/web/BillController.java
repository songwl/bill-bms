package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.BillGroup;
import com.yipeng.bill.bms.domain.Role;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.UserBillType;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.service.UserService;
import com.yipeng.bill.bms.vo.LoginUser;
import com.yipeng.bill.bms.vo.ZhuanYuanDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
@Controller
@RequestMapping(value = "/order")
public class BillController extends BaseController {
    @Autowired
    private BillService billService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private BillGroupMapper billGroupMapper;
    @Autowired
    private UserBillTypeMapper userBillTypeMapper;
    /**
     * 上下层
     *
     * @param model
     * @param way
     * @return
     */
    @RequestMapping(value = "/list")
    public String listDetails(ModelMap model, @RequestParam(defaultValue = "1") String way) {
        Map<String, Object> bms = new HashMap<>();
        LoginUser user = this.getCurrentAccount();
        bms.put("user", user);
        //type 1代表 查询上游的订单   2代表 查询提交到下游的订单
        model.put("way", way);
        List<User> userList = userService.getSearchUser(user, way);
        model.put("userList", userList);
        model.addAttribute("bmsModel", bms);
        Map<String,Object> sqlMap=new HashMap<>();
        sqlMap.put("userId",user.getId());
        List<BillGroup> billGroupList=billGroupMapper.selectByUserId(sqlMap);
        model.put("billGroupList", billGroupList);
        return "/bill/billList";
    }

    /**
     * 客户页面（渠道商和代理商 ）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/billCustomer")
    public String billCustomer(HttpServletRequest request, ModelMap modelMap) {
        LoginUser user = this.getCurrentAccount();
        Map<String, Long> params = new HashMap<>();
        List<UserBillType> userBillTypeList=new ArrayList<>();
        //查询当前登录对象对应的客户
        if(user.hasRole("ASSISTANT"))
        {
            params.put("createId", user.getCreateUserId());
            userBillTypeList=userBillTypeMapper.selectByUserId(user.getCreateUserId());
        }
        else
        {
            params.put("createId", user.getId());
            userBillTypeList=userBillTypeMapper.selectByUserId(user.getId());
        }

        List<User> userList = userService.userCreater(params);
        List<User> dailiList = userService.getDailiUser(params);
        Map<String, Object> bms = new HashMap<>();
        bms.put("user", user);
        modelMap.put("userList", userList);
        modelMap.put("dailiList", dailiList);
        modelMap.put("userBillTypeList", userBillTypeList);
        modelMap.addAttribute("bmsModel", bms);
        return "/bill/billCustomer";
    }

    /**
     * 切换订单，SELECT获取代理的客户
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDailiKehu")
    @ResponseBody
    public ResultMessage getDailiKehu(HttpServletRequest request,Long userId) {
        LoginUser user = this.getCurrentAccount();
        Map<String, Long> params = new HashMap<>();
        //查询当前登录对象对应的客户
        if(user.hasRole("ASSISTANT"))
        {
            params.put("createId", user.getCreateUserId());
        }
        else
        {
            params.put("createId", userId);
        }
        List<User> userList = userService.userCreater(params);
        return this.ajaxDone(1,"返回数据:",userList);


    }
    /**
     * 关键词优化
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/billOptimization")
    public String billOptimization(ModelMap model, @RequestParam(defaultValue = "1") String way) {
        LoginUser user = this.getCurrentAccount();
        if (user != null) {
            way = "2";
            Map<String, Object> bms = new HashMap<>();
            List<User> userList = userService.getSearchUser(user, way);
            bms.put("user", user);
            model.put("userList", userList);
            model.addAttribute("bmsModel", bms);
            Map<String,Object> sqlMap=new HashMap<>();
            sqlMap.put("userId",user.getId());
            List<BillGroup> billGroupList=billGroupMapper.selectByUserId(sqlMap);
            model.put("billGroupList", billGroupList);
        }

        return "/bill/billOptimization";
    }

    /**
     * 关键词优化列表获取
     *
     * @param limit
     * @param offset
     * @param way
     * @param website
     * @param state
     * @return
     */
    @RequestMapping(value = "/getBillOptimization")
    @ResponseBody
    public Map<String, Object> getBillOptimization(int limit, int offset, int way, String sortOrder, String sortName,
                                                   String website, String keywords, String searchName, String searchUserName,
                                                   String state,  String firstRanking1, String firstRanking2
                                                     , String newRanking1, String newRanking2, String newchange1, String newchange2,
                                                   String addTime1, String addTime2,String groupId,String standardDays1,String standardDays2) {
        offset = (offset - 1) * limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser loginUser = this.getCurrentAccount();
        if (!keywords.isEmpty()) {
            try {
                keywords = new String(keywords.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("keywords", keywords);
        }
        if (!website.isEmpty()) {
            params.put("website", website);
        }
        if (!searchName.isEmpty()) {
            try {
                searchName = new String(searchName.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("searchName", searchName);
        }
        if (!loginUser.hasRole("CUSTOMER")) {
            if (!searchUserName.isEmpty()) {
                params.put("searchUserNameId", searchUserName);
            }
        }
        if (!state.isEmpty()) {
            params.put("state", state);
        }

        if (sortName != null) {
            params.put("sortName", sortName);
            params.put("sortOrder", sortOrder);
        }
        if (!firstRanking1.isEmpty() && Integer.parseInt(firstRanking1) > 0) {
            params.put("firstRanking1", firstRanking1);
        }
        if (!firstRanking2.isEmpty() && Integer.parseInt(firstRanking2) > 0) {
            params.put("firstRanking2", firstRanking2);
        }

        if (!newRanking1.isEmpty() && Integer.parseInt(newRanking1) > 0) {
            params.put("newRanking1", newRanking1);
        }
        if (!newRanking2.isEmpty() && Integer.parseInt(newRanking2) > 0) {
            params.put("newRanking2", newRanking2);
        }
        if (!newchange1.isEmpty() && Integer.parseInt(newchange1) > 0) {
            params.put("newchange1", newchange1);
        }
        if (!standardDays1.isEmpty()) {
            params.put("standardDays1", standardDays1);
        }
        if (!standardDays2.isEmpty()) {
            params.put("standardDays2", standardDays2);
        }
        if (!newchange2.isEmpty() && Integer.parseInt(newchange2) > 0) {
            params.put("newchange2", newchange2);
        }
        if (!addTime1.isEmpty()) {
            params.put("addTime1", addTime1);
        }
        if (!addTime2.isEmpty()) {
            params.put("addTime2", addTime2);
        }
        if (!groupId.isEmpty()) {
            params.put("groupId", Long.parseLong(groupId));
        }

        LoginUser user = this.getCurrentAccount();
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("way", way);

        Map<String, Object> modelMap = billService.getBillDetails(params, user);
        return modelMap;
    }

    /**
     * 调整优化
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/OptimizationUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage OptimizationUpdate(HttpServletRequest request) {
        //getParameterMap()，获得请求参数map
        Map<String, String[]> map = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {

            int a = billService.OptimizationUpdate(map, user);
            return this.ajaxDoneSuccess("调整成功");

        } else {
            return this.ajaxDoneError("未登录");
        }
    }

    /**
     * 调整停止
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateYBYstate", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage updateYBYstate(HttpServletRequest request) {
        //getParameterMap()，获得请求参数map
        Map<String, String[]> map = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {

            int a = billService.updateYBYstate(map, user);
            if (a == 1) {
                return this.ajaxDoneSuccess("调整成功");
            } else {
                return this.ajaxDoneError("系统错误，请稍后再试！");
            }

        } else {
            return this.ajaxDoneError("未登录");
        }
    }

    /**
     * 待审核订单（审核）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/pendingAudit")
    public String pendingAudit(HttpServletRequest request, ModelMap model) {
        Map<String, Object> bms = new HashMap<>();
        LoginUser user = this.getCurrentAccount();
        if (user.hasRole("SUPER_ADMIN")) {
            //操作员
            Role role=roleMapper.selectByRoleCode("COMMISSIONER");
            Map<String, Long> params = new HashMap<>();
            params.put("role", role.getId());
            List<User> userList = userService.getUserAll(params);
            model.put("userList", userList);
            //渠道商
            Role role1=roleMapper.selectByRoleCode("DISTRIBUTOR");
            Map<String, Long> params1 = new HashMap<>();
            params1.put("role", role1.getId());
            List<User> distributorList = userService.getUserAll(params1);
            model.put("distributorList", distributorList);
        }
        else
        {

            String way="2";
            List<User> userList = userService.getSearchUser(user,way);//获取搜索框对应的客户
            model.put("distributorList", userList);
        }
        bms.put("user", user);
        model.addAttribute("bmsModel", bms);
        return "/bill/billPendingAudit";
    }

    /**
     * 订单table表格获取数据
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/getBillDetails")
    @ResponseBody
    public Map<String, Object> getBillDetails(int limit, int offset, int way, String sortOrder, String sortName,
                                              String website, String keywords, String searchName, String searchUserName,
                                              String state, String state2, String searchStandard,String standardDays,String createTime,String groupId) throws UnsupportedEncodingException {
        offset = (offset - 1) * limit;
            Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser loginUser = this.getCurrentAccount();
        if (!keywords.isEmpty()) {
            try {
                keywords = new String(keywords.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("keywords", keywords);
        }
        if (!website.isEmpty()) {
            params.put("website", website);
        }
        if (!searchName.isEmpty()) {
            try {
                searchName = new String(searchName.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("searchName", searchName);
        }
        if (!loginUser.hasRole("CUSTOMER")) {
            if (!searchUserName.isEmpty()) {
                params.put("searchUserNameId", searchUserName);
            }
        }
        if (!state.isEmpty()) {
            params.put("state", state);
        }
        if (!state2.isEmpty()) {
            params.put("state2", state2);
        }
        if (!searchStandard.isEmpty()) {
            params.put("searchStandard", searchStandard);
        }
        if (!standardDays.isEmpty()) {
            params.put("standardDays", standardDays);
        }
        if (!createTime.isEmpty()) {
            params.put("createTime", createTime);
        }
        if (!groupId.isEmpty()) {
            params.put("groupId", Long.parseLong(groupId));
        }
        if (sortName != null) {
            params.put("sortName", sortName);
            params.put("sortOrder", sortOrder);
        }
        LoginUser user = this.getCurrentAccount();
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("way", way);

        Map<String, Object> modelMap = billService.getBillDetails(params, user);


        return modelMap;
    }


    /**
     * 客户订单table表格获取数据
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/getCustomerBill")
    @ResponseBody
    public Map<String, Object> getCustomerBill(int limit, int offset, String sortOrder, String sortName,
                                               String website, String keywords, String searchName, String searchUserName,
                                               String state, String state2, String searchStandard,String standardDays,String addTime) {
        LoginUser user = this.getCurrentAccount();
        int way;
        if (user.hasRole("DISTRIBUTOR")||user.hasRole("ASSISTANT")) {
            way = 3;
        } else {
            way = 2;
        }
        offset = (offset - 1) * limit;
        // Page<Bill> page = this.getPageRequest();    //分页对象
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        if (!keywords.isEmpty()) {
            try {
                keywords = new String(keywords.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("keywords", keywords);
        }
        if (!website.isEmpty()) {
            params.put("website", website);
        }
        if (!searchName.isEmpty()) {
            try {
                searchName = new String(searchName.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("searchName", searchName);
        }
        if (!searchUserName.isEmpty()) {
            params.put("searchUserNameId", searchUserName);
        }
        if (!state.isEmpty()) {
            params.put("state", state);
        }
        if (!state2.isEmpty()) {
            params.put("state2", state2);
        }
        if (!searchStandard.isEmpty()) {
            params.put("searchStandard", searchStandard);
        }
        if (!standardDays.isEmpty()) {
            params.put("standardDays", standardDays);
        }
        if (!addTime.isEmpty()) {
            params.put("addTime", addTime);
        }
        if (sortName != null) {
            params.put("sortName", sortName);
            params.put("sortOrder", sortOrder);
        }
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("way", way);
        Map<String, Object> modelMap = billService.getBillDetails(params, user);
        return modelMap;
    }

    /**
     * 相同价提交
     *
     * @return
     */
    @RequestMapping(value = "/list/sameprice", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage samePrice(HttpServletRequest request) {

        //getParameterMap()，获得请求参数map
        Map<String, String[]> map = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        String errorDetails = billService.saveSameBill(map, user);
        return this.ajaxDoneSuccess(errorDetails);
    }

    /**
     * 不同价导入
     *
     * @return
     */
    @RequestMapping(value = "/list/diffrentprice", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage diffrentprice(HttpServletRequest request) {
        //getParameterMap()，获得请求参数map
        Map<String, String[]> map = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        String errorDetails = billService.savaDiffrentBill(map, user);

        return this.ajaxDoneSuccess(errorDetails);
    }
    /**
     * 客户提交订单
     *
     * @return
     */
    @RequestMapping(value = "/list/addBillByKehuCmt", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage addBillByKehuCmt(HttpServletRequest request) {

        //getParameterMap()，获得请求参数map
        Map<String, String[]> map = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        String errorDetails = billService.saveKeHuBill(map, user);
        return this.ajaxDoneSuccess(errorDetails);
    }


    /**
     * 调整价格
     *
     * @return
     */
    @RequestMapping(value = "/billList/updatePrice", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage updatePrice(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {
            int a = billService.updateBillPrice(params, user);
            if (a == 0) {
                return this.ajaxDoneSuccess("调整成功!");
            } else {
                return this.ajaxDoneError("未知错误,请稍后再试!");
            }

        } else {
            return this.ajaxDoneError("未登录");
        }
    }

    /**
     * 渠道商切换订单到代理商
     *
     * @return
     */
    @RequestMapping(value = "/billChangeDailiCmt", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage billChangeDailiCmt(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {
            String message = billService.billChangeDailiCmt(params, user);
            if ("".equals(message)) {
                return this.ajaxDoneSuccess("调整成功!");
            } else {
                return this.ajaxDoneError(message);
            }

        } else {
            return this.ajaxDoneError("未登录");
        }
    }

    /**
     * 修改订单信息（待审核的订单）
     *
     * @return
     */
    @RequestMapping(value = "/updateBillDetails", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage updateBillDetails(HttpServletRequest request, Long billId, String website, String keyword) {

        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser != null) {
            int a = billService.updateBillDetails(billId,website,keyword, loginUser);
            if (a == 0) {
                return this.ajaxDoneSuccess("修改成功!");
            } else if (a==1){
                return this.ajaxDoneError("网址填写错误!");
            }
            else if (a==2){
                return this.ajaxDoneError("请不要输入违禁词!");
            }
            else if (a==3){
                return this.ajaxDoneError("未知错误!");
            }
            else if (a==4){
                return this.ajaxDoneError("修改失败!");
            }
            else  {
                return this.ajaxDoneError("未知错误!");
            }

        } else {
            return this.ajaxDoneError("未登录");
        }


    }
    /**
     * 修改订单信息（优化中的订单）
     *
     * @return
     */
    @RequestMapping(value = "/updateBillDetailsYouHua", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage updateBillDetailsYouHua(HttpServletRequest request, Long billId, String website, String keyword) {

        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser != null) {
            int a = billService.updateBillDetailsYouHua(billId,website,keyword, loginUser);
            if (a == 0) {
                return this.ajaxDoneSuccess("修改成功!");
            } else if (a==1){
                return this.ajaxDoneError("网址填写错误!");
            }
            else if (a==2){
                return this.ajaxDoneError("请不要输入违禁词!");
            }
            else if (a==3){
                return this.ajaxDoneError("未知错误!");
            }
            else if (a==4){
                return this.ajaxDoneError("修改失败!");
            }
            else  {
                return this.ajaxDoneError("未知错误!");
            }

        } else {
            return this.ajaxDoneError("未登录");
        }


    }
    /**
     * 渠道商审核订单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/billList/distributorPrice", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage distributorPrice(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> params = request.getParameterMap();

        LoginUser user = this.getCurrentAccount();
        if (user != null) {
            int a = billService.distributorPrice(params, user);
            if (a == 0) {
                return this.ajaxDoneSuccess("成功！");
            } else {
                return this.ajaxDoneError("订单信息有误，无法审核！");
            }
        }
        return this.ajaxDoneError("未登录");

    }

    /**
     * 管理员审核订单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/billList/adminPrice", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage adminPrice(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> params = request.getParameterMap();

        User user = this.getCurrentAccount();
        if (user != null) {
            int a = billService.adminPrice(params, user);
            if (a == 0) {
                return this.ajaxDoneSuccess("审核成功！");
            } else {
                return this.ajaxDoneError("订单信息有误，无法审核！");
            }

        }
        return this.ajaxDoneError("未登录");

    }



    /**
     * 优化停止
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/billList/optimizationStop")
    @ResponseBody
    public ResultMessage optimizationStop(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {

            int a = billService.optimizationStop(params, user);
            if(a==1)
            {
                return this.ajaxDoneSuccess("操作成功");
            }
            else
            {
                return this.ajaxDoneSuccess("操作失败");
            }


        } else {
            return this.ajaxDoneError("未登录");
        }

    }

    /**
     * 启动优化
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/billList/optimizationStart")
    @ResponseBody
    public ResultMessage optimizationStart(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {

            int a = billService.optimizationStart(params, user);
            if(a==0)
            {
                return this.ajaxDoneSuccess("操作成功");
            }
            else
            {
                return this.ajaxDoneError("操作失败");
            }


        } else {
            return this.ajaxDoneError("未登录");
        }

    }

    /**
     * 启动优化
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/billList/deleteBill")
    @ResponseBody
    public ResultMessage deleteBill(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {

            int a = billService.deleteBill(params, user);
            return this.ajaxDoneSuccess("删除成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }

    /**
     * 待审核订单table表格获取数据(确认审核页面)
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/pendingAuditList")
    @ResponseBody
    public Map<String, Object> pendingAuditList(HttpServletRequest request, @RequestParam(required = true) int limit,
                                                @RequestParam(required = true) int offset, String website, String keywords,
                                                String searchName, String searchUserName, String sortOrder, String sortName) {

        offset = (offset - 1) * limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser user = this.getCurrentAccount();

        params.put("limit", limit);
        params.put("offset", offset);
        if (!keywords.isEmpty()) {
            try {
                keywords = new String(keywords.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("keywords", keywords);
        }
        if (!website.isEmpty()) {
            params.put("website", website);
        }
        if (!searchName.isEmpty()) {
            try {
                searchName = new String(searchName.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("searchName", searchName);
        }
        if (!searchUserName.isEmpty()) {
            User user1=userMapper.selectByUserName(searchUserName);
            params.put("searchUserNameId", user1.getId());
        }
        if (sortName != null) {
            params.put("sortName", sortName);
            params.put("sortOrder", sortOrder);
        }

        Map<String, Object> modelMap = billService.pendingAuditList(params, user);
            return modelMap;
    }

    /**
     * 待审核订单（预览）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/pendingAuditView")
    public String pendingAuditView(HttpServletRequest request, ModelMap model) {
        User user = this.getCurrentAccount();
        Map<String, Long> params = new HashMap<>();
        //查询当前登录对象对应的客户
        params.put("createId", user.getId());
        List<User> userList = userService.userCreater(params);
        model.put("userList", userList);
        return "/bill/billPendingAuditView";
    }


    /**
     * 渠道商待审核订单预览
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/pendingAuditViewList")
    @ResponseBody
    public Map<String, Object> pendingAuditViewList(HttpServletRequest request, @RequestParam(required = true) int limit,
                                                    @RequestParam(required = true) int offset, String website, String keywords,
                                                    String searchName, String searchUserName, String state) {

        LoginUser user = this.getCurrentAccount();
        offset = (offset - 1) * limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        if (!keywords.isEmpty()) {
            try {
                keywords = new String(keywords.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("keywords", keywords);
        }
        if (!website.isEmpty()) {
            params.put("website", website);
        }
        if (!searchName.isEmpty()) {
            try {
                searchName = new String(searchName.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("searchName", searchName);
        }
        if (!searchUserName.isEmpty()) {
            params.put("searchUserNameId", searchUserName);
        }
        int way=0;
        if (!state.isEmpty()) {
            params.put("state", state);
        }
        if (user.hasRole("DISTRIBUTOR")||user.hasRole("ASSISTANT")) {
            way = 3;
            params.put("state2", -4);
        }
        else  if (user.hasRole("AGENT")) {
            params.put("state2", 0);
            params.put("state3", -4);
            params.put("state4", -3);
            way = 2;
        }
        else if(user.hasRole("CUSTOMER"))
        {
            params.put("state2", 0);
            params.put("state3", -4);
            params.put("state4", -3);
            params.put("state5", -2);
            params.put("state6", -1);
            way = 1;

            Map<String, Object> modelMap = billService.pendingAuditViewKeHuList(params, user);
            return modelMap;
        }
        else {
            way = 2;
        }


        params.put("way", way);

        Map<String, Object> modelMap = billService.getBillDetails(params, user);
        return modelMap;
    }

    /**
     * 待审核订单（预览）客户提交的订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/pendingAuditView1")
    public String pendingAuditView1(HttpServletRequest request, ModelMap model) {
        User user = this.getCurrentAccount();
        Map<String, Long> params = new HashMap<>();
        //查询当前登录对象对应的客户
        params.put("createId", user.getId());
        List<User> userList = userService.userCreater(params);
        model.put("userList", userList);
        return "/bill/billPendingAuditView1";
    }
    /**
     * 待审核订单（客户自己提交的订单）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/pendingAuditViewKeHu")
    public String pendingAuditViewKeHu(HttpServletRequest request, ModelMap model) {
        User user = this.getCurrentAccount();
        Map<String, Long> params = new HashMap<>();
        //查询当前登录对象对应的客户
        params.put("createId", user.getId());
        List<User> userList = userService.userCreater(params);
        model.put("userList", userList);
        return "/bill/billPendingAuditViewKeHu";
    }

    /**
     * 客户提交订单待审核订单审核预览
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/pendingAuditView1List")
    @ResponseBody
    public Map<String, Object> pendingAuditView1List(HttpServletRequest request, @RequestParam(required = true) int limit,
                                                    @RequestParam(required = true) int offset, String website, String keywords,
                                                    String searchName, String searchUserName, String state) {

        LoginUser user = this.getCurrentAccount();


        offset = (offset - 1) * limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数

        if (!keywords.isEmpty()) {
            try {
                keywords = new String(keywords.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("keywords", keywords);
        }
        if (!website.isEmpty()) {
            params.put("website", website);
        }
        if (!searchName.isEmpty()) {
            try {
                searchName = new String(searchName.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put("searchName", searchName);
        }
        if (!searchUserName.isEmpty()) {
            params.put("searchUserNameId", searchUserName);
        }
        if (!state.isEmpty()) {
            params.put("state", state);
        }
        if (user.hasRole("AGENT")) {
            params.put("state2", 0);
        }
        params.put("limit", limit);
        params.put("offset", offset);


        Map<String, Object> modelMap = billService.pendingAuditView1List(params, user);
        return modelMap;
    }


    /**
     *  审核客户提交的订单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/pendingAuditView1ListCmt", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage pendingAuditView1ListCmt(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> params = request.getParameterMap();

        LoginUser user = this.getCurrentAccount();
        if (user != null) {
            int a = billService.pendingAuditView1ListCmt(params, user);
            if (a == 0) {
                return this.ajaxDoneSuccess("审核成功！");
            } else {
                return this.ajaxDoneError("订单信息有误，无法审核！");
            }

        }
        return this.ajaxDoneError("未登录");

    }
    /**
     * s删除待审核订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteBillPendingAuditView")
    @ResponseBody
    public ResultMessage deleteBillPendingAuditView(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {
            int a = billService.deleteBillPendingAuditView(params, user);
            return this.ajaxDoneSuccess("操作成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }

    /**
     * 价格详情
     *
     * @param request
     * @param limit
     * @param offset
     * @param billId
     * @param way
     * @return
     */
    @RequestMapping(value = "/getPriceDetails", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPriceDetails(HttpServletRequest request, @RequestParam(required = true) int limit,
                                               @RequestParam(required = true) int offset, @RequestParam(required = true) String billId,
                                               @RequestParam(required = true) String way) {
        LoginUser user = this.getCurrentAccount();
        if (!billId.isEmpty()) {
            offset = (offset - 1) * limit;
            Map<String, Object> map = billService.getPriceDetails(limit, offset, billId, user, way);

            return map;
        } else {
            return null;

        }
    }

    /**
     * 订单反馈
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/billFeedback", method = RequestMethod.GET)
    public String billFeedback(HttpServletRequest request, ModelMap modelMap) {
        String website = request.getParameter("website");
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser != null) {
            billService.billFeedback(website);
        }
        return "/bill/billFeedback";
    }

    /**
     * 审核订单(停单)
     *
     * @param model
     * @param way
     * @return
     */
    @RequestMapping(value = "/billApplyStop")
    public String billApplyStop(ModelMap model) {

        Map<String, Object> bms = new HashMap<>();
        LoginUser user = this.getCurrentAccount();
        bms.put("user", user);
        model.addAttribute("bmsModel", bms);

        return "/bill/billApplyStop";
    }

    /**
     * 审核订单
     *
     * @param model
     * @param
     * @return
     */
    @RequestMapping(value = "/billApplyStopPass")
    public String billApplyStopPass(ModelMap model) {

        Map<String, Object> bms = new HashMap<>();
        LoginUser user = this.getCurrentAccount();
        bms.put("user", user);
        model.addAttribute("bmsModel", bms);

        return "/bill/billApplyStopPass";
    }

    /**
     * 申请停单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/applyStopBillConfirm")
    @ResponseBody
    public ResultMessage applyStopBillConfirm(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {

            int a = billService.applyStopBillConfirm(params, user);
            return this.ajaxDoneSuccess("操作成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }

    /**
     * 申请停单通过
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/applyStopBillPass")
    @ResponseBody
    public ResultMessage applyStopBillPass(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {

            int a = billService.applyStopBillPass(params, user);
            return this.ajaxDoneSuccess("操作成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }

    /**
     * 申请停单不通过
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/applyStopBillNotPass")
    @ResponseBody
    public ResultMessage applyStopBillNotPass(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {

            int a = billService.applyStopBillNotPass(params, user);
            return this.ajaxDoneSuccess("操作成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }

    /**
     * 申请优化(功能)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/applyToOptimization")
    @ResponseBody
    public ResultMessage applyToOptimization(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser != null) {

            int a = billService.applyToOptimization(params, loginUser);
            return this.ajaxDoneSuccess("操作成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }

    /**
     * 审核申请优化界面(优化方)
     *
     * @param model
     * @param
     * @return
     */
    @RequestMapping(value = "/billApplyToOptimization")
    public String billApplyToOptimization(ModelMap model) {

        Map<String, Object> bms = new HashMap<>();
        LoginUser user = this.getCurrentAccount();
        bms.put("user", user);
        model.addAttribute("bmsModel", bms);

        return "/bill/billApplyToOptimization";
    }
    /**
     * 审核申请优化界面(渠道方)
     *
     * @param model
     * @param
     * @return
     */
    @RequestMapping(value = "/billApplyToOptimizationBySon")
    public String billApplyToOptimizationBySon(ModelMap model) {

        Map<String, Object> bms = new HashMap<>();
        LoginUser user = this.getCurrentAccount();
        bms.put("user", user);
        model.addAttribute("bmsModel", bms);

        return "/bill/billApplyToOptimizationBySon";
    }
    /**
     * 审核申请优化通过table数据
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/billApplyToOptimizationBySonTable")
    @ResponseBody
    public Map<String, Object> billApplyToOptimizationBySonTable(int limit, int offset, String sortOrder, String sortName) {
        offset = (offset - 1) * limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser user = this.getCurrentAccount();

        params.put("limit", limit);
        params.put("offset", offset);
        params.put("sortOrder",sortOrder);
        params.put("sortName",sortName);
        if (user.hasRole("SUPER_ADMIN")) {
            params.put("way", 2);
        } else {
            params.put("way", 1);
        }

        if (user.hasRole("DISTRIBUTOR")) {
            params.put("applyState", -2);
        } else {
            params.put("applyState", -1);
        }
        params.put("state", 3);
        Map<String, Object> modelMap = billService.getBillDetails(params, user);
        return modelMap;

    }
    /**
     * 审核申请优化通过table数据
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/billApplyToOptimizationTable")
    @ResponseBody
    public Map<String, Object> billApplyToOptimizationTable(int limit, int offset, String sortOrder, String sortName) {
        offset = (offset - 1) * limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser user = this.getCurrentAccount();

        params.put("limit", limit);
        params.put("offset", offset);
        params.put("sortOrder",sortOrder);
        params.put("sortName",sortName);
        if (user.hasRole("SUPER_ADMIN")) {
            params.put("way", 2);
        } else {
            params.put("way", 1);
        }

        if (user.hasRole("DISTRIBUTOR")) {
            params.put("applyState", -1);
        } else {
            params.put("applyState", -2);
        }
        params.put("state", 3);
        Map<String, Object> modelMap = billService.getBillDetails(params, user);
        return modelMap;

    }
    /**
     * 申请优化的订单通过审核
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/billApplyToOptimizationPass")
    @ResponseBody
    public ResultMessage billApplyToOptimizationPass(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser != null) {

            int a = billService.billApplyToOptimizationPass(params, loginUser);
            return this.ajaxDoneSuccess("操作成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }

    /**
     * 审核订单table数据
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/billApplyStopTable")
    @ResponseBody
    public Map<String, Object> billApplyStopTable(int limit, int offset, String sortOrder, String sortName) {
        offset = (offset - 1) * limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser user = this.getCurrentAccount();

        params.put("limit", limit);
        params.put("offset", offset);
        params.put("sortOrder",sortOrder);
        params.put("sortName",sortName);
        if (user.hasRole("SUPER_ADMIN")) {
            params.put("way", 2);
        } else {
            params.put("way", 1);
        }

        if (user.hasRole("DISTRIBUTOR")) {
            params.put("applyState", 2);
        } else {
            params.put("applyState", 1);
        }
        params.put("state", 2);
        Map<String, Object> modelMap = billService.getBillDetails(params, user);
        return modelMap;

    }

    /**
     * 审核通过table数据
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/billApplyStopPassTable")
    @ResponseBody
    public Map<String, Object> billApplyStopPassTable(int limit, int offset, String sortOrder, String sortName) {
        offset = (offset - 1) * limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser user = this.getCurrentAccount();

        params.put("limit", limit);
        params.put("offset", offset);
        params.put("sortOrder",sortOrder);
        params.put("sortName",sortName);
        if (user.hasRole("SUPER_ADMIN")) {
            params.put("way", 2);
        } else {
            params.put("way", 1);
        }

        if (user.hasRole("DISTRIBUTOR")) {
            params.put("applyState", 1);
        } else {
            params.put("applyState", 2);
        }
        params.put("state", 2);
        Map<String, Object> modelMap = billService.getBillDetails(params, user);
        return modelMap;

    }
    /**
     * 申请不通过（订单主状态）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/billNotExamine")
    @ResponseBody
    public ResultMessage billNotExamine(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {

            int a = billService.billNotExamine(params, user);
            return this.ajaxDoneSuccess("操作成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }
    /**
     * 优化结算
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/billOptimizationSettlement")
    public String billOptimizationSettlement(ModelMap model) {

        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = billService.billOptimizationSettlement(loginUser);
        model.addAttribute("bmsModel", bms);
        return "/bill/billOptimizationSettlement";

    }

    /**
     * 代理商结算
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/billAgentSettlement")
    public String billAgentSettlement(ModelMap model) {
/*
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = billService.billOptimizationSettlement(loginUser);
        model.addAttribute("bmsModel", bms);*/
        return "/bill/billAgentSettlement";

    }
    /**
     * 今日消费
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/billDayCost")
    @ResponseBody
    public Map<String, Object> billDayCost( String sortOrder, String sortName,String searchTime) {

        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser loginUser = this.getCurrentAccount();

        Map<String, Object> modelMap = billService.billDayCost(loginUser,searchTime);
        return modelMap;

    }

    /**
     * 客户今日消费
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/billClientDayCost")
    @ResponseBody
    public Map<String, Object> billClientDayCost( String sortOrder, String sortName) {

        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> modelMap = billService.billClientDayCost(loginUser);
        return modelMap;

    }
    /**
     * 今日消费
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/billAgentDayCost")
    @ResponseBody
    public Map<String, Object> billAgentDayCost( String sortOrder, String sortName) {

        Map<String, Object> params = this.getSearchRequest(); //查询参数
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> modelMap = billService.billAgentDayCost(loginUser);
        return modelMap;

    }

    /**
     * 优化结算(用户余额)     *
     * @param
     * @return
     */
    @RequestMapping(value = "/userBalance")
    @ResponseBody
    public Map<String, Object> userBalance(ModelMap model) {

        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = billService.userBalance(loginUser);
        return bms;

    }

    /**
     * 优化结算(年度结算)     *
     * @param
     * @return
     */
    @RequestMapping(value = "/yearConsumption")
    @ResponseBody
    public Map<String, Object> yearConsumption() {

        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = billService.yearConsumption(loginUser);
        return bms;

    }
    /**
     * 优化结算(年度结算)     *
     * @param
     * @return
     */
    @RequestMapping(value = "/lastMonthConsumption")
    @ResponseBody
    public Map<String, Object> lastMonthConsumption() {

        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = billService.lastMonthConsumption(loginUser);
        return bms;

    }

    /**
     * 订单切换 （获取操作员列表）    *
     * @param
     * @return
     */
    @RequestMapping(value = "/getAllUsers")
    @ResponseBody
    public List<User> getAllUsers() {
        Map<String,Long> params=new HashMap<>();
        Long role=new Long(3);
        params.put("role",role);
        List<User> userList=userService.getUserAll(params);
        return userList;

    }
    /**
     * 订单切换渠道商确认
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/billChangeCmt")
    @ResponseBody
    public ResultMessage billChangeCmt(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser user = this.getCurrentAccount();
        if (user != null) {

            int a = billService.billChangeCmt(params, user);
            return this.ajaxDoneSuccess("调整成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }
    /**
     * 订单切换客户确认
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/billChangeToKeHucmt")
    @ResponseBody
    public ResultMessage billChangeToKeHucmt(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser!= null) {

            int a = billService.billChangeToKeHucmt(params, loginUser);
            return this.ajaxDoneSuccess("调整成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }

    /**
     * 客户方管理里面的客户方计算页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/billClientSideSettlement")
    public String billClientSideSettlement(ModelMap model) throws Exception {
        LoginUser loginUser=this.getCurrentAccount();
        Map<String, Object> bms=new HashMap<>();
		/*Map<String, Object> bms=new HashMap<>();*/
        bms.put("user", loginUser);
        model.addAttribute("bmsModel", bms);

        return "/bill/billClientSideSettlement";
    }

    /**
     * 客户方管理里面的客户方计算页面TABLE 获取数据
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/billClientSideSettlementTable")
    @ResponseBody
    public Map<String,Object> billClientSideSettlementTable(int limit, int offset, String sortOrder, String sortName) {
        LoginUser loginUser=this.getCurrentAccount();
        return null;
    }

    /**
     * 添加分组
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createGroup")
    @ResponseBody
    public ResultMessage createGroup(String groupName) {
        LoginUser loginUser=this.getCurrentAccount();
        Map<String,Object> params=new HashMap<>();
        params.put("groupName",groupName);
        String result=billService.createGroup(params,loginUser);
          return  this.ajaxDoneSuccess(result);
    }

    /**
     * 分组列表
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBillGroupTable")
    @ResponseBody
    public Map<String,Object> getBillGroupTable(int limit, int offset, String sortOrder, String sortName) {

        offset = (offset - 1) * limit;
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        params.put("sortOrder",sortOrder);
        params.put("sortName",sortName);
        LoginUser loginUser=this.getCurrentAccount();
        Map<String,Object> viewMap=new HashMap<>();
        viewMap=billService.getBillGroupTable(params,loginUser);
        return viewMap;
    }

    /**
     * 订单切换客户确认
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/billToGroupCmt")
    @ResponseBody
    public ResultMessage billToGroupCmt(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser!= null) {
            int a = billService.billToGroupCmt(params, loginUser);
            return this.ajaxDoneSuccess("调整成功");

        } else {
            return this.ajaxDoneError("未登录");
        }

    }
    /**
     * 出租订单分配
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/leaseBill",method = RequestMethod.POST)
    @ResponseBody
    public  ResultMessage leaseBill(HttpServletRequest request)
    {
        Map<String,String[]> map=request.getParameterMap();
        LoginUser loginUser=this.getCurrentAccount();
        int a=billService.leaseBill(map,loginUser);
        if(a<1)
        {
            return  this.ajaxDoneError("订单分配出错！");
        }
        else if(a==1)
        {
            return  this.ajaxDoneSuccess("订单分配成功！");
        }
        else
        {
            return  this.ajaxDoneSuccess("订单已经存在！");
        }

    }

    /**
     * 删除分组
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteGroup",method = RequestMethod.POST)
    @ResponseBody
    public  ResultMessage deleteGroup(String groupId)
    {
        LoginUser loginUser=this.getCurrentAccount();
        if(!"".equals(groupId))
        {
            int a=billService.deleteGroup(groupId,loginUser);
            if(a==1)
            {
                return this.ajaxDoneSuccess("删除成功！");
            }
            else
            {
                return this.ajaxDoneError("删除失败！");
            }
        }
        else
        {
            return this.ajaxDoneError("数据解析失败！！");
        }

    }

    /**
     * 删除分组
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getZhuanyuanDetails",method = RequestMethod.GET)
    @ResponseBody
    public  ResultMessage getZhuanyuanDetails(String groupId)
    {
        LoginUser loginUser=this.getCurrentAccount();
        List<ZhuanYuanDetails> zhuanYuanDetailsList=billService.getZhuanyuanDetails();
        return this.ajaxDone(1,"数据",zhuanYuanDetailsList);

    }
    //导出excel(实例)
    @RequestMapping(value = "/export.controller")
    public void export(String ids, HttpServletResponse response) throws IOException {
        // 只是让浏览器知道要保存为什么文件而已，真正的文件还是在流里面的数据，你设定一个下载类型并不会去改变流里的内容。
        //而实际上只要你的内容正确，文件后缀名之类可以随便改，就算你指定是下载excel文件，下载时我也可以把他改成pdf等。
        System.out.println(ids);
        response.setContentType("application/vnd.ms-excel");
        // 传递中文参数编码
        String codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
        List<User> list = new ArrayList<User>();
        String[] array = ids.split(",");
        int[] id = new int[array.length];
        for (int i = 0; i < id.length; i++) {
            // User user = service.getById(Integer.valueOf(array[i]));

            // 将数据添加到list中
            // list.add(user);
        }
        // 定义一个工作薄
        Workbook workbook = new HSSFWorkbook();
        // 创建一个sheet页
        Sheet sheet = workbook.createSheet("学生信息");
        // 创建一行
        Row row = sheet.createRow(0);
        // 在本行赋值 以0开始
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("性别");
        row.createCell(3).setCellValue("年龄");
        // 定义样式
        CellStyle cellStyle = workbook.createCellStyle();
        // 格式化日期
        //cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        // 遍历输出
        for (int i = 1; i <= list.size(); i++) {
            User user1 = list.get(i - 1);
            row = sheet.createRow(i);


        }
        OutputStream fOut = response.getOutputStream();
        workbook.write(fOut);
        fOut.flush();
        fOut.close();
    }

    /**
     * 批量导入价格
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadPrice")
    @ResponseBody
    public ResultMessage uploadPrice(HttpServletRequest request, MultipartFile file) {
        LoginUser loginUser=this.getCurrentAccount();

        List<String[]> fileList=parseFile(file);
        if(fileList.size()>0)
        {
            try{
                String aa=billService.uploadPrice(fileList,loginUser);
                return  ajaxDoneSuccess(aa);
            }
            catch (Exception e)
            {
                return   ajaxDoneError("错误："+e.getMessage());
            }

        }
        else
        {
            return  ajaxDoneError("未知错误！");

        }

    }




    /**
     * 功能描述:解析excel文件 <br>
     * 〈功能详细描述〉
     *
     * @param file
     * @return 返回excel中每行值得列表
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static List<String[]> parseFile(MultipartFile file) {
        List<String[]> list = new ArrayList<>();
        try {
            //新建WorkBook
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            //获取Sheet（工作薄）总个数
            int sheetNumber = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNumber; i++) {
                //获取Sheet（工作薄）
                Sheet sheet = wb.getSheetAt(i);
                //开始行数
                int firstRow = sheet.getFirstRowNum();
                //结束行数
                int lastRow = sheet.getLastRowNum();
                //判断该Sheet（工作薄)是否为空
                boolean isEmpty = false;
                if (firstRow == lastRow) {
                    isEmpty = true;
                }
                if (!isEmpty) {
                    for (int j = firstRow + 1; j <= lastRow; j++) {
                        //获取一行
                        Row row = sheet.getRow(j);
                        if (row != null) {
                            //开始列数
                            int firstCell = row.getFirstCellNum();
                            //结束列数
                            int lastCell = row.getLastCellNum();
                            //判断该行是否为空
                            String[] value = new String[lastCell];
                            if (firstCell != lastCell) {
                                for (int k = firstCell; k < lastCell; k++) {
                                    //获取一个单元格
                                    Cell cell = row.getCell(k);
                                    if (cell == null) {
                                        continue;
                                    }
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    Object str = null;
                                    //获取单元格，值的类型
                                    int cellType = cell.getCellType();

                                    if (cellType == Cell.CELL_TYPE_NUMERIC) {
                                        str = cell.getNumericCellValue();
                                    } else if (cellType == 1) {
                                        str = cell.getStringCellValue();
                                    } else if (cellType == 2) {
                                    } else if (cellType == 4) {
                                        str = cell.getBooleanCellValue();
                                    }
                                    value[k] = str.toString();
                                }
                            }
                            //每一行循环完对应的就是一个用户故事的所有属性全部拿到
                            list.add(value);
                        }
                    }
                }
            }
            //wb.close();
        } catch (IOException e) {

        } catch (Exception e) {
            e.printStackTrace();

        }

        List<String[]> emptyRows = new ArrayList<>();
        for(String[] row:list) {
            boolean isEmpty =true;
            for(String cell : row) {
                if(!StringUtils.isEmpty(cell)){
                    isEmpty = false;
                    break;
                }
            }
            if(isEmpty) {
                emptyRows.add(row);
            }
        }
        list.removeAll(emptyRows);
        return list;
    }


}
