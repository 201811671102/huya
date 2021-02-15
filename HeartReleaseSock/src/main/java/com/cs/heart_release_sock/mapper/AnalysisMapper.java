package com.cs.heart_release_sock.mapper;

import com.cs.heart_release_sock.pojo.Analysis;
import com.cs.heart_release_sock.pojo.AnalysisExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface AnalysisMapper {
    long countByExample(AnalysisExample example);

    int deleteByExample(AnalysisExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Analysis record);

    int insertSelective(Analysis record);

    List<Analysis> selectByExample(AnalysisExample example);

    Analysis selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Analysis record, @Param("example") AnalysisExample example);

    int updateByExample(@Param("record") Analysis record, @Param("example") AnalysisExample example);

    int updateByPrimaryKeySelective(Analysis record);

    int updateByPrimaryKey(Analysis record);
}