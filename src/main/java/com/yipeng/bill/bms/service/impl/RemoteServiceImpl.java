package com.yipeng.bill.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
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
import org.json.JSONArray;
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

    Md5_UrlEncode md5_urlEncode=new Md5_UrlEncode();
    @Override
    public CustomerRankingResult getCustomerRanking(CustomerRankingParam customerRankingParam)throws IOException, NoSuchAlgorithmException  {
         //返回对象
        CustomerRankingResult result = new CustomerRankingResult();
        //请求返回
        String json=getTaskId(customerRankingParam);
        //转换返回数据
        JSONObject myJsonObject = new JSONObject(json);
        JSONArray  value =myJsonObject.getJSONArray("xValue") ;
        result.setCode(Integer.parseInt(myJsonObject.get("xCode").toString()));
        result.setMessage(myJsonObject.get("xMessage").toString());
        result.setValue(value);

        return result;
    }
   //导入优帮云订单
    @Override
    public String insertYby(Map<String, String> params) {
        String body = null;
        try {
            body = send("http://yhapi.youbangyun.com/api/public/taskapi.aspx", params,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }


    // 获取TASKID
    public  String getTaskId(CustomerRankingParam params )throws ParseException, IOException, NoSuchAlgorithmException, JSONException
    {

        Map<String, String> map = new HashMap<String, String>();
        map.put("wAction", params.getwAction());
        map.put("wParam", params.getwParam());
        map.put("wSign", params.getwSign());
        String body = send(url, map,"utf-8");
        return  body;
    }

    /**
     * 模拟请求
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
