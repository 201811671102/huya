package com.chuangshu.studentworker.service;


import com.chuangshu.studentworker.pojo.ReimRecord;

import java.util.List;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 22:26
 */
public interface ReimRecordService {
    List<ReimRecord> findReimRecord(ReimRecord reimRecord);


    //查看报销记录
    List<ReimRecord> findReimRecord();
    //下载报销记录
    ReimRecord downloadReimRecord(Integer id);
}
