package com.yipeng.bill.bms.web;

import com.google.gson.JsonObject;
import com.mchange.v1.db.sql.ConnectionUtils;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.dao.offersetMapper;
import com.yipeng.bill.bms.domain.KeywordsPrice;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.offerset;
import com.yipeng.bill.bms.model.GetAddressIP;
import com.yipeng.bill.bms.model.KeywordToPrice;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
import com.yipeng.bill.bms.service.OptimizationToolService;
import com.yipeng.bill.bms.service.impl.OptimizationToolServiceImpl;
import com.yipeng.bill.bms.vo.LoginUser;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.ErrorManager;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/25.
 */
@Controller
@RequestMapping(value = "/optimizationTool")
public class OptimizationToolController extends BaseController {
    @Autowired
    private OptimizationToolService optimizationToolService;
    @Autowired
    private offersetMapper offersetMapper;
    @Autowired
    private QueryOfferController queryOfferController;
    @Autowired
    private UserMapper userMapper;


    /**
     * 关键词价格查询页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/keywordpricesearch", method = RequestMethod.GET)
    public String keywordpricesearch(HttpServletRequest request) {
        return "/optimizationtool/keywordpricesearch";
    }

    /**
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/ParameterSetting")
    public String ParameterSetting(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
        if ((!loginUser.hasRole("DISTRIBUTOR") && !loginUser.hasRole("AGENT")) || offerset == null || offerset.getState() != 1) {
            return null;
        }
        modelMap.put("user", loginUser);
        modelMap.put("keypt", offerset.getTokenid());
        modelMap.put("rote", offerset.getRate());
        return "/optimizationtool/ParameterSetting";
    }

    @RequestMapping(value = "/KeywordsList")
    public String KeywordsList(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        return "/optimizationtool/KeywordsList";
    }

    @RequestMapping(value = "/keywordpricesearchClick", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage keywordpricesearchClick(HttpServletRequest request, @RequestParam(required = true) String keywords) {
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser.hasRole("SUPER_ADMIN") || loginUser.hasRole("COMMISSIONER")) {
            List<KeywordsPrice> list = optimizationToolService.forbiddenWordsList(keywords, 1);
            return this.ajaxDone(1, "", list);
        } else {

            offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
            Pattern pattern = Pattern.compile("^(\\d+(\\.\\d{1,2})?)$");
            if (offerset == null || offerset.getRate() == null || !pattern.matcher(offerset.getRate().toString()).matches()) {
                return this.ajaxDone(-1, "请去设置正确的两位小数倍率", null);
            }
            List<KeywordsPrice> list = optimizationToolService.forbiddenWordsList(keywords, offerset.getRate());

            return this.ajaxDone(1, "", list);
        }
    }

    @RequestMapping(value = "/LoopAllKeywords", method = RequestMethod.POST)
    @ResponseBody
    public String LoopAllKeywords() {
        return optimizationToolService.LoopAllKeywords() ? "1" : "0";
    }

    @RequestMapping(value = "/RestKeyt", method = RequestMethod.POST)
    @ResponseBody
    public String RestKeyt() {
        LoginUser loginUser = this.getCurrentAccount();
        offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
        if ((!loginUser.hasRole("DISTRIBUTOR") && !loginUser.hasRole("AGENT")) || offerset == null || offerset.getState() != 1) {
            return null;
        }
        String keypt = optimizationToolService.UpdateToken(loginUser);
        return keypt;
    }

    @RequestMapping(value = "/UpdateRote", method = RequestMethod.POST)
    @ResponseBody
    public String UpdateRote(String rote) {
        Pattern pattern = Pattern.compile("^(\\d+(\\.\\d{1,2})?)$");
        if (!pattern.matcher(rote).matches()) {
            return "-1";
        }
        LoginUser loginUser = this.getCurrentAccount();
        offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
        if ((!loginUser.hasRole("DISTRIBUTOR") && !loginUser.hasRole("AGENT")) || offerset == null || offerset.getState() != 1) {
            return "-2";
        }
        boolean flag = optimizationToolService.UpdateRote(loginUser, Double.parseDouble(rote));
        return flag ? "1" : "0";
    }

    @RequestMapping(value = "/SetOffer", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage SetOffer(int type, String keywordNum, String dataUser) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("SUPER_ADMIN") && !loginUser.hasRole("DISTRIBUTOR")) {
            return this.ajaxDone(-1, "你没有权限", null);
        }
        if (loginUser.hasRole("DISTRIBUTOR")) {
            String RoleName = userMapper.selectRoleName(dataUser);
            if (!RoleName.equals("AGENT")) {
                return this.ajaxDone(-2, "所选账号不是代理商", null);
            }
        }
        if (type != 0 && type != 1) {
            return this.ajaxDone(-2, "异常错误", null);
        }
        boolean flag = optimizationToolService.setOffer(type, keywordNum, dataUser);
        return this.ajaxDone(flag ? 0 : 1, flag ? "成功" : "失败", null);
    }

    @RequestMapping(value = "/GetOffer", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage GetOffer(String dataUser) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("SUPER_ADMIN")) {
            return this.ajaxDone(-1, "你没有权限", null);
        }
        offerset offerset = offersetMapper.selectByUserId(Long.parseLong(dataUser));
        if (offerset == null || offerset.getState() == 0) {
            return this.ajaxDone(0, "", null);
        }
        return this.ajaxDone(1, offerset.getRequestsecond().toString(), null);
    }

    @RequestMapping(value = "/GetOfferAgent", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage GetOfferAgent(String dataUser) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("DISTRIBUTOR")) {
            return this.ajaxDone(-1, "你没有权限", null);
        }
        offerset offerset = offersetMapper.selectByUserId(Long.parseLong(dataUser));
        if (offerset == null || offerset.getState() == 0) {
            return this.ajaxDone(0, "", null);
        }
        return this.ajaxDone(1, offerset.getRequestsecond().toString(), null);
    }

    @RequestMapping(value = "/OpenWebsitePower", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage OpenWebsitePower(HttpServletRequest httpServletRequest) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("SUPER_ADMIN")) {
            return this.ajaxDone(-1, "你没有权限", null);
        }
        Map<String, String[]> map = httpServletRequest.getParameterMap();
        int len = Integer.parseInt(map.get("len")[0]);
        String[] arr = new String[len];
        for (int i = 0; i < len; i++) {
            arr[i] = map.get("data[" + i + "][customerId]")[0].toString();
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("arr", arr);
        String type = map.get("type")[0];
        map1.put("type", type);
        boolean flag = optimizationToolService.setWebsitePower(map1);
        return this.ajaxDone(flag ? 1 : 0, "", null);
    }

    @RequestMapping(value = "/OpenAgentWebsitePower", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage OpenAgentWebsitePower(String data, int type) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("DISTRIBUTOR")) {
            return this.ajaxDone(-1, "你没有权限", null);
        }
        offerset offerset1 = offersetMapper.selectByUserId(loginUser.getId());
        if (offerset1.getState()==0) {
            return this.ajaxDone(-2, "你没有权限", null);
        }
        offerset offerset = offersetMapper.selectByUserId(Long.parseLong(data));
        if (offerset.getState()==0) {
            return this.ajaxDone(-2, "请先开通该代理商报价权限", null);
        }
        String RoleName = userMapper.selectRoleName(data);
        if (!RoleName.equals("AGENT")) {
            return this.ajaxDone(-2, "所选账号不是代理商", null);
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("arr", data);
        map1.put("type", type);
        boolean flag = optimizationToolService.setAgentWebsitePower(map1);
        return this.ajaxDone(flag ? 1 : 0, "", null);
    }

    @RequestMapping(value = "/OpenDistributorWebsitePower", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage OpenDistributorWebsitePower(String data, int type) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("SUPER_ADMIN")) {
            return this.ajaxDone(-1, "你没有权限", null);
        }
        offerset offerset = offersetMapper.selectByUserId(Long.parseLong(data));
        if (offerset.getState()==0) {
            return this.ajaxDone(-2, "请先开通该渠道商报价权限", null);
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("arr", data);
        map1.put("type", type);
        boolean flag = optimizationToolService.setAgentWebsitePower(map1);
        return this.ajaxDone(flag ? 1 : 0, "", null);
    }

    @RequestMapping(value = "/GetKeywordsList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> GetKeywordsList(int limit, int offset, String keywords) {
        LoginUser loginUser = this.getCurrentAccount();
        if (!loginUser.hasRole("SUPER_ADMIN")) {
            return null;
        }
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
        Map<String, Object> modelMap = optimizationToolService.GetKeywordsList(params);
        return modelMap;
    }
}
