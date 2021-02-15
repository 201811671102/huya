package com.cs.heart_release_sock.mapper.diymapper;

import com.cs.heart_release_sock.pojo.Analysis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName AnalysisMapperDIY
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/15 15:05
 **/
@Component
@Mapper
public interface AnalysisMapperDIY {

    /*
    * 查询用户没有统计分析的消息分析记录
    * */
    @Select("SELECT * FROM analysis WHERE id IN ( SELECT id FROM analysis WHERE id > IFNULL( ( SELECT MAX(analysis_id) FROM analysis_record WHERE user_id = #{userId}),0) AND user_id = #{userId} )")
    List<Analysis> selectUserAnalysis(Integer userId);
}
