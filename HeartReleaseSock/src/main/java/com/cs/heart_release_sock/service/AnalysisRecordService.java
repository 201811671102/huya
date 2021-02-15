package com.cs.heart_release_sock.service;

import com.cs.heart_release_sock.dto.AnalysisRecordDTO;
import com.cs.heart_release_sock.pojo.AnalysisRecord;

import java.util.Date;
import java.util.List;

/**
 * @ClassName AnalysisServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/15 13:07
 **/
public interface AnalysisRecordService {
    /*添加记录*/
    void insert(AnalysisRecord analysisRecord);
    /*用户id查询全部*/
    List<AnalysisRecordDTO> selectAll(Integer uid, Date startTime, Date endTime);

}
