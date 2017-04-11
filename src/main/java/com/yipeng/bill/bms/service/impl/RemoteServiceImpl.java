package com.yipeng.bill.bms.service.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.yipeng.bill.bms.service.RemoteService;
import com.yipeng.bill.bms.vo.CustomerRankingParam;
import com.yipeng.bill.bms.vo.CustomerRankingResult;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public CustomerRankingResult getCustomerRanking(CustomerRankingParam customerRankingParam)throws IOException, NoSuchAlgorithmException  {

        CustomerRankingResult result = new CustomerRankingResult();
        String aa=getTaskId();
        return result;
    }


    public  String getTaskId()throws ParseException, IOException, NoSuchAlgorithmException, JSONException
    {
        //设置传入参数
        String wAction = "AddSearchTask";
        String[] qkeyword = {"QQ"};
        String[] qurl = {"www.qq.com"};
        int[] timeSet = { 12 };
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("keyword", qkeyword);
        jsonObj.put("url", qurl);
        jsonObj.put("time", System.currentTimeMillis());
        jsonObj.put("timeSet", timeSet);
        jsonObj.put("userId", userId);
        jsonObj.put("businessType", 2006);
        jsonObj.put("searchType", 1010);
        jsonObj.put("searchOnce", true);
        //生成传入参数
        String wParam = jsonObj.toString();
        String wSign = EncoderByMd5(wAction + token + jsonObj.toString());
        Map<String, String> map = new HashMap<String, String>();
        map.put("wAction", wAction);
        map.put("wParam", wParam);
        map.put("wSign", wSign);
        String body = send(url, map,"utf-8");
        return  body;
    }
    /**
     * URL 转码
     *
     * @return String
     */
    public  String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public  String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return  toHexString(md5.digest(str.getBytes("utf-8")));
    }
    private  String toHexString(byte[] b) {
        char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }
    /**
     * 模拟请求
     *
     * @param url       资源地址
     * @param map   参数列表
     * @param encoding  编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public  String send(String url, Map<String,String> map,String encoding) throws ParseException, IOException{
        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
}
