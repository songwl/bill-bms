package com.yipeng.bill.bms.web;

import com.mashape.unirest.request.HttpRequest;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.dao.messageReplyMapper;
import com.yipeng.bill.bms.dao.sendBoxMapper;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.MessageService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/Message")
public class MessageController extends BaseController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private sendBoxMapper sendBoxMapper;
    @Autowired
    private messageReplyMapper messageReplyMapper;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/WriteMail")
    public String WriteMail(ModelMap modelMap) {
        List<Role> bumenlist = messageService.getBumen();
        modelMap.put("bumenlist", bumenlist);
        return "/Message/WriteMail";
    }


    @RequestMapping(value = "/SendBox")
    public String SendBox(ModelMap modelMap) {
        return "/Message/SendBox";
    }
    @RequestMapping(value = "/MessageInfo")
    public String MessageInfo(ModelMap modelMap) {
        return "/Message/MessageInfo";
    }

    @RequestMapping(value = "/ReadMail")
    public String ReadMail(ModelMap modelMap, Long MailId, Long StateId) {
        sendBox sendBox = messageService.getContent(MailId);
        modelMap.put("sendBox", sendBox);
        return "/Message/ReadMail";
    }

    @RequestMapping(value = "/NoticeSearch")
    public String NoticeSearch(ModelMap modelMap) {
        LoginUser loginUser=this.getCurrentAccount();
        modelMap.put("loginUser",loginUser);
        return "/Message/NoticeSearch";
    }

    @RequestMapping(value = "/ReadNoticeContent")
    public String ReadNotice(ModelMap modelMap, Long NoticeId) {
        noticepublish noticepublish = messageService.getNoticeContent(NoticeId);
        modelMap.put("noticepublish", noticepublish);
        return "/Message/ReadNoticeContent";
    }

    @RequestMapping(value = "/SendNotice")
    public String SendNotice(ModelMap modelMap) {
        List<Role> bumenlist = messageService.getBumen();
        modelMap.put("bumenlist", bumenlist);
        return "/Message/SendNotice";
    }
    @RequestMapping(value = "/SendFeedback")
    public String SendFeedback(ModelMap modelMap) {
        return "/Message/SendFeedback";
    }

    @RequestMapping(value = "/FeedbackSearch")
    public String FeedbackSearch(ModelMap modelMap) {
        LoginUser loginUser=this.getCurrentAccount();
        modelMap.put("loginUser", loginUser);
        return "/Message/FeedbackSearch";
    }

    @RequestMapping(value = "/ReadFeedback")
    public String ReadFeedback(ModelMap modelMap, Long FeedbackId) {
        LoginUser loginUser=this.getCurrentAccount();
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(FeedbackId);
        sendBox.setDealtstate(1);
        sendBoxMapper.updateByPrimaryKeySelective(sendBox);
        List<messageReply> messageReplyList = messageReplyMapper.selectByMessageId(FeedbackId);
        String sendUserName= userMapper.selectByPrimaryKey(Long.parseLong(sendBox.getSenduserid())).getUserName();
        modelMap.put("sendBox", sendBox);
        modelMap.put("messageReplyList", messageReplyList);
        modelMap.put("loginUser", loginUser.getId().toString());
        modelMap.put("sendUserName", sendUserName);
        return "/Message/ReadFeedback";
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public List<User> getUser(String deparment) {
        List<User> userslist = messageService.getUsers(deparment);
        return userslist;
    }

    //发信息
    @RequestMapping(value = "/SendMail", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage SendMail(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.SendMail(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    //发公告
    @RequestMapping(value = "/SendNotice", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage SendNotice(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.SendNotice(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    @RequestMapping(value = "/GetSendBox")
    @ResponseBody
    public Map<String, Object> GetSendBox(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        Map<String, Object> modelMap = messageService.GetSendBox(params, loginUser);

        return modelMap;
    }

    @RequestMapping(value = "/GetNoticeSearch")
    @ResponseBody
    public Map<String, Object> GetNoticeSearch(int limit, int offset,String SearchContent, int type) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
            //0发送 1接收
            Map<String, Object> modelMap = messageService.GetSendOrReciveNotice(params, loginUser,SearchContent,type);
            return modelMap;
    }

    @RequestMapping(value = "/GoOperation")
    @ResponseBody
    public ResultMessage GoOperation(HttpServletRequest sendBoxList, int type) {
        Map<String, String[]> param = sendBoxList.getParameterMap();
        int len = (param.size() - 1) / 10;
        String[] idarr = new String[len];
        for (int i = 0; i < len; i++) {
            idarr[i] = param.get("data[" + i + "][id]")[0].toString();
        }
        Boolean flag = messageService.updateRead(idarr, type);
        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    @RequestMapping(value = "/GoOperationSingle")
    @ResponseBody
    public ResultMessage GoOperationSingle(Long id, int type) {
        Boolean flag = messageService.updateReadSingle(id, type);
        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    @RequestMapping(value = "/ReMailNum")
    @ResponseBody
    public ResultMessage ReMailNum() {
        LoginUser loginUser = this.getCurrentAccount();
        Long sum = messageService.getReMailNum(loginUser, 0);
        return this.ajaxDoneSuccess(sum.toString());
    }

    @RequestMapping(value = "/SendMailAllNum")
    @ResponseBody
    public ResultMessage SendMailAllNum() {
        LoginUser loginUser = this.getCurrentAccount();
        Long sum = messageService.getReMailNum(loginUser, 1);
        return this.ajaxDoneSuccess(sum.toString());
    }
    @RequestMapping(value = "/SubmitFeedback")
    @ResponseBody
    public ResultMessage SubmitFeedback(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.SubmitFeedback(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }
    @RequestMapping(value = "/GetFeedbackSearch")
    @ResponseBody
    public Map<String, Object> GetFeedbackSearch(int limit, int offset,String SearchContent, int type) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        //0发送 1接收
        Map<String, Object> modelMap = messageService.GetSendOrReciveFeedback(params, loginUser,SearchContent,type);
        return modelMap;
    }
    //消息回复
    @RequestMapping(value = "/replySubmit", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage replySubmit(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.replySubmit(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }
}
