package com.cs.heart_release_sock.base.config;

import lombok.extern.log4j.Log4j2;
import org.quartz.Scheduler;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName QuartzTreadPoolConfig
 * @Description TODO
 * @Author QQ163
 * @Date 2021/1/23 14:53
 **/
@Configuration
@Component
@Log4j2
public class QuartzThreadPoolConfig {

    @Bean(name = "scheduler")
    @Order(3)
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean){
        return schedulerFactoryBean.getScheduler();
    }

    @Bean(name = "schedulerFactoryBean")
    @Order(2)
    public SchedulerFactoryBean getSchedulerFactoryBean(ThreadPoolTaskExecutor threadPoolTaskExecutor) throws IOException {
        if(threadPoolTaskExecutor == null){
            log.error("threadPoolTaskExecutor is null");
            System.exit(0);
        }
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        //创建SchedulerFactoryBean
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setTaskExecutor(threadPoolTaskExecutor);
        factory.setQuartzProperties(propertiesFactoryBean.getObject());
        //这样当spring关闭时，会等待所有已经启动的quartz job结束后spring才能完全shutdown。
        factory.setWaitForJobsToCompleteOnShutdown(true);
        factory.setOverwriteExistingJobs(false);
        factory.setStartupDelay(1);
        return factory;
    }

    @Bean("threadPoolTaskExecutor")
    @Primary
    @Order(1)
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(5);
        threadPoolTaskExecutor.setCorePoolSize(2);
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //线程池超过最大容量,进行扩容 {}
                executor.setMaximumPoolSize(10);
                Thread thread = new Thread(r);
                thread.start();
            }
        };
        threadPoolTaskExecutor.setRejectedExecutionHandler(handler);
        return threadPoolTaskExecutor;
    }

}
