package com.yipeng.bill.bms.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.*;

import com.yipeng.bill.bms.domain.UserPower;
import com.yipeng.bill.bms.domain.leaseOverdueTb;
import com.yipeng.bill.bms.domain.noticepublish;
import com.yipeng.bill.bms.domain.offerset;

import com.yipeng.bill.bms.domain.*;

import com.yipeng.bill.bms.service.*;
import com.yipeng.bill.bms.vo.LoginUser;
import com.yipeng.bill.bms.vo.PushMessageDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController extends BaseController {
    @Autowired
    private HomeService homeService;
    @Autowired
    private RemoteService remoteService;
    //@Autowired
    //private AuthorityService authorityService;
    @Autowired
    private sendBoxMapper sendBoxMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private offersetMapper offersetMapper;
    @Autowired
    private orderLeaseMapper orderLeaseMapper;
    @Autowired
    private leaseOverdueTbMapper leaseOverdueTbMapper;
    @Autowired
    private  PushMessageMapper pushMessageMapper;


    @RequestMapping(value = "/index")
    public String index(ModelMap model) throws Exception {

        Map<String, Object> bms = new HashMap<>();
        LoginUser user = this.getCurrentAccount();
        Long UnReadNum = sendBoxMapper.selectUnreadCount(user.getId().toString());//反馈未读数量
        Long SendUnReadNum = messageService.getReMailNum(user, 1);//发件箱未读数量
        Long InUnReadNum = messageService.getInReMailNum(user, 1);
        offerset offerset = offersetMapper.selectByUserId(user.getId());
        if (offerset != null) {
            bms.put("offerstate", offerset.getState());
            bms.put("leasepower", offerset.getLeasepower());
        } else {
            bms.put("offerstate", 0);
            bms.put("leasepower", 0);
        }
        if (user.hasRole("AGENT")) {
            Map<String, Object> map = new HashMap<>();
            map.put("reserveId", user.getId());
            long num = leaseOverdueTbMapper.selectByReserveIdCount(map);
            bms.put("leaseOverdue", num);
        }
        if (user.hasRole("CUSTOMER")) {//客户
            offerset offerset1 = offersetMapper.selectByUserId(user.getId());
            if (offerset1 != null) {
                bms.put("leasepowercustomer", offerset1.getState());
            } else {
                bms.put("leasepowercustomer", 0);
            }
        }
        if (user.hasRole("COMMISSIONER"))//消息
        {
            Map<String,Object> sqlMap=new HashMap<>();
            sqlMap.put("userId",user.getId());
            List<PushMessage> pushMessageList=pushMessageMapper.selectByUserId(sqlMap);
            if(!CollectionUtils.isEmpty(pushMessageList))
            {
                SimpleDateFormat sm=new SimpleDateFormat("yyy-MM-dd");
                String today=sm.format(new Date());
                List<PushMessageDetails> pushMessageDetailsList=new ArrayList<>();
                for (PushMessage item:pushMessageList
                     ) {
                    PushMessageDetails pushMessageDetails=new PushMessageDetails();
                    pushMessageDetails.setId(item.getId());
                    pushMessageDetails.setReceiveuserid(item.getReceiveuserid());
                    pushMessageDetails.setAlias(item.getAlias());
                    pushMessageDetails.setTag(item.getTag());
                    pushMessageDetails.setTypev(item.getTypev());
                    pushMessageDetails.setModal(item.getModal());
                    pushMessageDetails.setTitle(item.getTitle());
                    pushMessageDetails.setContent(item.getContent());
                    pushMessageDetails.setExtras(item.getExtras());
                    pushMessageDetails.setFlag(item.getFlag());
                    pushMessageDetails.setRetries(item.getRetries());
                    if(today.equals(sm.format(item.getCreatetime())))
                    {
                        pushMessageDetails.setCreatetime("今天");
                    }
                    else
                    {
                        pushMessageDetails.setCreatetime(sm.format(item.getCreatetime()));
                    }
                    pushMessageDetails.setSendtime(item.getSendtime());
                    pushMessageDetailsList.add(pushMessageDetails);
                }
                bms.put("pushMessageList", pushMessageDetailsList);
            }

        }
        bms.put("UnReadNum", UnReadNum);
        bms.put("SendUnReadNum", SendUnReadNum);
        bms.put("InUnReadNum", InUnReadNum);
        bms.put("user", user);

        //bms.put("bmsNavigationList", authorityService.queryBmsNavByUserType(NumberUtils.toInt(account.getLoginUserType())));
        model.addAttribute("bmsModel", bms);
        return "index";
    }

	/*@RequestMapping("/demo.do")
    public String demo() {
		return "demo";
	}*/

    @RequestMapping(value = "/home")
    public String home(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = new HashMap<>();
        /*Map<String, Object> bms=new HashMap<>();*/
        List<noticepublish> inbox = homeService.getInBox(loginUser);
        bms.put("user", loginUser);
        bms.put("inbox", inbox);
        model.addAttribute("bmsModel", bms);
        return "/home/home";
    }


    @RequestMapping(value = "/homeDetails")
    @ResponseBody
    public Map<String, Object> homeDetails(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.homeDetails(loginUser);
        model.addAttribute("bmsModel", bms);
        return bms;
    }

    //客户数
    @RequestMapping(value = "/userCount")
    @ResponseBody
    public Map<String, Object> userCount(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.userCount(loginUser);
        model.addAttribute("bmsModel", bms);
        return bms;
    }

    //余额
    @RequestMapping(value = "/balance")
    @ResponseBody
    public Map<String, Object> balance(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.balance(loginUser);
        model.addAttribute("bmsModel", bms);
        return bms;
    }

    //月总消费
    @RequestMapping(value = "/MonthConsumption")
    @ResponseBody
    public Map<String, Object> MonthConsumption(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.MonthConsumption(loginUser);
        model.addAttribute("bmsModel", bms);
        return bms;
    }

    //本日消费
    @RequestMapping(value = "/DayConsumption")
    @ResponseBody
    public Map<String, Object> DayConsumption(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.DayConsumption(loginUser);
        model.addAttribute("bmsModel", bms);
        return bms;
    }

    //当前任务数
    @RequestMapping(value = "/billCount")
    @ResponseBody
    public Map<String, Object> billCount(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.billCount(loginUser);
        model.addAttribute("bmsModel", bms);
        return bms;
    }

    //累计任务数
    @RequestMapping(value = "/AllbillCount")
    @ResponseBody
    public Map<String, Object> AllbillCount(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.AllbillCount(loginUser);
        model.addAttribute("bmsModel", bms);
        return bms;
    }

    //达标数
    @RequestMapping(value = "/standardSum")
    @ResponseBody
    public Map<String, Object> standardSum(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.standardSum(loginUser);
        model.addAttribute("bmsModel", bms);
        return bms;
    }

    //百度
    @RequestMapping(value = "/baiduCompleteness")
    @ResponseBody
    public Map<String, Object> baiduCompleteness(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.baiduCompleteness(loginUser);
        model.addAttribute("bmsModel", bms);
        return bms;
    }

    //手机百度
    @RequestMapping(value = "/baiduWapCompleteness")
    @ResponseBody
    public Map<String, Object> baiduWapCompleteness(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.baiduWapCompleteness(loginUser);
        model.addAttribute("bmsModel", bms);
        return bms;
    }

    //360
    @RequestMapping(value = "/sanliulingCompleteness")
    @ResponseBody
    public Map<String, Object> sanliulingCompleteness(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.sanliulingCompleteness(loginUser);

        return bms;
    }

    //搜狗
    @RequestMapping(value = "/sougouCompleteness")
    @ResponseBody
    public Map<String, Object> sougouCompleteness(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.sougouCompleteness(loginUser);
        return bms;
    }

    //神马
    @RequestMapping(value = "/shenmaCompleteness")
    @ResponseBody
    public Map<String, Object> shenmaCompleteness(ModelMap model) throws Exception {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> bms = homeService.shenmaCompleteness(loginUser);

        return bms;
    }

    //获取信息个数
    @RequestMapping(value = "/pushMessageListCount")
    @ResponseBody
    public int pushMessageListCount(ModelMap model) throws Exception {
        LoginUser loginUser=this.getCurrentAccount();
        Map<String,Object> sqlMap=new HashMap<>();
        sqlMap.put("userId",loginUser.getId());
        List<PushMessage> pushMessageList=pushMessageMapper.selectByUserId(sqlMap);

        if (!CollectionUtils.isEmpty(pushMessageList))
        {
            int num=pushMessageList.size();
            return num;
        }
        else
        {
            return 0;
        }

    }

    //获取信息个数
    @RequestMapping(value = "/confirmMessage")
    @ResponseBody
    public ResultMessage confirmMessage(String messageId) throws Exception {
        if(messageId!=null)
        {
            PushMessage pushMessage=pushMessageMapper.selectByPrimaryKey(new Long(messageId));
            if(pushMessage!=null)
            {
                pushMessage.setFlag(1);
                int a=   pushMessageMapper.updateByPrimaryKey(pushMessage);
                if(a>0)
                {
                    return this.ajaxDoneSuccess("操作成功");
                }
                else
                {
                    return this.ajaxDoneSuccess("操作失败");
                }
            }
            else {

                return this.ajaxDoneSuccess("操作失败");
            }
        }
        else
        {
            return ajaxDoneError("未知错误，请联系管理员！");
        }


    }
}
