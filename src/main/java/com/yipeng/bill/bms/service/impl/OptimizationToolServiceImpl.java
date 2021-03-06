package com.yipeng.bill.bms.service.impl;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.model.Define;
import com.yipeng.bill.bms.model.KeywordToPrice;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
import com.yipeng.bill.bms.service.OptimizationToolService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 鱼在我这里。 on 2017/5/25.
 */
@Service
public class OptimizationToolServiceImpl implements OptimizationToolService {

    @Autowired
    private ForbiddenWordsMapper forbiddenWordsMapper;
    @Autowired
    public KeywordsPriceMapper keywordsPriceMapper;

    @Autowired
    private RemoteServiceImpl remoteService;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private offersetMapper offersetMapper;
    @Autowired
    private UserPowerMapper userPowerMapper;
    Md5_UrlEncode md5_urlEncode = new Md5_UrlEncode();

    @Override
    public List<KeywordsPrice> forbiddenWordsList(String keywords, double rote) {
        if (keywords == "") {
            return null;
        }
        //获取所输入的关键字
        String[] arr = keywords.split("\n");
        //String where = "";
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            //清除空格
            arr[i] = arr[i].replace("\t", "").replace(" ", "");
            String aa = arr[i];
            //where += " or words LIKE '" + arr[i] + "'";
            list.add(arr[i]);
        }
        //去除重复关键词
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        Map<String, Object> params = new HashMap<>();
        params.put("list", list);
        List<ForbiddenWords> forbiddenWordsList = forbiddenWordsMapper.selectByKeywords(params);
        for (ForbiddenWords item : forbiddenWordsList
                ) {
            list.remove(item.getWords());//移除违禁词
        }
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Map<String, Object> params2 = new HashMap<>();
        params2.put("list", list);
        params2.put("rote", rote);
        List<KeywordsPrice> keywordsprices = keywordsPriceMapper.selectByword(params2);//查询本地数据
        //if (!CollectionUtils.isEmpty(keywordsprices)) {
        for (KeywordsPrice item : keywordsprices
                ) {
            list.remove(item.getKeywords());
        }
        //是否还存在可调用接口集合
        if (!CollectionUtils.isEmpty(list)) {
            //调用接口
            long time = (long) (System.currentTimeMillis() / 1000);
            String action = "AddSearchTask";
            String keyword = "";
            for (String item : list
                    ) {
                keyword += "\"" + item + "\",";
            }
            //去掉最后面的逗号
            keyword = keyword.substring(0, keyword.length() - 1);
            String data = "{\"userId\":" + Define.userId + ",\"time\":" + time + ",\"businessType\":1008,\"keyword\":[" + keyword + "]}";
            String sign = "";
            try {
                //sign验证加密
                sign = md5_urlEncode.EncoderByMd5(action + Define.token + data).toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //调用接口的参数
            Map<String, String> priceMap = new HashMap<>();
            priceMap.put("wAction", action);
            priceMap.put("wSign", sign);
            priceMap.put("wParam", data);
            //调用接口方法
            String result = remoteService.getPriceApiId(priceMap);
            JSONObject myJsonObject = new JSONObject(result);
            JSONArray array = myJsonObject.getJSONArray("xValue");
            for (int i = 0; i < array.length(); i++) {
                KeywordsPrice keywordsPrice = new KeywordsPrice();
                keywordsPrice.setTaskid(Integer.parseInt(array.getJSONArray(i).get(0).toString()));
                keywordsPrice.setKeywords(list.get(i));
                keywordsprices.add(keywordsPrice);
                keywordsPriceMapper.insert(keywordsPrice);
            }

        }
        return keywordsprices;
    }

