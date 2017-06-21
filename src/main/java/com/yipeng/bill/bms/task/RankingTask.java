package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.dao.BillMapper;
import com.yipeng.bill.bms.domain.Bill;
import com.yipeng.bill.bms.model.Define;
import com.yipeng.bill.bms.model.Md5_UrlEncode;
import com.yipeng.bill.bms.service.RemoteService;
import com.yipeng.bill.bms.vo.CustomerRankingParam;
import com.yipeng.bill.bms.vo.CustomerRankingResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 17/4/11.
 * 排名更新任务
 */
public class RankingTask {

    @Autowired
    private RemoteService remoteService;
    @Autowired
    private BillMapper billMapper;

    public void execute() throws IOException, NoSuchAlgorithmException {
        //1.获取自己数据库数据（需要同步排名的记录）

        //2.根据数据作为参数 customerRankingParam，httpclient接口


        int offset = 0;
        int limit = 50; //每次查询50条
        Md5_UrlEncode md5_urlEncode=new Md5_UrlEncode();
        while (true) {

            //1.查询有效的计费单Bill
            Map<String, Object> params = new HashMap<>();
            params.put("state", 2);
            //初始排名
            params.put("firstRanking", 200);
            params.put("offset", offset);
            params.put("limit", limit);
            //任务编号
            int businessType = 2006;
            //任务类型
            String wAction = "GetSearchTaskResult";
            List<Bill> billList = billMapper.selectBill(params);
            if (!CollectionUtils.isEmpty(billList)) {
                for (Bill bill : billList) {
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("businessType", businessType);
                     int[] taskId={bill.getWebAppId()};
                    jsonObj.put("taskId", taskId);
                    jsonObj.put("time", System.currentTimeMillis());
                    jsonObj.put("userId", Define.userId);
                    String wParam = jsonObj.toString();
                    String wSign = null;
                    try {

                        wSign = md5_urlEncode.EncoderByMd5(wAction + Define.token + jsonObj.toString());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    CustomerRankingParam customerRankingParam = new CustomerRankingParam();
                    customerRankingParam.setwAction(wAction);
                    customerRankingParam.setwParam(wParam);
                    customerRankingParam.setwSign(wSign);
                    CustomerRankingResult customerRankingResult=  remoteService.getCustomerRanking(customerRankingParam);
                    if(customerRankingResult!=null)
                    {
                        if(customerRankingResult.getMessage().equals("success.")) {
                            JSONArray value = customerRankingResult.getValue();
                            JSONArray value1= value.getJSONArray(0);
                            JSONObject str=value1.getJSONObject(1);
                            Object RankFirst=str.get("RankFirst");
                            Object RankLast=str.get("RankLast");
                            if (Integer.parseInt(RankFirst.toString())!=0)
                            {
                                bill.setFirstRanking(Integer.parseInt(RankFirst.toString()));
                                bill.setNewRanking(Integer.parseInt(RankLast.toString()));
                                billMapper.updateByPrimaryKeySelective(bill);
                            }
                        }
                    }

                }
            }
            if (CollectionUtils.isEmpty(billList) || billList.size()<limit) { //查询为空或者不足limit条,说明已查询结束
                break;
            }

            offset += limit; //查询下一页

        }



        //CustomerRankingResult customerRankingResult = remoteService.getCustomerRanking(customerRankingParam);

        //3.根据别人返回的数据 customerRankingResult ，处理自己的业务（更新排名等操作）

    }
}
