package pre.cg.scheduled.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import pre.cg.scheduled.QuartzDemo;


@Configuration
public class QuartzConfig {
    /**
     * 1.创建job对象
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean factoryBean=new JobDetailFactoryBean();
        //关联自己的job类
        factoryBean.setJobClass(QuartzDemo.class);
        return  factoryBean;
    }
    /**
     * 2.创建Trigger对象
     */
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        SimpleTriggerFactoryBean simpleTriggerFactoryBean=new SimpleTriggerFactoryBean();
        //关联JobDetail对象
        simpleTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        //该参数表示一个执行的毫秒数
        simpleTriggerFactoryBean.setRepeatInterval(2000);
        //重复次数
        simpleTriggerFactoryBean.setRepeatCount(5);

        return simpleTriggerFactoryBean;
    }
    /**
     * 2.创建CronTirgger对象
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        CronTriggerFactoryBean factoryBean=new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        //设置触发时间
        factoryBean.setCronExpression("0/2 * * * * ?");
        return  factoryBean;
    }

    /**
     * 3.创建Scheduler对象
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            SimpleTriggerFactoryBean simpleTriggerFactoryBean,
            JobDetailFactoryBean jobDetailFactoryBean,
            CronTriggerFactoryBean cronTriggerFactoryBean,
            MyAdaptableJobFactory myAdaptableJobFactory){

        SchedulerFactoryBean factoryBean=new SchedulerFactoryBean();
        //关联trigger
        //factoryBean.setTriggers(simpleTriggerFactoryBean.getObject());
        factoryBean.setTriggers(cronTriggerFactoryBean.getObject());

        factoryBean.setJobFactory(myAdaptableJobFactory);

        return  factoryBean;
    }
}
