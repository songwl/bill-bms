package com.yipeng.bill.bms.model;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
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
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getTaskId {
    //请求查询网关Url
    private static String url="http://api.youbangyun.com/api/customerapi.aspx";
    //平台token
    private static String token = "72F2D590D16463A5B6C1D5C0E37056BD";
    //平台uid
    private static String userId = "104982";


}

