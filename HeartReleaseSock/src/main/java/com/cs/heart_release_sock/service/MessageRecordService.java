package com.cs.heart_release_sock.service;

import com.cs.heart_release_sock.pojo.MessageRecord;

import java.util.List;

public interface MessageRecordService {

    void insert(MessageRecord messageRecord);

    List<MessageRecord> selectByTo(Integer to);

    void update(MessageRecord messageRecord);
}
