package com.cs.heart_release_sock.quzrtzJob;

import com.cs.heart_release_sock.base.utils.BaiDuApiUtil;
import com.cs.heart_release_sock.manager.SchedulerManager;
import lombok.SneakyThrows;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;

/**
 * @ClassName NoticeJob
 * @Description TODO
 * @Author QQ163
 * @Date 2021/1/23 14:59
 **/
@Component
public class NoticeJob implements Job {

    public static NoticeJob noticeJob;
    @PostConstruct
    public void init(){
        noticeJob = this;
    }
    @Autowired
    SchedulerManager schedulerManager;


    @SneakyThrows
    @Override
    @Transactional
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            noticeJob.schedulerManager.remove("systemKey","systemGroup");
            noticeJob.schedulerManager.systemJob();
        }catch (Exception e){
            throw new Exception("执行任务失败 \n "+e.getMessage());
        }
    }




}