    @Override
    public Boolean LoopAllKeywords() {
        while (true) {
            List<String> keywords = billMapper.selectAllKeywords();
            if (CollectionUtils.isEmpty(keywords)) {
                break;
            }
            try {
                Thread thread = Thread.currentThread();
                thread.sleep(15000);//暂停30秒
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            //调用接口
            long time = (long) (System.currentTimeMillis() / 1000);
            String action = "AddSearchTask";
            String keyword = "";
            for (String item : keywords
                    ) {
                keyword += "\"" + item + "\",";
            }
            //去掉最后面的逗号
            keyword = keyword.substring(0, keyword.length() - 1);
            String data = "{\"userId\":" + Define.userId + ",\"time\":" + time + ",\"businessType\":1008,\"keyword\":[" + keyword + "]}";
            String sign = "";
            try {
                //sign验证加密
                sign = md5_urlEncode.EncoderByMd5(action + Define.token + data).toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return false;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }
            //调用接口的参数
            Map<String, String> priceMap = new HashMap<>();
            priceMap.put("wAction", action);
            priceMap.put("wSign", sign);
            priceMap.put("wParam", data);
            //调用接口方法
            String result = remoteService.getPriceApiId(priceMap);
            JSONObject myJsonObject = new JSONObject(result);
            JSONArray array = myJsonObject.getJSONArray("xValue");
            for (int i = 0; i < array.length(); i++) {
                KeywordsPrice keywordsPrice = new KeywordsPrice();
                keywordsPrice.setTaskid(Integer.parseInt(array.getJSONArray(i).get(0).toString()));
                keywordsPrice.setKeywords(keywords.get(i));
                int num = keywordsPriceMapper.insert(keywordsPrice);
                if (num == 0)
                    return false;
            }
        }
        return true;
    }

    @Override
    public Boolean UpdateRote(LoginUser loginUser, double rote) {
        offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
        offerset.setRate(rote);
        int num = offersetMapper.updateByPrimaryKeySelective(offerset);
        return num > 0;
    }

    @Override
    public String UpdateToken(LoginUser loginUser) {
        int max = 999999;
        int min = 100000;
        Random random = new Random();
        int sui = random.nextInt(max) % (max - min + 1) + min;
        String keypt = null;
        try {
            keypt = md5_urlEncode.EncoderByMd5(sui + loginUser.getUserName());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        offerset offerset = offersetMapper.selectByUserId(loginUser.getId());
        offerset.setTokenid(keypt);
        offersetMapper.updateByPrimaryKeySelective(offerset);
        return keypt;
    }

    @Override
    public Boolean setOffer(int type, String keywordNum, double rote, String dataUser) {
        offerset offerset = offersetMapper.selectByUserId(Long.parseLong(dataUser));
        if (offerset == null) {
            int max = 999999;
            int min = 100000;
            Random random = new Random();
            int sui = random.nextInt(max) % (max - min + 1) + min;
            String keypt = null;
            try {
                keypt = md5_urlEncode.EncoderByMd5(sui + dataUser);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            offerset offerset1 = new offerset();
            offerset1.setUserid(Long.parseLong(dataUser));
            offerset1.setUpdatetime(new Date());
            offerset1.setState(type);
            offerset1.setTokenid(keypt);
            offerset1.setSurplussecond(type == 0 ? 0 : Integer.parseInt(keywordNum));
            offerset1.setRate(rote);
            offerset1.setCreatetime(new Date());
            offerset1.setRequestsecond(type == 0 ? 0 : Integer.parseInt(keywordNum));
            return offersetMapper.insert(offerset1) > 0;
        }
        if (type == 0) {
            offerset.setState(0);
            int num = offersetMapper.updateByPrimaryKeySelective(offerset);
            return num > 0;
        } else {
            offerset.setState(1);
            offerset.setUpdatetime(new Date());
            offerset.setRequestsecond(Integer.parseInt(keywordNum));
            offerset.setSurplussecond(Integer.parseInt(keywordNum));
            offerset.setRate(rote);
            int num = offersetMapper.updateByPrimaryKeySelective(offerset);
            return num > 0;
        }
    }

    @Override
    @Transactional
    public Boolean setWebsitePower(Map<String, Object> map) {
        String[] arr = (String[]) map.get("arr");
        int type = Integer.parseInt(map.get("type").toString());
        int num = 0;
        for (int i = 0; i < arr.length; i++) {
            offerset offerset = offersetMapper.selectByUserId(Long.parseLong(arr[i]));
            if (offerset == null) {
                int max = 999999;
                int min = 100000;
                Random random = new Random();
                int sui = random.nextInt(max) % (max - min + 1) + min;
                String keypt = null;
                try {
                    keypt = md5_urlEncode.EncoderByMd5(sui + arr[i]);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                offerset offerset1 = new offerset();
                offerset1.setUserid(Long.parseLong(arr[i]));
                offerset1.setUpdatetime(new Date());
                offerset1.setState(0);
                offerset1.setTokenid(keypt);
                offerset1.setSurplussecond(0);
                offerset1.setRate(1d);
                offerset1.setCreatetime(new Date());
                offerset1.setRequestsecond(0);
                offerset1.setLeasepower(type);
                num += offersetMapper.insert(offerset1);
            } else {
                offerset.setLeasepower(type);
                num += offersetMapper.updateByPrimaryKeySelective(offerset);
            }
            UserPower userPower = userPowerMapper.selectByUserId(Long.parseLong(arr[i]));
            if (userPower == null) {
                UserPower userPower1 = new UserPower();
                userPower1.setUserid(Long.parseLong(arr[i]));
                userPower1.setPowerid(1);
                int num1 = userPowerMapper.insert(userPower1);
                if (num1 == 0) {
                    throw new RuntimeException();
                }
            }
        }
        if (num == arr.length) {
            return true;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public Boolean setAgentWebsitePower(Map<String, Object> map) {
        String userId = map.get("arr").toString();
        int type = Integer.parseInt(map.get("type").toString());
        offerset offerset = offersetMapper.selectByUserId(Long.parseLong(userId));
        if (offerset == null) {
            int max = 999999;
            int min = 100000;
            Random random = new Random();
            int sui = random.nextInt(max) % (max - min + 1) + min;
            String keypt = null;
            try {
                keypt = md5_urlEncode.EncoderByMd5(sui + userId);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            offerset offerset1 = new offerset();
            offerset1.setUserid(Long.parseLong(userId));
            offerset1.setUpdatetime(new Date());
            offerset1.setState(0);
            offerset1.setTokenid(keypt);
            offerset1.setSurplussecond(0);
            offerset1.setRate(1d);
            offerset1.setCreatetime(new Date());
            offerset1.setRequestsecond(0);
            offerset1.setLeasepower(type);
            int num = offersetMapper.insert(offerset1);
            return num > 0;
        } else {
            offerset.setLeasepower(type);
            int num = offersetMapper.updateByPrimaryKeySelective(offerset);
            return num > 0;
        }
    }

    @Override
    public Map<String, Object> GetKeywordsList(Map<String, Object> map) {
        long total = keywordsPriceMapper.selectAllKeywordsCount(map);
        List<Map<String, Object>> mapList = keywordsPriceMapper.selectAllKeywords(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("total", total);
        map1.put("rows", mapList);
        return map1;
    }

    @Override
    @Transactional
    public int UpToTotal(long dataUser) {
        offerset offerset = offersetMapper.selectByUserId(dataUser);
        if (offerset == null || offerset.getLeasepower() == 0) {
            return -2;//未开通网租
        }
        UserPower userPower = userPowerMapper.selectByUserId(dataUser);
        if (userPower == null) {
            return -2;
        }
        int num = userPowerMapper.updateByPowerId();
        userPower.setPowerid(0);
        userPowerMapper.updateByPrimaryKeySelective(userPower);
        return 1;
    }


    @Override
    public List<KeywordToPrice> GetPriceList(List<String> list, double rote) {
        /*String where = "";
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            //清除空格
            arr[i] = arr[i].replace("\t", "").replace(" ", "");
            where += " or words LIKE '" + arr[i] + "'";
            list.add(arr[i]);
        }*/
        //去除重复关键词
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        Map<String, Object> params = new HashMap<>();
        params.put("list", list);
        List<ForbiddenWords> forbiddenWordsList = forbiddenWordsMapper.selectByKeywords(params);
        for (ForbiddenWords item : forbiddenWordsList
                ) {
            list.remove(item.getWords());//移除违禁词
        }
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Map<String, Object> params2 = new HashMap<>();
        params2.put("list", list);
        params2.put("rote", rote);
        List<KeywordToPrice> keywordsprices = keywordsPriceMapper.selectBywordToPrice(params2);//查询本地数据
        List<KeywordToPrice> keywordToPrices = keywordsPriceMapper.selectBywordToPriceHave(params2);//查询本地数据
        //if (!CollectionUtils.isEmpty(keywordsprices)) {
        for (KeywordToPrice item : keywordsprices
                ) {
            list.remove(item.getKeywords());
        }
        //是否还存在可调用接口集合
        if (!CollectionUtils.isEmpty(list)) {
            //调用接口
            long time = (long) (System.currentTimeMillis() / 1000);
            String action = "AddSearchTask";
            String keyword = "";
            for (String item : list
                    ) {
                keyword += "\"" + item + "\",";
            }
            //去掉最后面的逗号
            keyword = keyword.substring(0, keyword.length() - 1);
            String data = "{\"userId\":" + Define.userId + ",\"time\":" + time + ",\"businessType\":1008,\"keyword\":[" + keyword + "]}";
            String sign = "";
            try {
                //sign验证加密
                sign = md5_urlEncode.EncoderByMd5(action + Define.token + data).toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //调用接口的参数
            Map<String, String> priceMap = new HashMap<>();
            priceMap.put("wAction", action);
            priceMap.put("wSign", sign);
            priceMap.put("wParam", data);
            //调用接口方法
            String result = remoteService.getPriceApiId(priceMap);
            JSONObject myJsonObject = new JSONObject(result);
            JSONArray array = myJsonObject.getJSONArray("xValue");
            for (int i = 0; i < array.length(); i++) {
                KeywordsPrice keywordsPrice = new KeywordsPrice();
                keywordsPrice.setTaskid(Integer.parseInt(array.getJSONArray(i).get(0).toString()));
                keywordsPrice.setKeywords(list.get(i));
                keywordsPriceMapper.insert(keywordsPrice);
            }

        }
        return keywordToPrices;
    }

}
