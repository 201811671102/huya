package com.chuangshu.studentworker.serviceimpl;


import com.chuangshu.studentworker.mapper.ReimApplyMapper;
import com.chuangshu.studentworker.mapper.ReimRecordMapper;
import com.chuangshu.studentworker.pojo.ReimApply;
import com.chuangshu.studentworker.pojo.ReimRecord;
import com.chuangshu.studentworker.service.ReimApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 22:28
 */
@Service
public class ReimApplyServiceImpl implements ReimApplyService {
    @Autowired
    private ReimApplyMapper reimApplyMapper;
    @Autowired
    private ReimRecordMapper reimRecordMapper;

    @Override
    @Transactional
    public int reimApply(ReimApply reimApply) {
        int insert = reimApplyMapper.insert(reimApply);
        return insert;
    }

    @Override
    @Transactional
    public int removeReimApplyAll(ReimApply reimApply) {
        int deleteByExample = reimApplyMapper.delete(reimApply);
        return deleteByExample;
    }

    @Override
    @Transactional
    public int removeReimApplyone(ReimApply reimApply) {

        int deleteByExample = reimApplyMapper.delete(reimApply);
        return deleteByExample;
    }




    //查看报销申请
    @Override
    @Transactional
    public List<ReimApply> findReimApply() {
        List<ReimApply> reimApplies = reimApplyMapper.selectAll();

        return reimApplies;
    }

    //报销审核
    @Override
    @Transactional
    public int checkRecord(boolean bool,Integer id) {
        //根据 申请表的id找到要 审核 的 申请
        ReimApply reimApply = reimApplyMapper.selectByPrimaryKey(id);
        //判断是否为空
        if(reimApply!=null){
            //删除该记录
            int i = reimApplyMapper.deleteByPrimaryKey(id);
            //判断记录是否被删除
            if(i==1){
                //如果记录已经删除，则将数据 存储到 一个 审核记录 中
                ReimRecord reimRecord = new ReimRecord();
                reimRecord.setHospital(reimApply.getHospital());
                reimRecord.setReferral_prove(reimApply.getReferral_prove());
                reimRecord.setInvoice(reimApply.getInvoice());
                reimRecord.setSee_doctor_time(reimApply.getSee_doctor_time());
                reimRecord.setStudent_id(reimApply.getStudent_id());
                if(bool==true){
                    reimRecord.setIs_success(1);
                }else{
                    reimRecord.setIs_success(0);
                }

                //将该 记录 提交到 记录表
                int insert = reimRecordMapper.insert(reimRecord);
                return insert;
            }else{
                return 0;
            }
        }else{
            return 0;
        }

    }
}
