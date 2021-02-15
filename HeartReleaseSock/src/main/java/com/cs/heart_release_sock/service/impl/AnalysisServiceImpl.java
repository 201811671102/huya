package com.cs.heart_release_sock.service.impl;

import com.cs.heart_release_sock.code.BaseCode;
import com.cs.heart_release_sock.exception.HeartReleaseException;
import com.cs.heart_release_sock.mapper.AnalysisMapper;
import com.cs.heart_release_sock.mapper.diymapper.AnalysisMapperDIY;
import com.cs.heart_release_sock.pojo.Analysis;
import com.cs.heart_release_sock.pojo.AnalysisExample;
import com.cs.heart_release_sock.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AnalysisServiceImpl
 * @Description TODO
 * @Author QQ163
 * @Date 2021/2/15 12:56
 **/
@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    AnalysisMapper analysisMapper;
    @Autowired
    AnalysisExample analysisExample;
    @Autowired
    AnalysisMapperDIY analysisMapperDIY;

    @Override
    public void insert(Analysis analysis) {
        int i = analysisMapper.insertSelective(analysis);
        if (i == -1){
            throw new HeartReleaseException(BaseCode.System_Error);
        }
    }

    @Override
    public List<Analysis> selectByUid(Integer uid) {
        List<Analysis> analyses = analysisMapperDIY.selectUserAnalysis(uid);
        if (analyses == null || analyses.isEmpty()){
            throw new HeartReleaseException(BaseCode.Null);
        }
        return analyses;
    }
}
