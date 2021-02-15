package com.cs.heart_release_sock.mapper;

import com.cs.heart_release_sock.pojo.AnalysisRecord;
import com.cs.heart_release_sock.pojo.AnalysisRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface AnalysisRecordMapper {
    long countByExample(AnalysisRecordExample example);

    int deleteByExample(AnalysisRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AnalysisRecord record);

    int insertSelective(AnalysisRecord record);

    List<AnalysisRecord> selectByExample(AnalysisRecordExample example);

    AnalysisRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AnalysisRecord record, @Param("example") AnalysisRecordExample example);

    int updateByExample(@Param("record") AnalysisRecord record, @Param("example") AnalysisRecordExample example);

    int updateByPrimaryKeySelective(AnalysisRecord record);

    int updateByPrimaryKey(AnalysisRecord record);
}