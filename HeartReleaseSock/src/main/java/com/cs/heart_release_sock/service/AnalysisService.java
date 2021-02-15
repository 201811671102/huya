package com.cs.heart_release_sock.service;

import com.cs.heart_release_sock.pojo.Analysis;

import java.util.List;

public interface AnalysisService {
    /*增加信息分析*/
    void insert(Analysis analysis);
    /*查询用户信息分析结果*/
    List<Analysis> selectByUid(Integer uid);
}
