package com.yipeng.bill.bms.web;


import com.google.gson.JsonObject;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.LogsMapper;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.dao.offersetMapper;
import com.yipeng.bill.bms.domain.Logs;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.offerset;
import com.yipeng.bill.bms.model.GetAddressIP;
import com.yipeng.bill.bms.model.KeywordToPrice;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
import com.yipeng.bill.bms.service.OptimizationToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yipeng.bill.bms.vo.LoginUser;

import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/QueryOffer")
public class QueryOfferController extends BaseController {

    @Autowired
    private LogsMapper logsMapper;
    @Autowired
    private offersetMapper offersetMapper;
    @Autowired
    private OptimizationToolService optimizationToolService;

    @RequestMapping(value = "/GetPrice", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage GetPrice(String xAction, String xParam,  String apiSign) throws SocketException {

        GetAddressIP getAddressIP = new GetAddressIP();
        String ip = getAddressIP.getAllNetInterfaces();
        /*Md5_UrlEncode md5_urlEncode = new Md5_UrlEncode();
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
        }*/
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();//创建Json解析器
        JsonObject json = (JsonObject) parser.parse(xParam);//json解析参数xParam
        String UserId = json.get("UserId").toString();//任务类型
        offerset offerset = offersetMapper.selectByUserId(Long.parseLong(UserId));//获取登录人的参数设置信息
        if (offerset == null || !apiSign.equals(offerset.getTokenid())) {//判断传入的apisingn是否与数据库里面的tokenid相等

            Logs logs = new Logs();
            logs.setCreatetime(new Date());
            logs.setOptype(-100);
            logs.setUserid(new Long(UserId));
            logs.setOpobj("2");
            logs.setOpremake("xAction数据:" + xAction + ",xParam数据:" + xParam + ",apiSign数据:" + apiSign + ",ip地址：" + ip+"，结果：apiSign验证失败");
            logsMapper.insert(logs);
            return this.ajaxDone(-2, "apiSign验证失败", null);
        }
        if (offerset == null || offerset.getState() != 1) {
            Logs logs = new Logs();
            logs.setCreatetime(new Date());
            logs.setOptype(-100);
            logs.setUserid(new Long(UserId));
            logs.setOpobj("2");
            logs.setOpremake("xAction数据:" + xAction + ",xParam数据:" + xParam + ",apiSign数据:" + apiSign + ",ip地址：" + ip+"，结果：没有查询报价的权限");
            logsMapper.insert(logs);
            return this.ajaxDone(-4, "没有查询报价的权限", null);
        }
        JsonObject json1 = (JsonObject) parser.parse(json.get("Value").toString());//获取参数关键词
        String KeyWords = json1.get("keyword").toString().substring(1, (json1.get("keyword").toString().length() - 1));
        String[] arr = KeyWords.split(",");
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
        Date updatetime = null;
        Date now = null;
        try {
            updatetime = sm.parse(sm.format(offerset.getUpdatetime()));//获取最后一次的参数设置信息的修改时间
            now = sm.parse(sm.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (updatetime.getTime() < now.getTime()) {//如果参数设置的最后一次修改时间小于今日时间
            offerset.setSurplussecond(offerset.getRequestsecond());//修改剩余关键词数量等于总数量
            offerset.setUpdatetime(new Date());//修改最后一次修改时间为当前时间
            offersetMapper.updateByPrimaryKeySelective(offerset);
        }
        offerset offerset1 = offersetMapper.selectByUserId(Long.parseLong(UserId));//重新查询已调整的参数设置信息
        List<String> stringList = new ArrayList<>();
        List<String> stringList1 = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            //清除空格
            arr[i] = arr[i].replace("\t", "").replace(" ", "");
            stringList.add(arr[i]);
            stringList1.add(arr[i]);
        }
        if (stringList1.size() > offerset1.getSurplussecond()) {//判断关键词数量大于参数信息的剩余数量
            Logs logs = new Logs();
            logs.setCreatetime(new Date());
            logs.setOptype(-100);
            logs.setUserid(new Long(UserId));
            logs.setOpobj("2");
            logs.setOpremake("xAction数据:" + xAction + ",xParam数据:" + xParam + ",apiSign数据:" + apiSign + ",ip地址：" + ip+"，结果：请求的关键词数量超过总数，剩余关键词数量：" + offerset1.getSurplussecond());
            logsMapper.insert(logs);
            return this.ajaxDone(-3, "请求的关键词数量超过总数，剩余关键词数量：" + offerset1.getSurplussecond(), null);
        }
        List<KeywordToPrice> keywordsPriceList = optimizationToolService.GetPriceList(stringList, offerset1.getRate());

        offerset1.setUpdatetime(new Date());
        offerset1.setSurplussecond(offerset1.getSurplussecond() - stringList1.size());
        offersetMapper.updateByPrimaryKeySelective(offerset1);
        for (KeywordToPrice item : keywordsPriceList
                ) {
            stringList1.remove(item.getKeywords());
        }
        if (!CollectionUtils.isEmpty(stringList1)) {
            for (int j = 0; j < stringList1.size(); j++) {
                KeywordToPrice keywordToPrice = new KeywordToPrice();
                keywordToPrice.setKeywords(stringList1.get(j));
                keywordToPrice.setPricebaidupc(0d);
                keywordToPrice.setPricebaiduwap(0d);
                keywordToPrice.setPricesopc(0d);
                keywordToPrice.setPricesogoupc(0d);
                keywordToPrice.setPricesm(0d);
                keywordsPriceList.add(keywordToPrice);
            }
        }
        Logs logs = new Logs();
        logs.setCreatetime(new Date());
        logs.setOptype(-100);
        logs.setUserid(new Long(UserId));
        logs.setOpobj("2");
        logs.setOpremake("xAction数据:" + xAction + ",xParam数据:" + xParam + ",apiSign数据:" + apiSign + ",ip地址：" + ip+"，结果：成功，剩余关键词数量：" + offerset1.getSurplussecond());
        logsMapper.insert(logs);
        return this.ajaxDone(1, "成功，剩余关键词数" + offerset1.getSurplussecond(), keywordsPriceList);
    }

}
