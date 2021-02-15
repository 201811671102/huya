package com.cs.heart_release_sock;

import com.cs.heart_release_sock.Netty.WebsocketServer;
import com.cs.heart_release_sock.protocol.Consult;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.*;

@MapperScan("com.cs.heart_release_sock.mapper")
@EnableSwagger2
@SpringBootApplication
@Slf4j
//开启mybatis事务支持
@EnableTransactionManagement
public class HeartReleaseSockApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HeartReleaseSockApplication.class, args);
    }

    @Autowired
    WebsocketServer websocketServer;

    // 业务线程池
    public static final ExecutorService executorService =
            new ThreadPoolExecutor(10, 512, 60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(20000));
    @Override
    public void run(String... args) throws Exception {
        executorService.execute(() -> {
            try {
                websocketServer.startup();
            } catch (Exception e) {
                log.error("ws app", e);
            }
        });
    }
}
