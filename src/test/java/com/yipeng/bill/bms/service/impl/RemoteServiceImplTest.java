package com.yipeng.bill.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.yipeng.bill.bms.core.utils.DateUtils;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
import com.yipeng.bill.bms.model.Yby;
import com.yipeng.bill.bms.service.RemoteService;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by song on 17/4/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = "classpath:/META-INF/spring/applicationContext-beans.xml")})
public class RemoteServiceImplTest {

    @Autowired
    private RemoteService remoteService;

    @Test
    public void insertYby() throws Exception {
        int agid=100086;
        String action="importkw";
        String md5Key="05FEF02A7833380DC7E3354E9DB37F08";

        //取现在时间
        String dateString = DateUtils.formatDate(new Date(),"yyyyMMddHHmmss");

        //填充实体类
        List<Yby> ybyList=new ArrayList<>();
        //加密sign
        Yby yby=new Yby();
        yby.setKw("上海婚纱");
        yby.setUrl("www.hunsha.com");
        yby.setSe(1);
        yby.setMcpd(1);
        ybyList.add(yby);
        //组包
        StringBuffer sb= new StringBuffer();
        sb.append(dateString);
        sb.append(action);
        sb.append(ybyList);
        sb.append(md5Key);
        sb.append(agid);

        //加密sign
        String sign=null;
        try {
            sign= Md5_UrlEncode.EncoderByMd51(sb.toString()).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //创建JSON
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("agid",agid);
        jsonObject.put("stamp",dateString);
        jsonObject.put("action",action);
        jsonObject.put("args",JSON.toJSONString(ybyList));
        jsonObject.put("sign",sign);


        BASE64Encoder base64Encoder=new BASE64Encoder();
        //String data = base64Encoder.encode(jsonObject.toString().getBytes());
        String data= base64Encoder.encode(jsonObject.toString().getBytes("UTF-8")).replace("+", "%2B");
        Map<String,String> map=new HashMap<>();
        map.put("data",data);
        //String result = remoteService.insertYby(map);
        /*HttpResponse<String> jsonResponse = Unirest.post("http://yhapi.youbangyun.com/api/public/taskapi.aspx")
                .header("Content-type", "application/x-www-form-urlencoded")
                .header("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)")
                .field("data",data)
                .asString();
        String result =jsonResponse.getBody();*/
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        System.out.println("=====================");
        System.out.println(data);
        //System.out.println(result);
        System.out.println("=====================");
    }

}