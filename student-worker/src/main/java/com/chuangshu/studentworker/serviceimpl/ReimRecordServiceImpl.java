package com.chuangshu.studentworker.serviceimpl;



import com.chuangshu.studentworker.mapper.ReimRecordMapper;
import com.chuangshu.studentworker.pojo.ReimRecord;
import com.chuangshu.studentworker.service.ReimRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 22:29
 */
@Service
public class ReimRecordServiceImpl implements ReimRecordService {

    @Autowired
    private ReimRecordMapper reimRecordMapper;

    @Override
    @Transactional
    public List<ReimRecord> findReimRecord(ReimRecord reimRecord) {
        List<ReimRecord> reimRecords = reimRecordMapper.select(reimRecord);

        return reimRecords;
    }

    //查看报销记录
    @Override
    @Transactional
    public List<ReimRecord> findReimRecord() {
        List<ReimRecord> reimRecords = reimRecordMapper.selectAll();

        return reimRecords;
    }
    //下载报销记录
    @Override
    @Transactional
    public ReimRecord downloadReimRecord(Integer id) {
        ReimRecord reimRecord = reimRecordMapper.selectByPrimaryKey(id);

        return reimRecord;
    }
}
