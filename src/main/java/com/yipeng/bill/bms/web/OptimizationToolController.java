package com.yipeng.bill.bms.web;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.offersetMapper;
import com.yipeng.bill.bms.domain.KeywordsPrice;
import com.yipeng.bill.bms.domain.offerset;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
import com.yipeng.bill.bms.service.OptimizationToolService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
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

    @RequestMapping(value = "/ParameterSetting")
    public String ParameterSetting(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        modelMap.put("user", loginUser);
        offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
        modelMap.put("keypt", offerset.getTokenid());
        modelMap.put("rote", offerset.getRate());
        return "/optimizationtool/ParameterSetting";
    }

    @RequestMapping(value = "/keywordpricesearchClick", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage keywordpricesearchClick(HttpServletRequest request, @RequestParam(required = true) String keywords) {
        LoginUser loginUser = this.getCurrentAccount();
        offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
        Pattern pattern = Pattern.compile("^(\\d+(\\.\\d{1,2})?)$");
        if (offerset == null || offerset.getRate() == null || !pattern.matcher(offerset.getRate().toString()).matches()) {
            return this.ajaxDone(-1, "请去设置正确的两位小数倍率", null);
        }
        List<KeywordsPrice> list = optimizationToolService.forbiddenWordsList(keywords, offerset.getRate());

        return this.ajaxDone(1, "", list);
    }

    @RequestMapping(value = "/LoopAllKeywords", method = RequestMethod.POST)
    @ResponseBody
    public String LoopAllKeywords() {
        return optimizationToolService.LoopAllKeywords() ? "1" : "0";
    }

    @RequestMapping(value = "/RestKeyt", method = RequestMethod.POST)
    @ResponseBody
    public String RestKeyt() {
        String apiSign="897A54E9ECE5AFADB61F489B893E5869";
        String xAction="selectPrice";
        String xParam="{'UserId':123,'Value':{'keyword':'护发素,婚纱'} }";
        Md5_UrlEncode md5_urlEncode = new Md5_UrlEncode();
        String xSign = null;
        //加密
        try {
            xSign = md5_urlEncode.EncoderByMd5(xAction  + xParam);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        QueryOfferController queryOfferController=new QueryOfferController();
        queryOfferController.GetPrice(xAction,xParam,xSign,apiSign);




        LoginUser loginUser = this.getCurrentAccount();
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
        boolean flag = optimizationToolService.UpdateRote(loginUser, Double.parseDouble(rote));
        return flag ? "1" : "0";
    }
}
