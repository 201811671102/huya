package com.cs.heart_release_sock.service.impl;

import com.cs.heart_release_sock.code.BaseCode;
import com.cs.heart_release_sock.dto.AnalysisRecordDTO;
import com.cs.heart_release_sock.exception.HeartReleaseException;
import com.cs.heart_release_sock.mapper.AnalysisRecordMapper;
import com.cs.heart_release_sock.pojo.AnalysisRecord;
import com.cs.heart_release_sock.pojo.AnalysisRecordExample;
import com.cs.heart_release_sock.service.AnalysisRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AnalysisRecordServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/15 14:37
 **/
@Service
public class AnalysisRecordServiceImpl implements AnalysisRecordService {
    @Autowired
    AnalysisRecordMapper analysisRecordMapper;
    @Autowired
    AnalysisRecordExample analysisRecordExample;

    @Override
    public void insert(AnalysisRecord analysisRecord) {
        int i = analysisRecordMapper.insertSelective(analysisRecord);
        if ( i == -1 ){
            throw new HeartReleaseException(BaseCode.System_Error);
        }
    }

    @Override
    public List<AnalysisRecordDTO> selectAll(Integer uid, Date startTime, Date endTime) {
        analysisRecordExample.clear();
        analysisRecordExample.createCriteria()
                .andUserIdEqualTo(uid)
                .andRecordTimeGreaterThanOrEqualTo(startTime)
                .andRecordTimeLessThanOrEqualTo(endTime);
        List<AnalysisRecord> analysisRecords = analysisRecordMapper.selectByExample(analysisRecordExample);
        if (analysisRecords == null || analysisRecords.isEmpty()){
            throw new HeartReleaseException(BaseCode.Null);
        }
        List<AnalysisRecordDTO> analysisRecordDTOList = new ArrayList<>();
        analysisRecords.forEach(analysisRecord -> {
            analysisRecordDTOList.add(new AnalysisRecordDTO(analysisRecord));
        });
        return analysisRecordDTOList;
    }
}
