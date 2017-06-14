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
    RoleMapper roleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    sendBoxMapper sendBoxMapper;
    @Autowired
    inBoxMapper inBoxMapper;
    @Autowired
    noticepublishMapper noticepublishMapper;
    @Autowired
    messageReplyMapper messageReplyMapper;

    @Override
    public List<Role> getBumen() {
        List<Role> list = roleMapper.selectAllRole();
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
                sendBox.setDealtstate(0);
                sendBox.setSendtime(new Date());
                num1 += sendBoxMapper.insert(sendBox);
                inBox inBox = new inBox();
                inBox.setSenduserid(SendUserId.toString());
                inBox.setInuserid(item.getId().toString());
                inBox.setMailtype(1);
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
    public int SendNotice(Map<String, String[]> data, LoginUser loginUser) {
        if (!loginUser.hasRole("SUPER_ADMIN") && !loginUser.hasRole("DISTRIBUTOR") && !loginUser.hasRole("AGENT")) {
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
    public Boolean updateRead(String[] idarr, int type) {
        for (String item : idarr
                ) {
            sendBox sendBox = sendBoxMapper.selectByPrimaryKey(Long.parseLong(item));
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
        }
        return true;
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
}
