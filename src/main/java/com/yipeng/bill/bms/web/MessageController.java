package com.yipeng.bill.bms.web;

import com.mashape.unirest.request.HttpRequest;
import com.mchange.v1.db.sql.ConnectionUtils;
import com.yipeng.bill.bms.core.model.ResultMessage;
import com.yipeng.bill.bms.dao.UserMapper;
import com.yipeng.bill.bms.dao.inBoxMapper;
import com.yipeng.bill.bms.dao.messageReplyMapper;
import com.yipeng.bill.bms.dao.sendBoxMapper;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.MessageService;
import com.yipeng.bill.bms.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private inBoxMapper inBoxMapper;

    @RequestMapping(value = "/WriteMail")
    public String WriteMail(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        /*List<Role> bumenlist = messageService.getBumen()
        modelMap.put("bumenlist", bumenlist);;*/
        List<User> AddresseeList = messageService.GetAddressee(loginUser);
        modelMap.put("AddresseeList", AddresseeList);
        return "/Message/WriteMail";
    }


    @RequestMapping(value = "/SendBox")
    public String SendBox(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        modelMap.put("loginUser", loginUser);
        return "/Message/SendBox";
    }

    @RequestMapping(value = "/MessageInfo")
    public String MessageInfo(ModelMap modelMap) {
        return "/Message/MessageInfo";
    }

    @RequestMapping(value = "/ReadMail")
    public String ReadMail(ModelMap modelMap, Long MailId, Long StateId) {
        //sendBox sendBox = messageService.getContent(MailId);
        //modelMap.put("sendBox", sendBox);
        LoginUser loginUser = this.getCurrentAccount();
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(MailId);
        if (sendBox.getSenduserid().equals(loginUser.getId().toString()) || sendBox.getInuserid().equals(loginUser.getId().toString())) {
            if ((sendBox.getSenduserid().equals(loginUser.getId().toString()) && sendBox.getDealtstate() != 3) || (sendBox.getInuserid().equals(loginUser.getId().toString()) && sendBox.getDealtstate() != 2)) {
                sendBox.setDealtstate(1);
                sendBoxMapper.updateByPrimaryKeySelective(sendBox);
            }
            //messageReplyMapper.updateByMessageId(FeedbackId);//将MessageId为FeedbackId的信息返回表的DealtState（处理状态）改为2（已查看）
            List<messageReply> messageReplyList = messageReplyMapper.selectByMessageId(MailId);
            String sendUserName = userMapper.selectByPrimaryKey(Long.parseLong(sendBox.getInuserid())).getUserName();
            inBox inBox = inBoxMapper.selectBySendId(sendBox.getId());
            boolean flag = false;
            if (inBox != null && inBox.getMailtype() == 1) {
                flag = true;
            }
            modelMap.put("flag", flag);
            modelMap.put("sendBox", sendBox);
            modelMap.put("messageReplyList", messageReplyList);
            modelMap.put("loginUser", loginUser.getId().toString());
            modelMap.put("sendUserName", sendUserName);
        }
        return "/Message/ReadMail";
    }

    @RequestMapping(value = "/ReadInMail")
    public String ReadInMail(ModelMap modelMap, Long MailId, Long StateId) {
        LoginUser loginUser = this.getCurrentAccount();
        inBox inBox = messageService.getInContent(MailId);
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(inBox.getSendid());
        if (sendBox.getInuserid().equals(loginUser.getId().toString())) {
            if ((sendBox.getSenduserid().equals(loginUser.getId().toString()) && sendBox.getDealtstate() != 3) || (sendBox.getInuserid().equals(loginUser.getId().toString()) && sendBox.getDealtstate() != 2)) {
                sendBox.setDealtstate(1);
                sendBoxMapper.updateByPrimaryKeySelective(sendBox);
            }
            //messageReplyMapper.updateByMessageId(FeedbackId);//将MessageId为FeedbackId的信息返回表的DealtState（处理状态）改为2（已查看）
            List<messageReply> messageReplyList = messageReplyMapper.selectByMessageId(inBox.getSendid());
            String sendUserName = userMapper.selectByPrimaryKey(Long.parseLong(inBox.getSenduserid())).getUserName();
            boolean flag = false;
            if (sendBox.getMailtype() == 1) {
                flag = true;
            }
            modelMap.put("flag", flag);
            modelMap.put("inBox", inBox);
            modelMap.put("messageReplyList", messageReplyList);
            modelMap.put("loginUser", loginUser.getId().toString());
            modelMap.put("sendUserName", sendUserName);
        }
        return "/Message/ReadInMail";
    }

    @RequestMapping(value = "/NoticeSearch")
    public String NoticeSearch(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        modelMap.put("loginUser", loginUser);
        return "/Message/NoticeSearch";
    }

    @RequestMapping(value = "/NoticeReceiveSearch")
    public String NoticeReceiveSearch(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        modelMap.put("loginUser", loginUser);
        return "/Message/NoticeReceiveSearch";
    }

    @RequestMapping(value = "/ReadNoticeContent")
    public String ReadNotice(ModelMap modelMap, Long NoticeId) {
        noticepublish noticepublish = messageService.getNoticeContent(NoticeId);
        LoginUser CurrentId = this.getCurrentAccount();
        if (noticepublish.getSendid().equals(CurrentId.getId().toString()) || noticepublish.getInrole().equals(CurrentId.getCreateUserId().toString())) {
            modelMap.put("noticepublish", noticepublish);
        }
        return "/Message/ReadNoticeContent";
    }

    @RequestMapping(value = "/ReadNoticeReceiveContent")
    public String ReadNoticeReceiveContent(ModelMap modelMap, Long NoticeId) {
        noticepublish noticepublish = messageService.getNoticeContent(NoticeId);
        LoginUser CurrentId = this.getCurrentAccount();
        if (noticepublish.getSendid().equals(CurrentId.getId().toString()) || noticepublish.getInrole().equals(CurrentId.getCreateUserId().toString())) {
            modelMap.put("noticepublish", noticepublish);
        }
        return "/Message/ReadNoticeReceiveContent";
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
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser.hasRole("ADMIN")) {
            loginUser.setId(1l);
        }
        modelMap.put("loginUser", loginUser);
        return "/Message/FeedbackSearch";
    }

    /**
     * 阅读反馈
     *
     * @param modelMap
     * @param FeedbackId
     * @return
     */
    @RequestMapping(value = "/ReadFeedback")
    public String ReadFeedback(ModelMap modelMap, Long FeedbackId) {
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser.hasRole("ADMIN")) {
            loginUser.setId(1l);
        }
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(FeedbackId);
        if (sendBox.getSenduserid().equals(loginUser.getId().toString()) || sendBox.getInuserid().equals(loginUser.getId().toString())) {
            if (sendBox.getDealtstate() != 4 && ((sendBox.getSenduserid().equals(loginUser.getId().toString()) && (sendBox.getDealtstate() != 3 && sendBox.getDealtstate() != 4)) || (sendBox.getInuserid().equals(loginUser.getId().toString()) && (sendBox.getDealtstate() != 2 && sendBox.getDealtstate() != 5)))) {
                sendBox.setDealtstate(1);
                sendBoxMapper.updateByPrimaryKeySelective(sendBox);
            }
            //messageReplyMapper.updateByMessageId(FeedbackId);//将MessageId为FeedbackId的信息返回表的DealtState（处理状态）改为2（已查看）
            List<messageReply> messageReplyList = messageReplyMapper.selectByMessageId(FeedbackId);
            String sendUserName = userMapper.selectByPrimaryKey(Long.parseLong(sendBox.getSenduserid())).getUserName();
            modelMap.put("sendBox", sendBox);
            modelMap.put("messageReplyList", messageReplyList);
            modelMap.put("loginUser", loginUser.getId().toString());
            modelMap.put("sendUserName", sendUserName);
        }
        return "/Message/ReadFeedback";
    }

    @RequestMapping(value = "/InBox")
    public String InBox(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        modelMap.put("loginUser", loginUser);
        return "/Message/InBox";
    }

    @RequestMapping(value = "/DraftBox")
    public String DraftBox(ModelMap modelMap) {
        LoginUser loginUser = this.getCurrentAccount();
        modelMap.put("loginUser", loginUser);
        Map<String, Object> params = new HashMap<>();
        params.put("currentid", loginUser.getId());
        params.put("MailType", 4);
        Long DraftNum = sendBoxMapper.selectDraftBoxNum(params);
        modelMap.put("DraftNum", DraftNum);
        return "/Message/DraftBox";
    }

    @RequestMapping(value = "/ReadDraft")
    public String ReadDraft(ModelMap modelMap, Long sendId) {
        LoginUser loginUser = this.getCurrentAccount();
        List<User> AddresseeList = messageService.GetAddressee(loginUser);
        modelMap.put("AddresseeList", AddresseeList);
        modelMap.put("sendId", sendId);
        return "/Message/ReadDraft";
    }

    @RequestMapping(value = "/DustbinBox")
    public String DustbinBox(ModelMap modelMap) {
        return "/Message/DustbinBox";
    }

    @RequestMapping(value = "/ReadDustbin")
    public String ReadDustbin(ModelMap modelMap, Long MailId) {
        LoginUser loginUser = this.getCurrentAccount();
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(MailId);
        if (sendBox.getSenduserid().equals(loginUser.getId().toString()) || sendBox.getInuserid().equals(loginUser.getId().toString())) {

            //messageReplyMapper.updateByMessageId(FeedbackId);//将MessageId为FeedbackId的信息返回表的DealtState（处理状态）改为2（已查看）
            List<messageReply> messageReplyList = messageReplyMapper.selectByMessageId(MailId);
            String sendUserName = userMapper.selectByPrimaryKey(Long.parseLong(sendBox.getInuserid())).getUserName();

            modelMap.put("sendBox", sendBox);
            modelMap.put("messageReplyList", messageReplyList);
            modelMap.put("loginUser", loginUser.getId().toString());
            modelMap.put("sendUserName", sendUserName);
        }
        return "/Message/ReadDustbin";
    }

    @RequestMapping(value = "/ReadInDustbin")
    public String ReadInDustbin(ModelMap modelMap, Long MailId, Long StateId) {
        LoginUser loginUser = this.getCurrentAccount();
        inBox inBox = messageService.getInContent(MailId);
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(inBox.getSendid());
        if (sendBox.getInuserid().equals(loginUser.getId().toString())) {

            //messageReplyMapper.updateByMessageId(FeedbackId);//将MessageId为FeedbackId的信息返回表的DealtState（处理状态）改为2（已查看）
            List<messageReply> messageReplyList = messageReplyMapper.selectByMessageId(inBox.getSendid());
            String sendUserName = userMapper.selectByPrimaryKey(Long.parseLong(inBox.getSenduserid())).getUserName();

            modelMap.put("inBox", inBox);
            modelMap.put("messageReplyList", messageReplyList);
            modelMap.put("loginUser", loginUser.getId().toString());
            modelMap.put("sendUserName", sendUserName);
        }
        return "/Message/ReadInDustbin";
    }

    /**
     * 根据角色获取用户
     *
     * @param deparment
     * @return
     */
    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public List<User> getUser(String deparment) {
        List<User> userslist = messageService.getUsers(deparment);
        return userslist;
    }

    /**
     * 发信息
     *
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/SendMail", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage SendMail(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.SendMail(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 发公告按钮
     *
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/SendNotice", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage SendNotice(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        int flag = messageService.SendNotice(param, loginUser);

        return this.ajaxDoneSuccess(flag + "");
    }

    /**
     * 发件箱列表
     *
     * @param limit
     * @param offset
     * @return
     */
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

    /**
     * 收件箱列表
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/GetInBox")
    @ResponseBody
    public Map<String, Object> GetInBox(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        Map<String, Object> modelMap = messageService.GetInBox(params, loginUser);

        return modelMap;
    }

    /**
     * 草稿箱列表
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "/GetDraftBox")
    @ResponseBody
    public Map<String, Object> GetDraftBox(int limit, int offset) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        Map<String, Object> modelMap = messageService.GetDraftBox(params, loginUser);

        return modelMap;
    }

    @RequestMapping(value = "/GetDustbin")
    @ResponseBody
    public Map<String, Object> GetDustbin(int limit, int offset, int type) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        if (type != 0 && type != 1) {
            return null;
        }
        if (type == 0) {
            Map<String, Object> modelMap = messageService.GetSendDustbin(params, loginUser);
            return modelMap;
        } else {
            Map<String, Object> modelMap = messageService.GetInDustbin(params, loginUser);
            return modelMap;
        }
    }

    /**
     * 公告查询列表
     *
     * @param limit
     * @param offset
     * @param SearchContent
     * @param type
     * @return
     */
    @RequestMapping(value = "/GetNoticeSearch")
    @ResponseBody
    public Map<String, Object> GetNoticeSearch(int limit, int offset, String SearchContent, int type) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        //0发送 1接收
        Map<String, Object> modelMap = messageService.GetSendOrReciveNotice(params, loginUser, SearchContent, type);
        return modelMap;
    }

    /**
     * 对发件箱进行操作
     *
     * @param sendBoxList
     * @param type
     * @return
     */
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

    /**
     * 对收件箱进行操作
     *
     * @param InBoxList
     * @param type
     * @return
     */
    @RequestMapping(value = "/GoInOperation")
    @ResponseBody
    public ResultMessage GoInOperation(HttpServletRequest InBoxList, int type) {
        Map<String, String[]> param = InBoxList.getParameterMap();
        int len = (param.size() - 1) / 10;
        String[] idarr = new String[len];
        for (int i = 0; i < len; i++) {
            idarr[i] = param.get("data[" + i + "][id]")[0].toString();
        }
        Boolean flag = messageService.updateInRead(idarr, type);
        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 对发件箱的某一条消息进行操作
     *
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(value = "/GoOperationSingle")
    @ResponseBody
    public ResultMessage GoOperationSingle(Long id, int type) {
        Boolean flag = messageService.updateReadSingle(id, type);
        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 对收件箱的某一条消息进行操作
     *
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(value = "/GoInOperationSingle")
    @ResponseBody
    public ResultMessage GoInOperationSingle(Long id, int type) {
        Boolean flag = messageService.updateInReadSingle(id, type);
        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 发件箱未读数量
     *
     * @return
     */
    @RequestMapping(value = "/ReMailNum")
    @ResponseBody
    public ResultMessage ReMailNum() {
        LoginUser loginUser = this.getCurrentAccount();
        Long sum = messageService.getReMailNum(loginUser, 1);
        return this.ajaxDoneSuccess(sum.toString());
    }

    /**
     * 收件箱未读数量
     *
     * @return
     */
    @RequestMapping(value = "/InReMailNum")
    @ResponseBody
    public ResultMessage InReMailNum() {
        LoginUser loginUser = this.getCurrentAccount();
        Long sum = messageService.getInReMailNum(loginUser, 1);
        return this.ajaxDoneSuccess(sum.toString());
    }

    /**
     * 发件箱总数量
     *
     * @return
     */
    @RequestMapping(value = "/SendMailAllNum")
    @ResponseBody
    public ResultMessage SendMailAllNum() {
        LoginUser loginUser = this.getCurrentAccount();
        Long sum = messageService.getReMailNum(loginUser, 0);
        return this.ajaxDoneSuccess(sum.toString());
    }

    /**
     * 收件箱数量
     *
     * @return
     */
    @RequestMapping(value = "/InMailAllNum")
    @ResponseBody
    public ResultMessage InMailAllNum() {
        LoginUser loginUser = this.getCurrentAccount();
        Long sum = messageService.getInMailNum(loginUser, 0);
        return this.ajaxDoneSuccess(sum.toString());
    }

    /**
     * 提交反馈按钮
     *
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/SubmitFeedback")
    @ResponseBody
    public ResultMessage SubmitFeedback(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.SubmitFeedback(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 反馈查询列表
     *
     * @param limit
     * @param offset
     * @param SearchContent
     * @param type
     * @return
     */
    @RequestMapping(value = "/GetFeedbackSearch")
    @ResponseBody
    public Map<String, Object> GetFeedbackSearch(int limit, int offset, String SearchContent, int type) {
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser.hasRole("ADMIN")) {
            loginUser.setId(1l);
        }
        Map<String, Object> params = this.getSearchRequest(); //查询参数
        params.put("limit", limit);
        params.put("offset", offset);
        //0发送 1接收
        Map<String, Object> modelMap = messageService.GetSendOrReciveFeedback(params, loginUser, SearchContent, type);
        return modelMap;
    }

    //反馈回复
    @RequestMapping(value = "/replySubmit", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage replySubmit(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        if (loginUser.hasRole("ADMIN")) {
            loginUser.setId(1l);
        }
        boolean flag = messageService.replySubmit(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    //信息回复
    @RequestMapping(value = "/MailreplySubmit", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage MailreplySubmit(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.replySubmit(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    //信息回复
    @RequestMapping(value = "/InMailreplySubmit", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage InMailreplySubmit(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.MailreplySubmit(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 存至草稿箱
     *
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/SaveDraftMail", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage SaveDraftMail(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.SaveDraftMail(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 预加载草稿箱
     *
     * @param sendId
     * @return
     */
    @RequestMapping(value = "/LoadInit", method = RequestMethod.POST)
    @ResponseBody
    public sendBox LoadInit(Long sendId) {
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(sendId);
        return sendBox;
    }

    /**
     * 保存已查看的草稿箱
     *
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/SaveOldDraft", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage SaveOldDraft(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.SaveOldDraft(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 发送已查看的草稿箱
     *
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/SendDraft", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage SendDraft(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        LoginUser loginUser = this.getCurrentAccount();
        boolean flag = messageService.SendDraft(param, loginUser);

        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 删除草稿箱
     *
     * @param httpRequest
     * @return
     */
    @RequestMapping(value = "/DeleteDraft", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage DeleteDraft(HttpServletRequest httpRequest) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        int len = param.size() / 10;
        String[] idarr = new String[len];
        for (int i = 0; i < len; i++) {
            idarr[i] = param.get("data[" + i + "][id]")[0].toString();
        }
        Boolean flag = messageService.DeleteDraft(idarr);
        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    @RequestMapping(value = "/DeleteGarbage", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage DeleteGarbage(HttpServletRequest httpRequest, int type) {
        Map<String, String[]> param = httpRequest.getParameterMap();
        int len = (param.size() + 1) / 10;
        String[] idarr = new String[len];
        for (int i = 0; i < len; i++) {
            idarr[i] = param.get("data[" + i + "][id]")[0].toString();
        }
        Boolean flag = messageService.DeleteGarbage(idarr, type);
        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 删除单个垃圾箱
     *
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(value = "/DeleteGarbageSingle", method = RequestMethod.POST)
    @ResponseBody
    public ResultMessage DeleteGarbageSingle(String id, int type) {
        String[] idarr = new String[1];
        idarr[0] = id;
        Boolean flag = messageService.DeleteGarbage(idarr, type);
        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 结束反馈对话
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/FinishFeedback")
    @ResponseBody
    public ResultMessage FinishFeedback(Long id) {
        Boolean flag = messageService.FinishFeedback(id);
        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }

    /**
     * 删除选中的公告
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/deleteNotice")
    @ResponseBody
    public ResultMessage deleteNotice(HttpServletRequest httpServletRequest) {
        LoginUser loginUser = this.getCurrentAccount();
        Map<String, String[]> map = httpServletRequest.getParameterMap();
        int len = Integer.parseInt(map.get("len")[0].toString());
        Long[] arr = new Long[len];
        for (int i = 0; i < len; i++) {
            arr[i] = Long.parseLong(map.get("selectContent[" + i + "][id]")[0]);
        }
        Boolean flag = messageService.DeleteNotice(arr,loginUser);
        return this.ajaxDoneSuccess(flag ? "1" : "0");
    }
}
