package com.cs.heart_release_sock.mapper;

import com.cs.heart_release_sock.pojo.MessageRecord;
import com.cs.heart_release_sock.pojo.MessageRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface MessageRecordMapper {
    long countByExample(MessageRecordExample example);

    int deleteByExample(MessageRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessageRecord record);

    int insertSelective(MessageRecord record);

    List<MessageRecord> selectByExampleWithBLOBs(MessageRecordExample example);

    List<MessageRecord> selectByExample(MessageRecordExample example);

    MessageRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessageRecord record, @Param("example") MessageRecordExample example);

    int updateByExampleWithBLOBs(@Param("record") MessageRecord record, @Param("example") MessageRecordExample example);

    int updateByExample(@Param("record") MessageRecord record, @Param("example") MessageRecordExample example);

    int updateByPrimaryKeySelective(MessageRecord record);

    int updateByPrimaryKeyWithBLOBs(MessageRecord record);

    int updateByPrimaryKey(MessageRecord record);
}