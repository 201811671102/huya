package com.chuangshu.studentworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.chuangshu.studentworker.mapper")
@EnableSwagger2
public class StudentWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentWorkerApplication.class, args);
    }

}
