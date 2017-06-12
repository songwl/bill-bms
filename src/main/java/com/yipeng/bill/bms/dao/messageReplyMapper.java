package com.yipeng.bill.bms.dao;

import com.yipeng.bill.bms.domain.messageReply;

import java.util.List;

public interface messageReplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(messageReply record);

    int insertSelective(messageReply record);

    messageReply selectByPrimaryKey(Long id);

    List<messageReply> selectByMessageId(Long MessageId);

    int updateByPrimaryKeySelective(messageReply record);

    int updateByPrimaryKey(messageReply record);

    int updateByMessageId(Long MessageId);
}