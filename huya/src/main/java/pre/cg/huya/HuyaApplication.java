package pre.cg.huya;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pre.cg.huya.websocket.WebsocketServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@MapperScan("pre.cg.huya.mapper")//扫描mybatis的mapper
@EnableSwagger2
@SpringBootApplication
@Slf4j
public class HuyaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HuyaApplication.class, args);
    }

    @Autowired
    WebsocketServer websocketServer;

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    @Override
    public void run(String... args) throws Exception {
        executorService.execute(()->{
            try {
                websocketServer.startup();
            }catch (Exception e){
                log.error("ws app",e);
            }
        });
    }
}
