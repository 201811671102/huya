package com.cs.heart_release_sock.service.impl;

import com.cs.heart_release_sock.code.BaseCode;
import com.cs.heart_release_sock.exception.HeartReleaseException;
import com.cs.heart_release_sock.mapper.MessageRecordMapper;
import com.cs.heart_release_sock.pojo.MessageRecord;
import com.cs.heart_release_sock.pojo.MessageRecordExample;
import com.cs.heart_release_sock.service.MessageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName MessageRecordServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/7 14:30
 **/
@Service
public class MessageRecordServiceImpl implements MessageRecordService {
    @Autowired
    MessageRecordExample messageRecordExample;
    @Autowired
    MessageRecordMapper messageRecordMapper;

    @Override
    public void insert(MessageRecord messageRecord) {
        int i = messageRecordMapper.insertSelective(messageRecord);
        if (i == -1){
            throw new HeartReleaseException(BaseCode.System_Error);
        }
    }

    @Override
    public List<MessageRecord> selectByTo(Integer to) {
        messageRecordExample.clear();
        messageRecordExample.createCriteria()
                .andToUserIdEqualTo(to)
                .andReadedEqualTo(false);
        List<MessageRecord> messageRecords = messageRecordMapper.selectByExampleWithBLOBs(messageRecordExample);
        return messageRecords.stream()
                .sorted(Comparator.comparing(MessageRecord::getRecordTime))
                .collect(Collectors.toList());
    }

    @Override
    public void update(MessageRecord messageRecord) {
        messageRecord.setReaded(true);
        int i = messageRecordMapper.updateByPrimaryKey(messageRecord);
        if (i == -1){
            throw new HeartReleaseException(BaseCode.System_Error);
        }
    }
}
