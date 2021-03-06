package com.yipeng.bill.bms.service.impl;

import com.mchange.v1.db.sql.ConnectionUtils;
import com.yipeng.bill.bms.dao.*;
import com.yipeng.bill.bms.domain.*;
import com.yipeng.bill.bms.service.MessageService;
import com.yipeng.bill.bms.vo.Feedback;
import com.yipeng.bill.bms.vo.LoginUser;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private sendBoxMapper sendBoxMapper;
    @Autowired
    private inBoxMapper inBoxMapper;
    @Autowired
    private noticepublishMapper noticepublishMapper;
    @Autowired
    private messageReplyMapper messageReplyMapper;

    @Override
    public List<Role> getBumen() {
        List<Role> list = roleMapper.selectAllRole();
        return list;
    }

    @Override
    public List<User> GetAddressee(LoginUser loginUser) {
        List<User> list = userMapper.selectAddressee(loginUser.getId().toString());
        return list;
    }

    @Override
    public List<User> getUsers(String role) {
        List<User> list = userMapper.selectAllUsers(role);
        return list;
    }

    @Override
    public Boolean SendMail(Map<String, String[]> data, LoginUser loginUser) {
        Long SendUserId = loginUser.getId();
        String InUserId = data.get("User")[0];
        String Title = data.get("Title")[0];
        String Content = data.get("content")[0];
        String affairState = data.get("affairState")[0];
        int num1 = 0, num2 = 0;
        if (!"0".equals(InUserId)) {
            sendBox sendBox = new sendBox();
            sendBox.setSenduserid(SendUserId.toString());
            sendBox.setInuserid(InUserId);
            sendBox.setMailtype(1);
            sendBox.setTitle(Title);
            sendBox.setContent(Content);
            sendBox.setAffairstate(Integer.parseInt(affairState));
            sendBox.setDealtstate(3);
            sendBox.setSendtime(new Date());
            num1 += sendBoxMapper.insert(sendBox);
            inBox inBox = new inBox();
            inBox.setSendid(sendBox.getId());
            inBox.setSenduserid(SendUserId.toString());
            inBox.setInuserid(InUserId);
            inBox.setMailtype(1);
            inBox.setTitle(Title);
            inBox.setContent(Content);
            inBox.setAffairstate(Integer.parseInt(affairState));
            inBox.setDealtstate(3);
            inBox.setIntime(new Date());
            num2 += inBoxMapper.insert(inBox);
        } else {
            String department = data.get("department")[0];
            List<User> users = userMapper.selectAllUsers(department);
            for (User item : users
                    ) {
                sendBox sendBox = new sendBox();
                sendBox.setSenduserid(SendUserId.toString());
                sendBox.setInuserid(item.getId().toString());
                sendBox.setMailtype(1);
                sendBox.setTitle(Title);
                sendBox.setContent(Content);
                sendBox.setAffairstate(Integer.parseInt(affairState));
                sendBox.setDealtstate(3);
                sendBox.setSendtime(new Date());
                num1 += sendBoxMapper.insert(sendBox);
                inBox inBox = new inBox();
                inBox.setSenduserid(SendUserId.toString());
                inBox.setInuserid(item.getId().toString());
                inBox.setMailtype(1);
                inBox.setTitle(Title);
                inBox.setContent(Content);
                inBox.setAffairstate(Integer.parseInt(affairState));
                inBox.setDealtstate(3);
                inBox.setIntime(new Date());
                num2 += inBoxMapper.insert(inBox);
            }
        }
        return (num1 == num2 && num1 > 0) ? true : false;
    }

    @Override
    public Boolean SaveDraftMail(Map<String, String[]> data, LoginUser loginUser) {
        Long SendUserId = loginUser.getId();
        String InUserId = data.get("User")[0];
        String Title = data.get("Title")[0];
        String Content = data.get("content")[0];
        String affairState = data.get("affairState")[0];
        int num1 = 0, num2 = 0;
        if (!"0".equals(InUserId)) {
            sendBox sendBox = new sendBox();
            sendBox.setSenduserid(SendUserId.toString());
            sendBox.setInuserid(InUserId);
            sendBox.setMailtype(4);
            sendBox.setTitle(Title);
            sendBox.setContent(Content);
            sendBox.setAffairstate(Integer.parseInt(affairState));
            sendBox.setDealtstate(3);
            sendBox.setSendtime(new Date());
            num1 += sendBoxMapper.insert(sendBox);
            inBox inBox = new inBox();
            inBox.setSendid(sendBox.getId());
            inBox.setSenduserid(SendUserId.toString());
            inBox.setInuserid(InUserId);
            inBox.setMailtype(4);//草稿箱
            inBox.setTitle(Title);
            inBox.setContent(Content);
            inBox.setAffairstate(Integer.parseInt(affairState));
            inBox.setDealtstate(3);
            inBox.setIntime(new Date());
            num2 += inBoxMapper.insert(inBox);
        } else {
            String department = data.get("department")[0];
            List<User> users = userMapper.selectAllUsers(department);
            for (User item : users
                    ) {
                sendBox sendBox = new sendBox();
                sendBox.setSenduserid(SendUserId.toString());
                sendBox.setInuserid(item.getId().toString());
                sendBox.setMailtype(4);//草稿箱
                sendBox.setTitle(Title);
                sendBox.setContent(Content);
                sendBox.setAffairstate(Integer.parseInt(affairState));
                sendBox.setDealtstate(0);
                sendBox.setSendtime(new Date());
                num1 += sendBoxMapper.insert(sendBox);
                inBox inBox = new inBox();
                inBox.setSenduserid(SendUserId.toString());
                inBox.setInuserid(item.getId().toString());
                inBox.setMailtype(4);
                inBox.setTitle(Title);
                inBox.setContent(Content);
                inBox.setAffairstate(Integer.parseInt(affairState));
                inBox.setDealtstate(0);
                inBox.setIntime(new Date());
                num2 += inBoxMapper.insert(inBox);
            }
        }
        return (num1 == num2 && num1 > 0) ? true : false;
    }

    @Override
    public Boolean SaveOldDraft(Map<String, String[]> data, LoginUser loginUser) {
        Long SendUserId = loginUser.getId();
        String id = data.get("id")[0];
        String InUserId = data.get("User")[0];
        String Title = data.get("Title")[0];
        String Content = data.get("content")[0];
        String affairState = data.get("affairState")[0];
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(Long.parseLong(id));
        sendBox.setInuserid(InUserId);
        sendBox.setTitle(Title);
        sendBox.setContent(Content);
        sendBox.setAffairstate(Integer.parseInt(affairState));
        sendBox.setSendtime(new Date());
        sendBox.setDealtstate(3);
        inBox inBox = inBoxMapper.selectBySendId(Long.parseLong(id));
        inBox.setInuserid(InUserId);
        inBox.setTitle(Title);
        inBox.setContent(Content);
        inBox.setAffairstate(Integer.parseInt(affairState));
        inBox.setIntime(new Date());
        inBox.setDealtstate(3);
        int num1 = sendBoxMapper.updateByPrimaryKeySelective(sendBox);
        int num2 = inBoxMapper.updateByPrimaryKeySelective(inBox);
        if (num1 == num2 && num1 > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean SendDraft(Map<String, String[]> data, LoginUser loginUser) {
        Long SendUserId = loginUser.getId();
        String id = data.get("id")[0];
        String InUserId = data.get("User")[0];
        String Title = data.get("Title")[0];
        String Content = data.get("content")[0];
        String affairState = data.get("affairState")[0];
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(Long.parseLong(id));
        sendBox.setInuserid(InUserId);
        sendBox.setTitle(Title);
        sendBox.setContent(Content);
        sendBox.setAffairstate(Integer.parseInt(affairState));
        sendBox.setMailtype(1);
        sendBox.setSendtime(new Date());
        sendBox.setDealtstate(3);
        inBox inBox = inBoxMapper.selectBySendId(Long.parseLong(id));
        inBox.setInuserid(InUserId);
        inBox.setTitle(Title);
        inBox.setContent(Content);
        inBox.setMailtype(1);
        inBox.setAffairstate(Integer.parseInt(affairState));
        inBox.setIntime(new Date());
        inBox.setDealtstate(3);
        int num1 = sendBoxMapper.updateByPrimaryKeySelective(sendBox);
        int num2 = inBoxMapper.updateByPrimaryKeySelective(inBox);
        if (num1 == num2 && num1 > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int SendNotice(Map<String, String[]> data, LoginUser loginUser) {
        if (!loginUser.hasRole("SUPER_ADMIN") && !loginUser.hasRole("DISTRIBUTOR") && !loginUser.hasRole("AGENT") && !loginUser.hasRole("ADMIN")) {
            return 2;
        }
        Long SendUserId = loginUser.getId();
        String Title = data.get("Title")[0];
        String Content = data.get("content")[0];
        /*String department = data.get("department")[0];*/
        noticepublish noticepublish = new noticepublish();
        noticepublish.setSendid(SendUserId.toString());
        noticepublish.setInrole(SendUserId.toString());
        noticepublish.setMailtype(1);
        noticepublish.setTitle(Title);
        noticepublish.setContent(Content);
        noticepublish.setDealtstate(0);
        noticepublish.setSendtime(new Date());
        int num1 = noticepublishMapper.insert(noticepublish);
        return (num1 > 0) ? 1 : 0;
    }

    /**
     * 提交反馈
     *
     * @param data
     * @param loginUser
     * @return
     */
    @Override
    public Boolean SubmitFeedback(Map<String, String[]> data, LoginUser loginUser) {
        Long SendUserId = loginUser.getId();
        String Title = data.get("Title")[0];
        String Content = data.get("content")[0];
        String affairState = data.get("affairState")[0];
        User user = userMapper.selectByPrimaryKey(SendUserId);
        String InUserId = user.getCreateUserId().toString();
        sendBox sendBox = new sendBox();
        sendBox.setSenduserid(SendUserId.toString());
        sendBox.setInuserid(InUserId);
        sendBox.setMailtype(2);
        sendBox.setTitle(Title);
        sendBox.setContent(Content);
        sendBox.setAffairstate(Integer.parseInt(affairState));
        sendBox.setDealtstate(3);
        sendBox.setSendtime(new Date());
        int num1 = sendBoxMapper.insert(sendBox);
        /*inBox inBox = new inBox();
        inBox.setSenduserid(SendUserId.toString());
        inBox.setInuserid(InUserId);
        inBox.setMailtype(2);
        inBox.setTitle(Title);
        inBox.setContent(Content);
        inBox.setAffairstate(Integer.parseInt(affairState));
        inBox.setDealtstate(0);
        inBox.setIntime(new Date());
        int num2 = inBoxMapper.insert(inBox);*/
        return (num1 > 0) ? true : false;
    }

    @Override
    public Boolean replySubmit(Map<String, String[]> data, LoginUser loginUser) {
        Long SendUserId = loginUser.getId();
        Long id = Long.parseLong(data.get("id")[0]);
        String Content = data.get("ReplyContent")[0];
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(id);
        if (sendBox.getDealtstate() == 4) {//会话已结束
            return false;
        }
        messageReply messageReply = new messageReply();
        messageReply.setMessageid(id);
        messageReply.setSendid(SendUserId.toString());
        if (sendBox.getSenduserid().equals(SendUserId.toString())) {
            messageReply.setInid(sendBox.getInuserid());
            sendBox.setDealtstate(3);//接收者有消息提醒
        } else {
            messageReply.setInid(sendBox.getSenduserid());
            sendBox.setDealtstate(2);//接收者有消息提醒
        }
        messageReply.setReplycontent(Content);
        messageReply.setDealtstate(1);
        messageReply.setMessagetype(1);
        messageReply.setReplytime(new Date());
        int num = messageReplyMapper.insert(messageReply);
        int num2 = sendBoxMapper.updateByPrimaryKeySelective(sendBox);
        return (num == num2 && num > 0);
    }

    @Override
    public Boolean MailreplySubmit(Map<String, String[]> data, LoginUser loginUser) {
        Long SendUserId = loginUser.getId();
        Long id = Long.parseLong(data.get("id")[0]);
        inBox inBox = inBoxMapper.selectByPrimaryKey(id);
        id = inBox.getSendid();
        String Content = data.get("ReplyContent")[0];
        int mailType = Integer.parseInt(data.get("mailType")[0]);
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(id);
        messageReply messageReply = new messageReply();
        messageReply.setMessageid(id);
        messageReply.setSendid(SendUserId.toString());
        if (sendBox.getSenduserid().equals(SendUserId.toString())) {
            messageReply.setInid(sendBox.getInuserid());
            sendBox.setDealtstate(3);//接收者有消息提醒
        } else {
            messageReply.setInid(sendBox.getSenduserid());
            sendBox.setDealtstate(2);//接收者有消息提醒
        }
        messageReply.setReplycontent(Content);
        messageReply.setDealtstate(1);
        messageReply.setMessagetype(mailType);//0是信息消息 1是反馈消息
        messageReply.setReplytime(new Date());
        int num = messageReplyMapper.insert(messageReply);
        int num2 = sendBoxMapper.updateByPrimaryKeySelective(sendBox);
        return (num == num2 && num > 0);
    }

    /**
     * 获取发件箱列表
     *
     * @param params
     * @param loginUser
     * @return
     */
    @Override
    public Map<String, Object> GetSendBox(Map<String, Object> params, LoginUser loginUser) {
        params.put("currentid", loginUser.getId());
        params.put("MailType", 1);
        params.put("type", 0);
        Long total = sendBoxMapper.selectCount(params);
        List<sendBox> sendBoxList = sendBoxMapper.selectSendBox(params);
        /*SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        for (sendBox item:sendBoxList
             ) {
            item.setSendtime(sm.format(item.getSendtime()));
        }*/
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", sendBoxList);

        return map;
    }

    @Override
    public Map<String, Object> GetInBox(Map<String, Object> params, LoginUser loginUser) {
        params.put("currentid", loginUser.getId());
        params.put("MailType", 1);
        Long total = inBoxMapper.selectCount(params);
        List<inBox> inBoxList = inBoxMapper.selectInBox(params);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", inBoxList);

        return map;
    }

    @Override
    public Map<String, Object> GetDraftBox(Map<String, Object> params, LoginUser loginUser) {
        params.put("currentid", loginUser.getId());
        params.put("MailType", 4);
        Long total = inBoxMapper.selectCount(params);
        List<sendBox> sendBoxList = sendBoxMapper.selectDraftBox(params);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", sendBoxList);
        return map;
    }

    @Override
    public Map<String, Object> GetSendDustbin(Map<String, Object> params, LoginUser loginUser) {
        params.put("currentid", loginUser.getId());
        params.put("MailType", 0);
        params.put("type", 0);
        Long total = inBoxMapper.selectCount(params);
        List<sendBox> sendBoxList = sendBoxMapper.selectSendBox(params);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", sendBoxList);
        return map;
    }

    @Override
    public Map<String, Object> GetInDustbin(Map<String, Object> params, LoginUser loginUser) {
        params.put("currentid", loginUser.getId());
        params.put("MailType", 0);
        Long total = inBoxMapper.selectCount(params);
        List<inBox> inBoxList = inBoxMapper.selectInBox(params);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", inBoxList);
        return map;
    }

    @Override
    public Boolean updateRead(String[] idarr, int type) {
        for (String item : idarr
                ) {
            sendBox sendBox = sendBoxMapper.selectByPrimaryKey(Long.parseLong(item));
            switch (type) {
                case 1://将处理类型改为已查看
                    break;
                case 2://type=2,将事务状态改为重要
                    sendBox.setAffairstate(3);
                    break;
                case 3://type=3，将邮件类型改为0，既垃圾箱
                    sendBox.setMailtype(0);
                    break;
            }
            sendBox.setDealtstate(1);//将处理类型改为已查看
            Boolean flag = sendBoxMapper.updateByPrimaryKey(sendBox) > 0;
            if (!flag) return false;
        }
        return true;
    }

    @Override
    public Boolean DeleteDraft(String[] idarr) {
        int num1 = 0, num2 = 0;
        for (String item : idarr
                ) {
            num1 += sendBoxMapper.deleteByPrimaryKey(Long.parseLong(item));
            num2 += inBoxMapper.deleteBySendId(Long.parseLong(item));
        }
        if (num1 == num2 && num1 > 0) return true;
        return false;
    }

    @Override
    public Boolean DeleteGarbage(String[] idarr, int type) {
        if (type != 0 && type != 1) {
            return false;
        }
        if (type == 0) {//发件箱删除
            int num1 = 0, num2 = 0;
            for (String item : idarr
                    ) {
                inBox inBox = inBoxMapper.selectBySendId(Long.parseLong(item));
                if (inBox == null) {
                    num1 += sendBoxMapper.deleteByPrimaryKey(Long.parseLong(item));
                    num2++;
                    messageReplyMapper.deleteByMessageId(Long.parseLong(item));//删除回复信息
                } else {
                    sendBox sendBox = sendBoxMapper.selectByPrimaryKey(Long.parseLong(item));
                    sendBox.setMailtype(-1);
                    num1 += sendBoxMapper.updateByPrimaryKeySelective(sendBox);
                    num2++;
                }
            }
            if (num1 == num2 && num1 > 0) return true;
            return false;
        } else//收件箱删除
        {
            int num1 = 0, num2 = 0;
            for (String item : idarr
                    ) {
                inBox inBox = inBoxMapper.selectByPrimaryKey(Long.parseLong(item));
                sendBox sendBox = sendBoxMapper.selectByPrimaryKey(inBox.getSendid());
                if (sendBox.getMailtype() != -1) {
                    num1 += inBoxMapper.deleteByPrimaryKey(Long.parseLong(item));
                    num2++;
                } else {
                    num1 += inBoxMapper.deleteByPrimaryKey(Long.parseLong(item));
                    num2 += sendBoxMapper.deleteByPrimaryKey(inBox.getSendid());
                    messageReplyMapper.deleteByMessageId(inBox.getSendid());//删除回复信息
                }
            }
            if (num1 == num2 && num1 > 0) return true;
            return false;
        }
    }

    @Override
    public Boolean updateInRead(String[] idarr, int type) {
        for (String item : idarr
                ) {
            inBox inBox = inBoxMapper.selectByPrimaryKey(Long.parseLong(item));
            sendBox sendBox = sendBoxMapper.selectByPrimaryKey(inBox.getSendid());
            if (type == 1) {
            }
            if (type == 2) {
                inBox.setAffairstate(3);
            }
            if (type == 3) {
                inBox.setMailtype(0);
            }
            sendBox.setDealtstate(1);
            Boolean flag = inBoxMapper.updateByPrimaryKey(inBox) > 0
                    && sendBoxMapper.updateByPrimaryKeySelective(sendBox) > 0;
            if (!flag) return false;
        }
        return true;
    }

    @Override
    public Boolean updateReadSingle(Long id, int type) {
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(id);
        if (type == 1) {
        }
        if (type == 2) {
            sendBox.setAffairstate(3);
        }
        if (type == 3) {
            sendBox.setMailtype(0);
        }
        sendBox.setDealtstate(1);
        Boolean flag = sendBoxMapper.updateByPrimaryKey(sendBox) > 0;
        if (!flag) return false;
        return true;
    }

    @Override
    public Boolean updateInReadSingle(Long id, int type) {
        inBox inBox = inBoxMapper.selectByPrimaryKey(id);
        if (type == 1) {
        }
        if (type == 2) {
            inBox.setAffairstate(3);
        }
        if (type == 3) {
            inBox.setMailtype(0);
        }
        inBox.setDealtstate(1);
        Boolean flag = inBoxMapper.updateByPrimaryKey(inBox) > 0;
        if (!flag) return false;
        return true;
    }

    @Override
    public Long getReMailNum(LoginUser loginUser, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentid", loginUser.getId());
        params.put("MailType", 1);
        params.put("type", 0);
        Long sum;
        if (type == 0) {
            sum = sendBoxMapper.selectCount(params);
        } else {
            sum = sendBoxMapper.selectCountRead(params);
        }
        return sum;
    }

    @Override
    public Long getInReMailNum(LoginUser loginUser, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentid", loginUser.getId());
        params.put("MailType", 1);
        params.put("type", 1);
        Long sum;
        if (type == 0) {
            sum = inBoxMapper.selectCount(params);
        } else {
            sum = inBoxMapper.selectCountRead(params);
        }
        return sum;
    }

    @Override
    public Long getInMailNum(LoginUser loginUser, int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentid", loginUser.getId());
        params.put("MailType", 1);
        Long sum;
        if (type == 0) {
            sum = inBoxMapper.selectCount(params);
        } else {
            sum = inBoxMapper.selectCountRead(params);
        }
        return sum;
    }

    @Override
    public sendBox getContent(Long id) {
        sendBox sendBox = sendBoxMapper.selectById(id);
        return sendBox;
    }

    @Override
    public inBox getInContent(Long id) {
        inBox inBox = inBoxMapper.selectByPrimaryKey(id);
        return inBox;
    }

    @Override
    public noticepublish getNoticeContent(Long NoticeId) {
        noticepublish noticepublish = noticepublishMapper.selectByPrimaryKey(NoticeId);
        return noticepublish;
    }

    @Override
    public Map<String, Object> GetSendOrReciveNotice(Map<String, Object> params, LoginUser loginUser, String SearchContent, int type) {
        if (type != 1 && type != 0) {
            return null;
        }
        params.put("currentid", loginUser.getId().toString());
        params.put("type", type);
        params.put("SearchContent", SearchContent);
        Long total = noticepublishMapper.getCount(params);
        List<noticepublish> noticepublishList = noticepublishMapper.selectByInUser(params);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", noticepublishList);
        return map;
    }

    @Override
    public Map<String, Object> GetSendOrReciveFeedback(Map<String, Object> params, LoginUser loginUser, String SearchContent, int type) {
        params.put("currentid", loginUser.getId().toString());
        params.put("SearchContent", SearchContent);
        params.put("MailType", 2);
        params.put("type", type);
        Long total = sendBoxMapper.selectCount(params);
        List<sendBox> sendBoxList = sendBoxMapper.selectSendBox(params);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", sendBoxList);
        return map;
    }

    @Override
    public Boolean FinishFeedback(Long id) {
        sendBox sendBox = sendBoxMapper.selectByPrimaryKey(id);
        sendBox.setDealtstate(4);
        int num = sendBoxMapper.updateByPrimaryKeySelective(sendBox);
        return num > 0;
    }

    @Override
    public Boolean DeleteNotice(Long[] arr, LoginUser loginUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("arr", arr);
        map.put("dealtstate", -1);
        map.put("userId", loginUser.getId().toString());
        int num = noticepublishMapper.updateByIdsSelective(map);
        return num == arr.length ? true : false;
    }
}
