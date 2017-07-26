package com.yipeng.bill.bms.web;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yipeng.bill.bms.dao.KeywordsPriceMapper;
import com.yipeng.bill.bms.dao.LogsMapper;
import com.yipeng.bill.bms.domain.Logs;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
import com.yipeng.bill.bms.service.JedisClientService;
import com.yipeng.bill.bms.service.RankingUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/5/4.
 */
@Controller
@RequestMapping(value = "/ybyNotify")
public class ybyNotifyController extends BaseController {

    @Autowired
    private RankingUpdateService rankingUpdateService;
    @Autowired
    private LogsMapper logsMapper;
    @Autowired
    private KeywordsPriceMapper keywordsPriceMapper;
    @Autowired
    private JedisClientService jedisClientService;


    /**
     * 获取优帮云通知
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getYbyNotify", method = RequestMethod.POST)
    public String getYbyNotify(String xAction, String xParam, String xSign) {


        Md5_UrlEncode md5_urlEncode = new Md5_UrlEncode();
        //api编号
        String apiToken = "91A684A075E4EEFB359C08059B9677F5";
        String wSign = null;
        //加密
        try {
            wSign = md5_urlEncode.EncoderByMd5(xAction + apiToken + xParam);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //验证签名
        if (xSign.equals(wSign)) {
            JsonParser parser = new JsonParser();//创建Json解析器
            JsonObject json = (JsonObject) parser.parse(xParam);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JsonObject json1 = (JsonObject) parser.parse(json.get("Value").toString());
            //获取数据
            String BusinessType = json.get("BusinessType").toString();//任务类型
            if ("1008".equals(BusinessType)) {
                //报价
                int a = rankingUpdateService.updateKeywords(json1);
                if (a == 1) {

                    return "1";
                } else {
                    return "1";
                }

            } else {
                //排名通知
                String TaskId = json1.get("TaskId").toString();//任务编号
                String RankFirst = json1.get("RankFirst").toString();//初排
                String RankLast = json1.get("RankLast").toString();//新排
                if (RankFirst == null || "".equals(RankFirst) || RankLast == null || "".equals(RankLast)) {
                    return "1";

                }

                jedisClientService.rpush("xParam",xParam);
                 return "1";

               /* int a = rankingUpdateService.updateRanking(Integer.parseInt(TaskId), Integer.parseInt(RankLast), Integer.parseInt(RankFirst));
                if (a > 0) {
                    Logs logs = new Logs();
                    logs.setCreatetime(new Date());
                    logs.setOptype(1);
                    logs.setUserid(new Long(1));
                    logs.setOpobj("2");
                    logs.setOpremake("更新成功！xParam数据:" + xParam);
                    logsMapper.insert(logs);
                    return "1";

                } else if (a == 0) {
                    Logs logs = new Logs();
                    logs.setCreatetime(new Date());
                    logs.setOptype(999);
                    logs.setUserid(new Long(1));
                    logs.setOpobj("2");
                    logs.setOpremake("更新失败！xParam数据:" + xParam);
                    logsMapper.insert(logs);
                    return "1";
                } else {
                    return "数据更新失败";
                }*/
            }


        } else {
            return "签名验证失败！";
        }


    }

}
