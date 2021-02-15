package pre.cg.scheduled;

import org.springframework.stereotype.Component;

/**
 * Scheduled定时任务
 */
@Component
public class ScheduledDemo {
    /**
     * 定时任务方法
     * @Scheduled:设置定时任务
     * cron属性：cron表达式。定时任务触发是时间的一个字符串表达形式
     * 0/2 * * * * ? *
     * 秒  分 时 日 月 星期几 年
     */
  /*  @Scheduled(cron = "0/2 * * * * ?")
    public void scheduleMethod(){
        System.out.println("123");
    }*/

}
