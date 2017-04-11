package com.yipeng.bill.bms.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.yipeng.bill.bms.service.RemoteService;
import com.yipeng.bill.bms.vo.CustomerRankingParam;
import com.yipeng.bill.bms.vo.CustomerRankingResult;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by song on 17/4/11.
 */
@Service
public class RemoteServiceImpl implements RemoteService {

    //请求查询网关Url
    private static String url="http://api.youbangyun.com/api/customerapi.aspx";
    //平台token
    private static String token = "72F2D590D16463A5B6C1D5C0E37056BD";
    //平台uid
    private static String userId = "104982";

    @Override
    public CustomerRankingResult getCustomerRanking(CustomerRankingParam customerRankingParam) {
        //1.根据参数请求
        String wAction = "AddSearchTask";
        String[] qkeyword = {"QQ"};
        String[] qurl = {"www.qq.com"};
        int[] timeSet = { 12 };

        JSONObject jsonObject = null;
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(url)
                    .field("keyword", qkeyword)
                    .field("url", qurl)
                    .field("timeSet", timeSet)
                    .field("userId", userId)
                    .field("businessType", 2006)
                    .field("searchType", 1010)
                    .field("searchOnce", true)
                    .asJson();

            //logger.debug("输出结果:"+jsonResponse.getBody().toString());

            JsonNode jsonNode = jsonResponse.getBody();
            jsonObject  = jsonNode.getObject();
        }catch (UnirestException ue) {
            ue.printStackTrace();
        }
        //2.获取结果处理
        //把jsonObject转出CustomerRankingResult
        CustomerRankingResult result = new CustomerRankingResult();

        return result;
    }
}
