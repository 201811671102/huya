package pre.cg;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("pre.cg.mapper")//扫描mybatis的mapper
@EnableScheduling//定时任务
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
