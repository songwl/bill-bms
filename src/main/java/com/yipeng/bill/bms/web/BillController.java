package com.yipeng.bill.bms.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import com.yipeng.bill.bms.core.model.Page;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.core.utils.DateUtils;
import com.yipeng.bill.bms.dao.BillCostMapper;
import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.dao.BillPriceMapper;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.BillPriceDetails;
import com.yipeng.bill.bms.service.BillService;
import com.yipeng.bill.bms.service.UserService;
import com.yipeng.bill.bms.vo.BillDetails;
import com.yipeng.bill.bms.vo.LoginUser;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

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
    private  UserMapper userMapper;
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
        User user = this.getCurrentAccount();
        Map<String, Long> params = new HashMap<>();
        //查询当前登录对象对应的客户
        params.put("createId", user.getId());
        List<User> userList = userService.userCreater(params);
        modelMap.put("userList", userList);
        return "/bill/billCustomer";
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
            model.put("userList", userList);
            model.addAttribute("bmsModel", bms);
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
                                                   String state, String searchStandard, String firstRanking1, String firstRanking2
            , String newRanking1, String newRanking2, String newchange1, String newchange2,
                                                   String addTime1, String addTime2) {
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

        if (!searchStandard.isEmpty()) {
            params.put("searchStandard", searchStandard);
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
        if (!newchange2.isEmpty() && Integer.parseInt(newchange2) > 0) {
            params.put("newchange2", newchange2);
        }
        if (!addTime1.isEmpty()) {
            params.put("addTime1", addTime1);
        }
        if (!addTime2.isEmpty()) {
            params.put("addTime2", addTime2);
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
            long ret = 3;
            Map<String, Long> params = new HashMap<>();
            params.put("role", ret);
            List<User> userList = userService.getUserAll(params);
            model.put("userList", userList);
            //渠道商
            long ret1 = 4;
            Map<String, Long> params1 = new HashMap<>();
            params1.put("role", ret1);
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
                                              String state, String state2, String searchStandard,String standardDays,String createTime) throws UnsupportedEncodingException {
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
        if (user.hasRole("DISTRIBUTOR")) {
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

        User user = this.getCurrentAccount();
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
            return this.ajaxDoneSuccess("操作成功");

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
            return this.ajaxDoneSuccess("操作成功");

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
     * 待审核订单预览
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


        int way;
        if (user.hasRole("DISTRIBUTOR")) {
            way = 3;
        } else {
            way = 2;
        }
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
        params.put("way", way);

        Map<String, Object> modelMap = billService.getBillDetails(params, user);
        return modelMap;
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
     * 审核订单
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
     * 订单切换确认
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
    public ResultMessage importUser(HttpServletRequest request,MultipartFile file) {
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
