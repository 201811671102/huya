package com.cs.heart_release_sock.listener;

import com.cs.heart_release_sock.manager.SchedulerManager;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName ParkingAppListener
 * @Description TODO
 * @Author QQ163
 * @Date 2021/1/24 18:29
 **/
@Component
@Log4j2
public class ParkingAppListener implements ApplicationListener<ContextRefreshedEvent> {

    public static ParkingAppListener parkingAppListener;
    @PostConstruct
    public void init(){
        parkingAppListener = this;
    }

    @Autowired
    SchedulerManager schedulerManager;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null){
            try {
                parkingAppListener.schedulerManager.systemJob();
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }
}
