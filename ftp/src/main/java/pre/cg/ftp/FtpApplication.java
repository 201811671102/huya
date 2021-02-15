package pre.cg.ftp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("pre.cg.ftp.mapper")
public class FtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(FtpApplication.class, args);
    }

}
