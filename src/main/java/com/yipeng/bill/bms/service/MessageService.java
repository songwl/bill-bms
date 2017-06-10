package com.yipeng.bill.bms.service;

import com.yipeng.bill.bms.domain.Role;
import com.yipeng.bill.bms.domain.User;
import com.yipeng.bill.bms.domain.noticepublish;
import com.yipeng.bill.bms.domain.sendBox;
import com.yipeng.bill.bms.vo.LoginUser;

import java.util.List;
import java.util.Map;

public interface MessageService {
    List<Role> getBumen();

    List<User> getUsers(String role);

    Boolean SendMail(Map<String, String[]> data, LoginUser loginUser);

    Boolean SendNotice(Map<String, String[]> data, LoginUser loginUser);

    Boolean SubmitFeedback(Map<String, String[]> data, LoginUser loginUser);

    Boolean replySubmit(Map<String, String[]> data, LoginUser loginUser);

    Map<String, Object> GetSendBox(Map<String, Object> params, LoginUser loginUser);

    Boolean updateRead(String[] idarr, int type);

    Boolean updateReadSingle(Long id, int type);

    Long getReMailNum(LoginUser loginUser, int type);//获取发件箱数量

    sendBox getContent(Long id);

    noticepublish getNoticeContent(Long NoticeId);

    Map<String, Object> GetSendOrReciveNotice(Map<String, Object> params, LoginUser loginUser, String SearchContent, int type);

    Map<String, Object> GetSendOrReciveFeedback(Map<String, Object> params, LoginUser loginUser, String SearchContent, int type);
}
