package com.yipeng.bill.bms.task;

import com.yipeng.bill.bms.core.utils.DateUtils;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 鱼在我这里。 on 2017/4/13.
 */
public class NewRankingTask {
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
                        //判断接口是否成功
                        if(customerRankingResult.getMessage().equals("success.")) {
                            JSONArray value = customerRankingResult.getValue();
                            JSONArray value1= value.getJSONArray(0);
                            JSONObject str=value1.getJSONObject(1);
                            Object RankLast=str.get("RankLast");
                            String UpdateTime=str.get("UpdateTime").toString();


                            //字符串中的日期格式
                            DateFormat from_type = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //想要得到的日期显示格式
                            DateFormat to_type   = new SimpleDateFormat("yyyy/MM/dd");
                            //用来做中间转换的Date
                            Date   date= null;

                            //将字符串转换成日期格式
                            try {
                                date = from_type.parse(UpdateTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //将日期格式转换成字符串
                            String str1 = to_type.format(date);
                            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");

                            //时间转化
                            Date now = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                            String  now1=dateFormat.format(now);


                            //判断是否获取到排名
                            if (Integer.parseInt(RankLast.toString())!=0)
                            {
                                if(now1.equals(str1))
                                {
                                    bill.setNewRanking(Integer.parseInt(RankLast.toString()));
                                    billMapper.updateByPrimaryKeySelective(bill);
                                }

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
