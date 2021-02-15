package com.cs.heart_release_sock.manager;

import com.cs.heart_release_sock.base.utils.BaiDuApiUtil;
import com.cs.heart_release_sock.base.utils.CornTimeUtil;
import com.cs.heart_release_sock.code.BaseCode;
import com.cs.heart_release_sock.exception.HeartReleaseException;
import com.cs.heart_release_sock.quzrtzJob.NoticeJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName ScedulerManager
 * @Description TODO
 * @Author QQ163
 * @Date 2021/1/23 14:56
 **/
@Component
public class SchedulerManager {

    @Autowired
    private Scheduler scheduler;

    public static SchedulerManager schedulerManager;
    @PostConstruct
    public void init(){
        schedulerManager = this;
    }


    //清除任务
    public void clear() throws SchedulerException {
        scheduler.clear();
    }


    /*systemjob*/
    public void systemJob(){
        try {
            scheduler.start();
            JobDetail jobDetail = JobBuilder.newJob(NoticeJob.class)
                    .withIdentity("systemKey", "systemGroup")
                    .build();
            Date date = BaiDuApiUtil.getAuth();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(CornTimeUtil.getInstance().dateToCorn(date));
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("systemKey", "systemGroup")
                    .withSchedule(scheduleBuilder)
                    .build();
            scheduler.scheduleJob(jobDetail,cronTrigger);
        } catch (Exception e) {
            throw new HeartReleaseException(BaseCode.System_Error.getCode(),"获取token任务失败！\n "+e.getMessage());
        }
    }

    //添加任务
    public void add(String jobName,String jobGroup, Date cornDate, JobDataMap jobDataMap) throws SchedulerException {
        try {
            scheduler.start();
            JobDetail jobDetail = JobBuilder.newJob(NoticeJob.class)
                    .withIdentity(jobName, jobGroup)
                    .usingJobData(jobDataMap)
                    .build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(CornTimeUtil.getInstance().dateToCorn(cornDate));
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName, jobGroup)
                    .withSchedule(scheduleBuilder)
                    .build();
            scheduler.scheduleJob(jobDetail,cronTrigger);
        } catch (Exception e) {
            throw new HeartReleaseException(BaseCode.System_Error.getCode(),"定时任务出错！\n "+e.getMessage());
        }
    }

    //添加任务
    public void add(String jobName, String jobGroup, LocalDateTime localDateTime, JobDataMap jobDataMap) throws SchedulerException {
        try {
            scheduler.start();
            JobDetail jobDetail = JobBuilder.newJob(NoticeJob.class)
                    .withIdentity(jobName, jobGroup)
                    .usingJobData(jobDataMap)
                    .build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(CornTimeUtil.getInstance().dateToCorn(localDateTime));
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName, jobGroup)
                    .withSchedule(scheduleBuilder)
                    .build();
            scheduler.scheduleJob(jobDetail,cronTrigger);
        } catch (Exception e) {
            throw new HeartReleaseException(BaseCode.System_Error.getCode(),"定时任务出错！\n "+e.getMessage());
        }
    }

    //添加任务
    public void add(String jobName,String jobGroup, Date cornDate) throws SchedulerException {
        try {
            scheduler.start();
            JobDetail jobDetail = JobBuilder.newJob(NoticeJob.class)
                    .withIdentity(jobName, jobGroup)
                    .build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(CornTimeUtil.getInstance().dateToCorn(cornDate));
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName, jobGroup)
                    .withSchedule(scheduleBuilder)
                    .build();
            scheduler.scheduleJob(jobDetail,cronTrigger);
        } catch (Exception e) {
            throw new HeartReleaseException(BaseCode.System_Error.getCode(),"定时任务出错！\n "+e.getMessage());
        }
    }

    //停止任务
    public boolean pause(String jobName,String jobGroup){
        JobKey jobKey = new JobKey(jobName,jobGroup);
        //暂停触发器
        try {
            scheduler.pauseJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            return false;
        }
    }

    //移除任务
    public boolean remove(String jobName,String jobGroup){
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName,jobGroup);
        try {
            //停止出发前
            scheduler.pauseTrigger(triggerKey);
            //移除触发器
            scheduler.unscheduleJob(triggerKey);
            //删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName,jobGroup));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //恢复停止的任务
    public boolean resume(String jobName,String jobGroup){
        JobKey jobKey = new JobKey(jobName,jobGroup);
        try {
            scheduler.resumeJob(jobKey);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //修改任务时间
    public boolean modify(String jobName,String jobGroup,String time){
        Date date = null;
        try {
            TriggerKey triggerKey = new TriggerKey(jobName,jobGroup);
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            String oldTime = cronTrigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)){
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
                CronTrigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(jobName,jobGroup)
                        .withSchedule(cronScheduleBuilder).build();
                date = scheduler.rescheduleJob(triggerKey,trigger);
            }
            return date != null;
        }catch (Exception e){
            return false;
        }
    }

}
