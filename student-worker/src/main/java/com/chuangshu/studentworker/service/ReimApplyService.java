package com.chuangshu.studentworker.service;


import com.chuangshu.studentworker.pojo.ReimApply;

import java.util.List;

/**
 * @author Jiaqi Guo
 * @date 2020/6/8  - 22:26
 */
public interface ReimApplyService {
    //报销申请
    int reimApply(ReimApply reimApply);
    //撤销一个或多个报销申请
    //(撤销一个时是传入申请表的id，全部的时候是这个学生的id)
    int removeReimApplyAll(ReimApply reimApply);
    int removeReimApplyone(ReimApply reimApply);


    //查看报销申请
    List<ReimApply> findReimApply();
    //报销审核
    int checkRecord(boolean bool,Integer id);



}
