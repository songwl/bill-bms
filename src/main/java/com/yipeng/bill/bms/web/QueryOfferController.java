package com.yipeng.bill.bms.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.LogsMapper;
import com.yipeng.bill.bms.dao.offersetMapper;
import com.yipeng.bill.bms.domain.Logs;
import com.yipeng.bill.bms.domain.offerset;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/QueryOffer")
public class QueryOfferController extends BaseController {

    @Autowired
    private LogsMapper logsMapper;
    @Autowired
    private offersetMapper offersetMapper;

    @RequestMapping(value = "/GetPrice", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage GetPrice(String xAction, String xParam, String xSign, String apiSign) {
       /* Logs logs = new Logs();
        logs.setCreatetime(new Date());
        logs.setOptype(1);
        logs.setUserid(new Long(1));
        logs.setOpobj("2");
        logs.setOpremake("xAction数据:" + xAction + ",xParam数据:" + xParam + ",xSign数据:" + xSign);
        logsMapper.insert(logs);*/

        Md5_UrlEncode md5_urlEncode = new Md5_UrlEncode();
        String wSign = null;
        //加密
        try {
            wSign = md5_urlEncode.EncoderByMd5(xAction + xParam);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!wSign.equals(xSign)) {
            return this.ajaxDone(-1, "xSign验证失败", null);
        }
        JsonParser parser = new JsonParser();//创建Json解析器
        JsonObject json = (JsonObject) parser.parse(xParam);
        String UserId = json.get("UserId").toString();//任务类型
        offerset offerset = offersetMapper.selectByUserId(12l);
        if (offerset == null) {

        }
        if (!apiSign.equals(offerset.getTokenid())) {
            return this.ajaxDone(-2, "apiSign验证失败", null);
        }
        JsonObject json1 = (JsonObject) parser.parse(json.get("Value").toString());
        String KeyWords = json1.get("keyword").toString();
        String[] arr = KeyWords.split(",");
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        Date updatetime = null;
        Date now = null;
        try {
            updatetime = sm.parse(offerset.getUpdatetime().toString());
            now = sm.parse(new Date().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (updatetime.getTime() < now.getTime()) {
            offerset.setSurplussecond(offerset.getRequestsecond());
            offerset.setUpdatetime(new Date());
            offersetMapper.updateByPrimaryKeySelective(offerset);
        }
        offerset offerset1 = offersetMapper.selectByUserId(1l);
        if (arr.length > offerset1.getRequestsecond()) {
            return this.ajaxDone(-3, "请求的关键词数量超过总数，剩余关键词数量：" + offerset1.getRequestsecond(), null);
        }

        return null;
    }
}
